package com.wangxiaobao.wechatgateway.controller.openplatform;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoResponse;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoSearchCondition;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.CreateUUID;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.WxUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.http.URIUtil;

@Controller
@Slf4j
public class OpenPlatformController {
	public final Logger logger = LoggerFactory.getLogger(OpenPlatformController.class);
	String appId = "wx2faa58ec608c1d69";
	String appsecret = "77347a37924b2583b183e1b43900c439";
	String redirectUrl = "http://kkg7c8.natappfree.cc";

	// String appId = "wxde53154a5290b54d";
	// String appsecret = "846e110a7c0e88488ca1ef915224f23d";
	static String sessionKey = "NcyLnPKk2skzUOtbBzBXDw==";

	@Autowired
	private TestService testService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/")
	@ResponseBody
	public String getSessionKey(String appId, String appsecret, String code) {
		String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret=" + appsecret
				+ "&js_code=" + code + "&grant_type=authorization_code";
		String StrResult = restTemplate.postForObject(url, new HashMap<>(), String.class);
		sessionKey = JSONObject.parseObject(StrResult).getString("session_key");
		logger.info(sessionKey);
		return StrResult;
	}

	@RequestMapping(value = "/union")
	@ResponseBody
	public String getUnionId(String data, String iv) throws Exception {
		data = "z9uNBJOrIC0Jky1ehbxEZoz9IEMKahsRf51gkL6YSa19dnJphQ+VmlLANBwbqmP5mexRLKzXGN7XAIcyKzGQ3rzsHPuxlIs882f1Za2yfQIIW5SzGk+eVVhKIh0SMYBG1XtbXog/yrhaD1kJQpYVpDFmBTF0zlpHsid6qYjnWpp0IA7h5AagU/JCzGxq1hghxdbOvTGRShB2zOT6u2ZGKM8Ewa+tguKLlxDdivGhtWNiN1C0OpkMJiIPoxg+Lia76aSTFfJVms1pyVNjmHin56EQ5+xw3ebvn7jgFdm3ET6QqQzMb0IhvyyaYEaYErHmyMaTqh4l9MSoIIcXXOZiD1uWh1D9Bz0O4u9DX+5D1lz8A0UYmYGTghwIcEoiW8HIvoDsYII0HH789xIj1TE2kZLuGdT9FFZSuVlsHFkHMC2GMp3ftFvtNeojpG+78cKFxfpflMIaL5Ok7gHNLxpWzfv2meUpNWsGodWPfCFj3hs=";
		iv = "RO5NIX5yPhRmcK1JFRZIhA==";
		// 被加密的数据
		byte[] dataByte = Base64.decodeBase64(data);
		// 加密秘钥
		byte[] keyByte = Base64.decodeBase64(sessionKey);
		// 偏移量
		byte[] ivByte = Base64.decodeBase64(iv);
		try {
			// 如果密钥不足16位，那么就补足. 这个if 中的内容很重要
			int base = 16;
			if (keyByte.length % base != 0) {
				int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
				byte[] temp = new byte[groups * base];
				Arrays.fill(temp, (byte) 0);
				System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
				keyByte = temp;
			}
			// 初始化
			Security.addProvider(new BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
			SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
			AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
			parameters.init(new IvParameterSpec(ivByte));
			cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
			byte[] resultByte = cipher.doFinal(dataByte);
			if (null != resultByte && resultByte.length > 0) {
				String result = new String(resultByte, "UTF-8");
				return result;
			}
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/9984853244.txt")
	public void index(HttpServletResponse response) {
		try {

			// 下载机器码文件
			response.setHeader("content-type", "text/plain");
			response.setContentType("text/plain");
			response.setHeader("Content-Disposition",
					"attachment;filename=" + new String("9984853244.txt".getBytes("ISO-8859-1"), "UTF-8"));

			OutputStream os = response.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);

			int length = 0;
			byte[] temp = "6649d1c4c1007ad1b60d9ced05fc2265".getBytes();

			bos.write(temp, 0, length);
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @methodName: AuthCallBack @Description:
	 *              微信平台回调地址（定时10分钟的ticket推送和授权以及取消授权推送） @param request @return
	 *              Object @createUser: liping_max @createDate: 2017年9月13日
	 *              下午7:22:39 @updateUser: liping_max @updateDate: 2017年9月13日
	 *              下午7:22:39 @throws
	 */
	@RequestMapping(value = "/index/auth/callBack")
	@ResponseBody
	public String AuthCallBack(HttpServletRequest request) {
		String authCode = request.getParameter("auth_code");
		String msgSignature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String organizeId = request.getParameter("organizeId");
		// 授权账户类型1：公众号；2：小程序
		String authType = request.getParameter("authType");
		if (StringUtils.isEmpty(authCode)) {
			try {
				InputStream inputStream = request.getInputStream();
				String resultXml = testService.decodeStr(inputStream, msgSignature, timestamp, nonce, appId);
				logger.info("微信推送解密后resultXml：" + resultXml);
				if (null != resultXml) {
					Map<String, String> map = WxUtil.readStringXmlOut(resultXml);
					String infoType = map.get("InfoType");
					String authorizerAppid = map.get("AuthorizerAppid");
					// component_verify_ticket:微信平台定时推送；authorized：微信公众号授权第三方平台；unauthorized：微信公众号取消授权第三方平台
					switch (infoType) {
					case "component_verify_ticket":
						String ticket = map.get("ComponentVerifyTicket");
						logger.info("微信推送获取ticket值：" + ticket);
						redisService.set("componentVerifyTicket", ticket);
						return "success";
					case "authorized":
						String authorizationCode = map.get("AuthorizationCode");
						logger.info("返回的授权code：" + authorizationCode);
						String authorizationInfo = testService.apiQueryAuth(authorizationCode, authType, organizeId,
								appId, appsecret);
						return buildingAuthorizer(authorizationInfo,authType,organizeId);
					case "unauthorized":
						logger.info("授权公众号取消授权：AuthorizerAppid=" + authorizerAppid);
						wXopenPlatformMerchantInfoService.deleteByWXAppId(authorizerAppid);
						return "授权公众号取消授权";
					case "TESTCOMPONENT_MSG_TYPE_TEXT":
						return "TESTCOMPONENT_MSG_TYPE_TEXT_callback";
					default:
						break;
					}
				} else {
					logger.info("微信平台未推送内容到服务器");
				}
			} catch (IOException e) {
				logger.error("接收处理微信平台推送内容异常" + e.getMessage());
				e.printStackTrace();
			}
		} else {
			logger.info("返回的授权code：" + authCode);
			String authorizationInfo = testService.apiQueryAuth(authCode, authType, organizeId, appId, appsecret);
			return buildingAuthorizer(authorizationInfo,authType,organizeId);
		}
		return null;
	}

	//组装授权和创建开放平台
	public String buildingAuthorizer(String authorizationInfo,String authType,String organizeId){
		JSONObject jsono = JSONObject.parseObject(authorizationInfo);
		WXopenPlatformMerchantInfo oldWxInfo = wXopenPlatformMerchantInfoService
				.getByWXAppId(jsono.getString("authorizer_appid"));
		if (null == oldWxInfo || StringUtils.isEmpty(oldWxInfo.getWxAppid())) {
			if(Constants.AUTHORIZER_TYPE_GONGZHONGHAO.equals(authType)){
				// 创建商户公众号的开放平台
				JsonResult result = testService.createOpen(jsono.getString("authorizer_appid"),
						jsono.getString("authorizer_access_token"));
				if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
					WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(),
							jsono.getString("authorizer_appid"), null,
							jsono.getString("authorizer_access_token"),
							jsono.getString("authorizer_refresh_token"), "createUser", new Date(),
							"updateUser", new Date(), result.getData().toString(), authType, organizeId);
					wXopenPlatformMerchantInfoService.save(wxInfo);
					return "授权成功";
				} else {
					return "授权失败";
				}
			}else{
				//小程序授权
				WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(Constants.AUTHORIZER_TYPE_GONGZHONGHAO, organizeId);
				List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService.findByCondition(wxCondition);
				if(null==wxInfoResponses||wxInfoResponses.size()<=0){
					return "授权失败,请先授权公众号";
				}
				JsonResult result = testService.bindOpen(jsono.getString("authorizer_appid"), wxInfoResponses.get(0).getOpenAppid(), jsono.getString("authorizer_access_token"));
				if(JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())){
					WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(),
							jsono.getString("authorizer_appid"), null,
							jsono.getString("authorizer_access_token"),
							jsono.getString("authorizer_refresh_token"), "createUser", new Date(),
							"updateUser", new Date(), wxInfoResponses.get(0).getOpenAppid(), authType, organizeId);
					wXopenPlatformMerchantInfoService.save(wxInfo);
				}
				return result.getMessage();
			}
		}else{
			return "授权成功";
		}
	}
	
	/**
	 * @methodName: getPreAuthCode @Description: 获取第三方平台的预授权码 @return
	 *              String @createUser: liping_max @createDate: 2017年9月13日
	 *              下午7:24:30 @updateUser: liping_max @updateDate: 2017年9月13日
	 *              下午7:24:30 @throws
	 */
	public String getPreAuthCode() {
		String componentAccessToken = testService.getApiComponentToken(appId, appsecret);
		JSONObject jsonO = new JSONObject();
		jsonO.put("component_appid", appId);
		String StrResult = HttpClientUtils.executeByJSONPOST(
				"https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode?component_access_token="
						+ componentAccessToken,
				jsonO.toJSONString(), 5000);
		JSONObject jsonResult = JSONObject.parseObject(StrResult);
		String preAuthCode = jsonResult.getString("pre_auth_code");
		return preAuthCode;
	}

	/**
	 * @methodName: getAuthUrl @Description: 微信公众号管理者授权入口 @param @return
	 *              ModelAndView @createUser: liping_max @createDate: 2017年9月13日
	 *              下午7:24:59 @updateUser: liping_max @updateDate: 2017年9月13日
	 *              下午7:24:59 @throws
	 */
	@RequestMapping("/index/wx/getAuthUrl")
	public String getAuthUrl(Model model, HttpServletRequest request) {
		String authType = request.getParameter("authType");
		String organizeId = request.getParameter("organizeId");
		if (StringUtils.isEmpty(authType)) {
			authType = "3";
		}
		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=" + appId + "&pre_auth_code="
				+ getPreAuthCode());
		sbUrl.append("&auth_type=").append(authType);
		String redirectUri = URIUtil.encodeURIComponent(
				redirectUrl + "/index/auth/callBack?organizeId=" + organizeId + "&authType=" + authType);
		sbUrl.append("&redirect_uri=").append(redirectUri);
		model.addAttribute("redirectUrl", sbUrl.toString());
		return "/test";
	}

	/**
	 * @methodName: getAuthorizerInfo @Description: 获取授权方的账号基本信息 @param
	 * wxAppId @return JsonResult @createUser: liping_max @createDate:
	 * 2018年1月13日 下午2:16:04 @updateUser: liping_max @updateDate: 2018年1月13日
	 * 下午2:16:04 @throws
	 */
	@RequestMapping("/wxAuth/getAuthorizerInfo")
	@ResponseBody
	public JsonResult getAuthorizerInfo(String wxAppId) {
		String authorizerInfoUrl = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token="
				+ testService.getApiComponentToken(appId, appsecret);
		JSONObject params = new JSONObject();
		params.put("component_appid", appId);
		params.put("authorizer_appid", wxAppId);
		String result = HttpClientUtils.executeByJSONPOST(authorizerInfoUrl, params.toJSONString(), 50000);
		return JsonResult.newInstanceDataSuccess(JSONObject.parseObject(result));
	}

	/**
	 * @methodName: unauthorized @Description: 取消授权 @param wxAppId @return
	 * JsonResult @createUser: liping_max @createDate: 2018年1月13日
	 * 下午2:04:52 @updateUser: liping_max @updateDate: 2018年1月13日
	 * 下午2:04:52 @throws
	 */
	public JsonResult unauthorized(String wxAppId) {

		return null;
	}

	/**
	 * @methodName: createOpen @Description: 用第三方平台帮助商家公众号创建开放平台 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午9:57:25 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午9:57:25 @throws
	 */
	@RequestMapping("/gongzhonghao/createOpen")
	@ResponseBody
	public JsonResult createOpen(String wxAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid, appId, appsecret);
		return testService.createOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
	}

	/**
	 * @methodName: unbindOpen @Description: 商户公众号与开放平台解绑 @param wxAppid @param
	 *              openAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午11:00:12 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午11:00:12 @throws
	 */
	@RequestMapping("/gongzhonghao/unbindOpen")
	@ResponseBody
	public JsonResult unbindOpen(String wxAppid, String openAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid, appId, appsecret);
		JsonResult jsonResult = testService.unbindOpen(wxAppid, openAppid,
				wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
		return jsonResult;
	}

	/**
	 * @methodName: getBindOpen @Description: 获取商户微信号或公众号绑定的开放平台信息 @param
	 * wxAppid @return JsonResult @createUser: liping_max @createDate:
	 * 2018年1月13日 下午4:24:56 @updateUser: liping_max @updateDate: 2018年1月13日
	 * 下午4:24:56 @throws
	 */
	@RequestMapping("/gongzhonghao/getBindOpen")
	@ResponseBody
	public JsonResult getBindOpen(String wxAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid, appId, appsecret);
		return testService.getBindOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
	}

}