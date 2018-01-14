package com.wangxiaobao.wechatgateway.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.SSLContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangxiaobao.wechatgateway.properties.SysConfig;
import com.wangxiaobao.wechatgateway.sign.MD5Util;
import com.wangxiaobao.wechatgateway.sign.SHA1;

@SuppressWarnings("deprecation")
public class WxUtil {

	private static Logger logger = LoggerFactory.getLogger(WxUtil.class);

	/**
	 * 
	 * @author:Libin
	 * @Title: getStringByMap
	 * @Description: DOMAP的数据集合转String进行MD5加密
	 * @param @param sParaTemp
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getStringByMap(SortedMap<Object, Object> sParaTemp) {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<Object, Object> entry : sParaTemp.entrySet()) {
			if (entry.getValue() != "" && entry.getKey() != "sign") {
				list.add(entry.getKey() + "=" + entry.getValue() + "&");
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		return sb.toString();
	}

	private static String getStringByMapWZP(SortedMap<Object, Object> sParaTemp) {
		ArrayList<String> list = new ArrayList<String>();
		for (Entry<Object, Object> entry : sParaTemp.entrySet()) {
			if (entry.getValue() != "" && entry.getKey() != "sign") {
				list.add(entry.getKey() + "" + entry.getValue());
			}
		}
		int size = list.size();
		String[] arrayToSort = list.toArray(new String[size]);
		Arrays.sort(arrayToSort, String.CASE_INSENSITIVE_ORDER);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			sb.append(arrayToSort[i]);
		}
		return sb.toString();
	}

	/**
	 * 
	 * @author:Libin
	 * @Title: getSign
	 * @Description: DO生产预支付订单和提交支付是的MD5签名
	 * @param @param sParaTemp
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String getSign(SortedMap<Object, Object> sParaTemp) {
		String result = getStringByMap(sParaTemp) + "key=" + SysConfig.getPayKey();
		result = MD5Util.MD5Encode(result).toUpperCase();
		return result;
	}

	public static String getSign(SortedMap<Object, Object> sParaTemp, String key) {
		String result = getStringByMap(sParaTemp) + "key=" + key;
		result = MD5Util.MD5Encode(result).toUpperCase();
		return result;
	}

	public static String getSignWZP(SortedMap<Object, Object> sParaTemp, String key) {
		String result = getStringByMapWZP(sParaTemp) + "key" + key;
		result = MD5Util.MD5Encode(result).toUpperCase();
		return result;
	}

	/**
	 * 取得wxConfig签名signature
	 * 
	 * @param timestamp
	 * @param nonceStr
	 * @param url
	 * @param accessToken
	 * @return
	 */
	public static String getSignature(long timestamp, String nonceStr, String url, String accessToken) {
		String ticket = getTicket(accessToken);
		logger.info("获取微信tiket：{}", ticket);
		String signature = null;
		String str = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + timestamp + "&url=" + url;
		logger.info("获取签名String组合：{}", str);
		// 对string1进行sha1签名，得到signature
		signature = SHA1.getDigestOfString(str.getBytes());
		return signature;
	}

	/**
	 * 取得随机字符串
	 * 
	 * @return
	 */
	public static String getNoncestr() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * JavaBean转换成xml
	 * 
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String convertToXml(Object obj, String encoding) {
		String result = null;
		try {
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);

			StringWriter writer = new StringWriter();
			marshaller.marshal(obj, writer);
			result = writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/** 
	 * xml转换成JavaBean 
	 * @param xml 
	 * @param c 
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T converyToJavaBean(Class<T> c, String xml) {
		T t = null;
		try {
			JAXBContext context = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			t = (T) unmarshaller.unmarshal(new StringReader(xml));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/************************* 获取微信预支付订单号XML转MAP ****开始 *********************************************************************/
	/**
	 * @description 将xml字符串转换成map
	 * @param xml
	 * @return Map
	 */
	public static Map<String, String> readStringXmlOut(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			map = listNodes(rootElt, map);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 遍历当前节点下的所有节点
	@SuppressWarnings("unchecked")
	public static Map<String, String> listNodes(Element node, Map<String, String> map) {

		// 如果当前节点内容不为空，则输出
		if (!(node.getTextTrim().equals(""))) {
			// logger.info("属性" + node.getName() + "：" + node.getText());
			map.put(node.getName(), node.getText());
		}
		Iterator<Element> iterator = node.elementIterator();
		while (iterator.hasNext()) {
			Element e = iterator.next();
			listNodes(e, map);
		}
		return map;
	}

	/************************* 获取微信预支付订单号XML转MAP ****开始 *********************************************************************/
	/**
	 * 
	 * @author:Libin
	 * @Title: getPrepayId
	 * @Description: DO 获取微信的预支付订单号
	 * @param @param sParaTemp
	 * @param @return
	 * @return String
	 * @throws
	 */
	/*
	 * public static String getPrepayId(Object sParaTemp) { String url =
	 * "https://api.mch.weixin.qq.com/pay/unifiedorder"; String result = null;
	 * HttpPost httpRequst = new HttpPost(url); String postDataXML =
	 * convertToXml(sParaTemp, "UTF-8"); try { //
	 * 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别 StringEntity postEntity = new
	 * StringEntity(postDataXML, "UTF-8"); httpRequst.setEntity(postEntity);
	 * HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
	 * HttpEntity entity = httpResponse.getEntity(); result =
	 * EntityUtils.toString(entity, "UTF-8"); Map<String, String> xmlMap =
	 * readStringXmlOut(result); result = xmlMap.get("prepay_id"); } catch
	 * (ConnectionPoolTimeoutException e) { } catch (ConnectTimeoutException e)
	 * { } catch (SocketTimeoutException e) { } catch (Exception e) { } finally
	 * { } return result; }
	 */

	/**
	 * 获取OpenId
	 * 
	 * @Title: getOpenId
	 * @Description: DO
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static JSONObject getOpenId(String code) {
		// access_token
		String requestUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		requestUrl = requestUrl.replace("APPID", SysConfig.getAppId());
		requestUrl = requestUrl.replace("SECRET", SysConfig.getAppSecret());
		requestUrl = requestUrl.replace("CODE", code);

		try {
			String SubmitResult = HttpClientUtils.executeByGET(requestUrl);

			JSONObject demoJson = new JSONObject(SubmitResult);

			return demoJson;

		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @author:Libin
	 * @Title: wxSubmitOrderPaySign
	 * @Description: DO 调用微信支付所需的参数和签名
	 * @param @param prepay_id
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static SortedMap<Object, Object> wxSubmitOrderPaySign(String prepay_id) {
		SortedMap<Object, Object> sParaTemp = new TreeMap<Object, Object>();
		sParaTemp.put("appId", SysConfig.getAppId());
		sParaTemp.put("timeStamp", System.currentTimeMillis() / 1000);
		sParaTemp.put("nonceStr", getNoncestr());
		sParaTemp.put("package", "prepay_id=" + prepay_id);
		sParaTemp.put("signType", "MD5");
		String sign = getSign(sParaTemp);
		sParaTemp.put("sign", sign);
		return sParaTemp;
	}

	/************************* 获取微信通行令牌 固定写法 ****开始 *********************************************************************/

	public static String getTicket(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=" + accessToken;
		logger.info("获取ticket完整URL地址:{}", url);
		String ticket = null;
		try {
			URL urlGet = new URL(url);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();
			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String message = new String(jsonBytes, "UTF-8");
			JSONObject demoJson = new JSONObject(message);
			if (demoJson != null && demoJson.has("ticket")) {
				ticket = demoJson.getString("ticket");
			} else {
			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}

	/**
	 * 
	 * @author:LIBIN
	 * @title closeOrder
	 * @Description DO 微信关闭订单
	 * @updaeDate 2015年10月15日
	 * @param sParaTemp
	 * @return
	 */
	/*
	 * public static String closeOrder(Object sParaTemp) { String url =
	 * "https://api.mch.weixin.qq.com/pay/closeorder"; String result = null;
	 * HttpPost httpRequst = new HttpPost(url); String postDataXML =
	 * convertToXml(sParaTemp, "UTF-8"); try { //
	 * 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别 StringEntity postEntity = new
	 * StringEntity(postDataXML, "UTF-8"); httpRequst.setEntity(postEntity);
	 * HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
	 * HttpEntity entity = httpResponse.getEntity(); result =
	 * EntityUtils.toString(entity, "UTF-8"); Map<String, String> xmlMap =
	 * readStringXmlOut(result); result = xmlMap.get("return_code"); } catch
	 * (ConnectionPoolTimeoutException e) { } catch (ConnectTimeoutException e)
	 * { } catch (SocketTimeoutException e) { } catch (Exception e) { } finally
	 * { } return result; }
	 */

	/**
	 * 
	 * @author:LIBIN
	 * @title refundOrder
	 * @Description DO微信退款
	 * @updaeDate 2015年10月15日
	 * @param sParaTemp
	 * @return
	 * @throws IOException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws KeyManagementException 
	 * @throws UnrecoverableKeyException 
	 */
	public static String refundOrder(Object sParaTemp) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException, NoSuchAlgorithmException, IOException {
		String result = null;
		HttpPost httpRequst = new HttpPost(WxUrlConfig.WEIXIN_PAY_REFUND_URL);
		String postDataXML = convertToXml(sParaTemp, "UTF-8");
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(SysConfig.getPropertiesParams("app_refund_cert")));// 加载本地的证书进行https加密传输
		try {
			keyStore.load(instream, SysConfig.getMchId().toCharArray());// 设置证书密码
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} finally {
			instream.close();
		}
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, SysConfig.getMchId().toCharArray()).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		// 根据默认超时限制初始化requestConfig
		httpRequst.setConfig(RequestConfig.custom().setSocketTimeout(1000).setConnectTimeout(1000).build());
		try {
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
			httpRequst.setEntity(postEntity);
			HttpResponse httpResponse = httpClient.execute(httpRequst);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			Map<String, String> xmlMap = readStringXmlOut(result);
			result = xmlMap.get("return_code");
			if ("SUCCESS".equals(result)) {
				result = xmlMap.get("result_code") + ":" + xmlMap.get("err_code_des") == null ? "" : xmlMap.get("err_code_des");
			} else {
				result = xmlMap.get("return_msg");
			}
		} catch (ConnectionPoolTimeoutException e) {
		} catch (ConnectTimeoutException e) {
		} catch (SocketTimeoutException e) {
		} catch (Exception e) {
		} finally {
		}
		return result;
	}

	/**
	 * 
	 * @author:LIBIN
	 * @title getWxDataByInterfaceURL
	 * @Description DO微信POST请求
	 * @updaeDate 2015年10月16日
	 * @param URL
	 * @param interfaceName
	 * @param sParaTemp
	 * @return
	 */

	@SuppressWarnings("resource")
	public static String getWxDataByInterfaceURL(String URL, Object sParaTemp, String interfaceName) {
		String result = null;
		HttpPost httpRequst = new HttpPost(URL);
		String postDataXML = convertToXml(sParaTemp, "UTF-8");
		try {
			// 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
			StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
			httpRequst.setEntity(postEntity);
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			HttpEntity entity = httpResponse.getEntity();
			result = EntityUtils.toString(entity, "UTF-8");
			Map<String, String> xmlMap = readStringXmlOut(result);
			result = getWxDataByXmlMap(URL, xmlMap, interfaceName);
		} catch (ConnectionPoolTimeoutException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return result;
	}

	private static String getWxDataByXmlMap(String uRL, Map<String, String> xmlMap, String interfaceName) {
		// 预订单返订单编号
		String result = null;
		if (WxUrlConfig.WEIXIN_PAY_UNIFIEDORDER_URL.equals(uRL)) {
			result = xmlMap.get("prepay_id");
			if (Constants.QC_PAY.equals(interfaceName)) {
				result = xmlMap.get("code_url");
			}
		} else if (WxUrlConfig.WEIXIN_PAY_ORDERQUERY_URL.equals(uRL)) {
			result = xmlMap.get("return_code");
			String trade_state = xmlMap.get("trade_state");
			if (!"SUCCESS".equals(trade_state)) {
				result = null;
			}
		} else if (WxUrlConfig.WEIXIN_WIFI_POIID.equals(uRL)) {

		} else {
			result = xmlMap.get("return_code");
		}
		return result;
	}

	/************************* 获取微信通行令牌 固定写法 ****结束 *********************************************************************/
	public static String getWxConfig(String url, String accessToken) {
		String config = "wx.config({debug: false, appId: '" + SysConfig.getAppId() + "', timestamp:{0} , nonceStr:'{1}', signature:'{2}',jsApiList: ['hideOptionMenu','closeWindow','scanQRCode'] })";
		long timestamp = System.currentTimeMillis() / 1000;// 时间戳
		String noncestr = getNoncestr();// 随机字符串
		int length = url.length();
		if (url.indexOf("#") != -1) {
			length = url.indexOf("#");
		}
		String uri = url.substring(0, length);// 当前网页的URL，不包含#及其后面部分
		logger.info("获取签名参数:timestamp={},noncestr={},url={},accessToken={}", timestamp, noncestr, uri, accessToken);
		String signature = WxUtil.getSignature(timestamp, noncestr, uri, accessToken);// 调取上面的获取signature的方法
		logger.info("获取签名结果:signature={}", signature);
		config = config.replace("{0}", Long.toString(timestamp)).replace("{1}", noncestr).replace("{2}", signature);
		logger.info("获取WxConfig结果：{}", config);
		return config;
	}

	/************************* 获取微信通行令牌 重载写法 ****结束 *********************************************************************/
	/**
	 *
	 * @param url
	 * @param jsApiList "'hideOptionMenu','closeWindow','scanQRCode','onMenuShareTimeline','onMenuShareAppMessage'"
	 * @param accessToken
	 * @return
	 */
	public static String getWxConfig(String url,String jsApiList, String accessToken) {
		String config = "wx.config({debug: false, appId: '" + SysConfig.getAppId() + "', timestamp:{0} , nonceStr:'{1}', signature:'{2}',jsApiList:"+jsApiList+" })";
		long timestamp = System.currentTimeMillis() / 1000;// 时间戳
		String noncestr = getNoncestr();// 随机字符串
		int length = url.length();
		if (url.indexOf("#") != -1) {
			length = url.indexOf("#");
		}
		String uri = url.substring(0, length);// 当前网页的URL，不包含#及其后面部分
		logger.info("获取签名参数:timestamp={},noncestr={},url={},accessToken={}", timestamp, noncestr, uri, accessToken);
		String signature = WxUtil.getSignature(timestamp, noncestr, uri, accessToken);// 调取上面的获取signature的方法
		logger.info("获取签名结果:signature={}", signature);
		config = config.replace("{0}", Long.toString(timestamp)).replace("{1}", noncestr).replace("{2}", signature);
		logger.info("获取WxConfig结果：{}", config);
		return config;
	}



	public static String getOpendIdByUrl(String url) throws JSONException {
		String code = GetWeiXinCode.getCodeRequest(url);
		JSONObject demoJson = getOpenId(code);
		return demoJson.getString("openid");
	}


	/**
	 * DO 上传图片
	 * @param accessToken
	 * @param fileType
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String uploadImg(String accessToken, String fileType, String filePath) throws Exception {
		// 上传文件请求路径
		String action = WxUrlConfig.WEIXIN_WIFI_UPLOADIMG + "?access_token=" + accessToken + "&type=" + fileType;
		URL url = new URL(action);
		String result = null;
		File file = new File(filePath);
		if (!file.exists() || !file.isFile()) {
			throw new IOException("上传的文件不存在");
		}
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "----------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
		// 请求正文信息
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		InputStream in = new FileInputStream(file);
		int bytes = 0;
		byte[] bufferOut = new byte[(int) file.length()];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			if (result == null) {
				result = buffer.toString();
			}
		} catch (IOException e) {
			System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new IOException("数据读取异常");
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		String imgUrl = null;
		JSONObject jsonObj = new JSONObject(result);
		if (jsonObj != null && jsonObj.has("url")) {
			imgUrl = jsonObj.getString("url");
		} else {
		}
		return imgUrl;
	}
}
