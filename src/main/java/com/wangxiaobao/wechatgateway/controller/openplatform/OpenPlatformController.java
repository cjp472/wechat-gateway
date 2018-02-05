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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoSearchCondition;
import com.wangxiaobao.wechatgateway.service.kefu.KeFuService;
import com.wangxiaobao.wechatgateway.service.miniprogramtemplate.WxMiniprogramTemplateService;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.CreateUUID;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.WxUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;

@Controller
@Slf4j
public class OpenPlatformController {
	public final Logger logger = LoggerFactory.getLogger(OpenPlatformController.class);
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Value("${wechat.openplatform.redirectUrl}")
	String redirectUrl;
	@Value("${wechat.openplatform.token}")
	String token;
	@Value("${wechat.openplatform.encodingAesKey}")
	String encodingAesKey;

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
	@Autowired
	OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private OrganizeTemplateService organizeTemplateService;
	@Autowired
	KeFuService keFuService;
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
		String organizationAccount = request.getParameter("organizationAccount");
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
						String authorizationInfo = testService.apiQueryAuth(authorizationCode,appId, appsecret);
						buildingAuthorizer(authorizationInfo, authType, organizationAccount);
						return "success";
					case "unauthorized":
						logger.info("授权公众号取消授权：AuthorizerAppid=" + authorizerAppid);
						if(!"wx570bc396a51b8ff8".equals(authorizerAppid)){
							wXopenPlatformMerchantInfoService.deleteByWXAppId(authorizerAppid);
						}
						return "授权公众号取消授权";
					case "TESTCOMPONENT_MSG_TYPE_TEXT":
						return "TESTCOMPONENT_MSG_TYPE_TEXT_callback";
					case "weapp_audit_success":
						log.info("小程序{}审核成功{}", map.get("ToUserName"), map.get("SuccTime"));
						wXopenPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(map.get(""));
						return "success";
					case "weapp_audit_fail":
						log.info("小程序{}审核失败{}", map.get("ToUserName"), map.get("Reason"));
						return "success";
					default:
						break;
					}
					return "success";
				} else {
					logger.info("微信平台未推送内容到服务器");
					return "success";
				}
			} catch (IOException e) {
				logger.error("接收处理微信平台推送内容异常" + e.getMessage());
				e.printStackTrace();
				return "success";
			}
		} else {
			logger.info("返回的授权code：" + authCode);
			String authorizationInfo = testService.apiQueryAuth(authCode, appId,
					appsecret);
			buildingAuthorizer(authorizationInfo, authType, organizationAccount);
			return "success";
		}
	}

	private String weixinTest(Map<String, String> map, String timeStamp, String nonce)
			throws AesException {
		WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		String msgType = map.get("MsgType");
		if ("gh_3c884a361561".equals(map.get("ToUserName")) || "gh_8dad206e9538".equals(map.get("ToUserName"))) {
			switch (msgType) {
			case "event":
				// 事件处理
				String event = map.get("Event");
				WxMpXmlOutTextMessage eventTextMessage = WxMpXmlOutMessage.TEXT().content(event + "from_callback")
						.toUser(map.get("FromUserName")).fromUser(map.get("ToUserName")).build();
				String result = pc.encryptMsg(eventTextMessage.toXml(), timeStamp, nonce);
				return result;
			case "text":
				String result1 = "";
				if("TESTCOMPONENT_MSG_TYPE_TEXT".equals(map.get("Content"))){
					WxMpXmlOutTextMessage textMessage = WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
							.toUser(map.get("FromUserName")).fromUser(map.get("ToUserName")).build();
					result1 = pc.encryptMsg(textMessage.toXml(), timeStamp, nonce);
				}else if(map.get("Content").contains("QUERY_AUTH_CODE")){
					String context = map.get("Content");
					String query_auth_code = context.substring(context.indexOf(":")+1);
					//异步方法
					String authorizationInfo = testService.apiQueryAuth(query_auth_code, appId, appsecret);
					JSONObject jsono = JSONObject.parseObject(authorizationInfo);
					WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.TEXT().content(query_auth_code+"_from_api").toUser(map.get("FromUserName")).build();
					keFuService.sendKefuMessage(jsono.getString("authorizer_access_token"), wxMpKefuMessage);
					return "";
				}
				return result1;
			default:
				return "";
			}
		}
		return null;
	}
	
	

	@SuppressWarnings("finally")
	@RequestMapping("/event/{APPID}/callBack")
	@ResponseBody
	public String eventCallBack(HttpServletRequest request, @PathVariable(name = "APPID") String APPID,
			@RequestBody String postData) {
		String msgSignature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		logger.info("timestamp={},nonce={},msg_signature={},postData={}",timestamp,nonce,msgSignature,postData);
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			String resultXml = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
			logger.info("微信事件与消息解密后resultXml：" + resultXml);
			if (null != resultXml) {
				Map<String, String> map = WxUtil.readStringXmlOut(resultXml);
				String msgType = map.get("MsgType");
				String event = map.get("Event");
				// 微信自动化测试的专用测试公众号和小程序处理
				String result = weixinTest(map,timestamp,nonce);
				if(null!=result){
					return result;
				}
				// 处理事件消息
				if ("event".equals(msgType)) {
//					String toUserName = map.get("ToUserName");
					// component_verify_ticket:微信平台定时推送；authorized：微信公众号授权第三方平台；unauthorized：微信公众号取消授权第三方平台
					switch (event) {
					case "weapp_audit_success":
						logger.info("微信商家小程序{}审核成功回调", APPID);
						try {
							organizeTemplateService.updateOrganizeTemplateStatus(APPID,
									OrganizeTemplateStatusEnum.SUCCESS.getStatus(),null);
							openPlatformXiaochengxuService.release(APPID);
							organizeTemplateService.updateOrganizeTemplateIsOnline(APPID, "1");
						} catch (Exception e) {
							log.error("微信商家小程序{}更新审核状态为成功失败", APPID, e.getMessage());
						} finally {
							return "success";
						}
					case "weapp_audit_fail":
						logger.info("微信商家小程序{}审核成功回调", APPID);
						try {
							organizeTemplateService.updateOrganizeTemplateStatus(APPID,
									OrganizeTemplateStatusEnum.FAIL.getStatus(),map.get("Reason"));
						} catch (Exception e) {
							log.error("微信商家小程序{}更新审核状态为失败失败", APPID, e.getMessage());
						} finally {
							return "success";
						}
					default:
						return "success";
					}
				} else {
					// 处理通知消息
				}
			} else {
				logger.info("微信平台未推送内容到服务器");
			}
		} catch (Exception e) {
			logger.error("接收处理微信平台推送内容异常" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 公众号小程序授权回调
	 * 
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping("platform/auth/callBack")
	public String platformCallBack(Model model, HttpServletRequest request) {
		String authCode = request.getParameter("auth_code");
		String organizationAccount = request.getParameter("organizationAccount");
		// 授权账户类型1：公众号；2：小程序
		String authType = request.getParameter("authType");
		logger.info("返回的授权code：" + authCode+",authType="+authType);
		String authorizationInfo = testService.apiQueryAuth(authCode, appId, appsecret);
		JsonResult jsonResult = buildingAuthorizer(authorizationInfo, authType, organizationAccount);
		log.info("授权返回结果{}",JSONObject.toJSONString(jsonResult));
		model.addAttribute("authResult", jsonResult.getMessage());
		model.addAttribute("authReason", jsonResult.getData());
		return "/authResult";
	}

	// 组装授权和创建开放平台
	public JsonResult buildingAuthorizer(String authorizationInfo, String authType, String organizationAccount) {
		JSONObject jsono = JSONObject.parseObject(authorizationInfo);
		WXopenPlatformMerchantInfo oldWxInfo = wXopenPlatformMerchantInfoService
				.getByWXAppId(jsono.getString("authorizer_appid"));
		// 换取了调用凭证，必须更新redis数据
		if (null != oldWxInfo && !StringUtils.isEmpty(oldWxInfo.getWxAppid())) {
			oldWxInfo.setAuthoriceAccessToken(jsono.getString("authorizer_access_token"));
			oldWxInfo.setAuthoriceRefreshToken(jsono.getString("authorizer_refresh_token"));
			wXopenPlatformMerchantInfoService.save(oldWxInfo);
			redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY + oldWxInfo.getWxAppid(),
					JSONObject.toJSONString(oldWxInfo), 7000);
		} else {
			redisService.del(Constants.MERCHANT_WX_OPENPLATFORM_KEY + jsono.getString("authorizer_appid"));
		}
		// 获取平台调用凭证
		String component_access_token = testService.getApiComponentToken(appId, appsecret);
		// 获取授权方的详细信息
		String authorizerStr = testService.getAuthorizerInfo(appId, jsono.getString("authorizer_appid"),
				component_access_token);
		JSONObject authorizerJson = JSONObject.parseObject(authorizerStr);
		JSONObject authorizerInfoJson = authorizerJson.getJSONObject("authorizer_info");
		if (Constants.AUTHORIZER_TYPE_GONGZHONGHAO.equals(authType)) {
			if (null == oldWxInfo || StringUtils.isEmpty(oldWxInfo.getWxAppid())) {
				// 创建商户公众号的开放平台
				JsonResult result = testService.createOpen(jsono.getString("authorizer_appid"),
						jsono.getString("authorizer_access_token"));
				if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
					WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(),
							jsono.getString("authorizer_appid"), null, jsono.getString("authorizer_access_token"),
							jsono.getString("authorizer_refresh_token"), "createUser", new Date(), "updateUser",
							new Date(), result.getData().toString(), authType, organizationAccount,
							authorizerInfoJson.getString("nick_name"), authorizerInfoJson.getString("head_img"),
							authorizerInfoJson.getString("verify_type_info"),
							authorizerInfoJson.getString("user_name"));
					wXopenPlatformMerchantInfoService.save(wxInfo);
					// 关联我们的小程序和商家的
					OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService
							.findCanBindXiaochengxu();
					if (null == openPlatformXiaochengxu) {
						return JsonResult.newInstanceAuthFail("平台黄页小程序不存在");
					}
					JSONObject resultJson = testService.bindWxamplink(openPlatformXiaochengxu.getAppId(),
							jsono.getString("authorizer_access_token"));
					JsonResult jsonResult = JsonResult.newInstance(resultJson.getString("errcode"),
							resultJson.getString("errmsg"));
					if (!JsonResult.APP_RETURN_SUCCESS.equals(jsonResult.getCode())) {
						return JsonResult.newInstanceAuthFail("绑定第三方平台小程序失败");
					}
					return JsonResult.newInstanceAuthSuccess("授权成功");
				} else {
					return JsonResult.newInstanceAuthFail("创建公众号开放平台失败");
				}
			} else {
				// 更新权限
				WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(
						Constants.AUTHORIZER_TYPE_XIAOCHENGXU, organizationAccount);
				List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService
						.findByCondition(wxCondition);
				// 当前公众号没有绑定小程序，可以跟换公众号
				if (null == wxInfoResponses) {
					wxCondition.setAuthType(Constants.AUTHORIZER_TYPE_GONGZHONGHAO);
					List<WXopenPlatformMerchantInfo> wxInfoList = wXopenPlatformMerchantInfoService
							.findByCondition(wxCondition);
					// 更新微信公众号需要将之前的删除
					if (null != wxInfoList
							&& !wxInfoList.get(0).getWxAppid().equals(jsono.getString("authorizer_appid"))) {
						wXopenPlatformMerchantInfoService.deleteByWXAppId(wxInfoList.get(0).getWxAppid());
					}
					// 创建商户公众号的开放平台
					JsonResult result = testService.createOpen(jsono.getString("authorizer_appid"),
							jsono.getString("authorizer_access_token"));
					if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
						WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(),
								jsono.getString("authorizer_appid"), null, jsono.getString("authorizer_access_token"),
								jsono.getString("authorizer_refresh_token"), "createUser", new Date(), "updateUser",
								new Date(), result.getData().toString(), authType, organizationAccount,
								authorizerInfoJson.getString("nick_name"), authorizerInfoJson.getString("head_img"),
								authorizerInfoJson.getString("verify_type_info"),
								authorizerInfoJson.getString("user_name"));
						wXopenPlatformMerchantInfoService.save(wxInfo);
						// 关联我们的小程序和商家的
						OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService
								.findCanBindXiaochengxu();
						if (null == openPlatformXiaochengxu) {
							return JsonResult.newInstanceAuthFail("更新授权，平台黄页小程序不存在");
						}
						testService.bindWxamplink(openPlatformXiaochengxu.getAppId(),
								jsono.getString("authorizer_access_token"));
						return JsonResult.newInstanceAuthSuccess("授权成功");
					} else {
						return JsonResult.newInstanceAuthFail("创建公众号开放平台失败");
					}
				} else if (oldWxInfo.getWxAppid().equals(jsono.getString("authorizer_appid"))) {
					// 当前已绑定小程序，只能更新权限，不能更改公众号
					return JsonResult.newInstanceAuthSuccess("更新公众号授权成功");
				} else {
					return JsonResult.newInstanceAuthFail("更换公众号有可能造成数据丢失，请联系【旺小宝】更换");
				}
			}
		} else {
			// 小程序授权
			WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(
					Constants.AUTHORIZER_TYPE_GONGZHONGHAO, organizationAccount);
			List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService
					.findByCondition(wxCondition);
			if (null == wxInfoResponses || wxInfoResponses.size() <= 0) {
				return JsonResult.newInstanceAuthFail("授权小程序之前需要授权公众号");
			} else if (null != oldWxInfo && !oldWxInfo.getWxAppid().equals(jsono.getString("authorizer_appid"))) {
				return JsonResult.newInstanceAuthFail("更换小程序可能造成用户数据丢失,请联系【旺小宝】更换");
			}
			JsonResult result = testService.bindOpen(jsono.getString("authorizer_appid"),
					wxInfoResponses.get(0).getOpenAppid(), jsono.getString("authorizer_access_token"));
			if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
				String wxAppid = "";
				if (null == oldWxInfo) {
					WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(),
							jsono.getString("authorizer_appid"), null, jsono.getString("authorizer_access_token"),
							jsono.getString("authorizer_refresh_token"), "createUser", new Date(), "updateUser",
							new Date(), wxInfoResponses.get(0).getOpenAppid(), authType, organizationAccount,
							authorizerInfoJson.getString("nick_name"), authorizerInfoJson.getString("head_img"),
							authorizerInfoJson.getString("verify_type_info"),
							authorizerInfoJson.getString("user_name"));
					wXopenPlatformMerchantInfoService.save(wxInfo);
					wxAppid = wxInfo.getWxAppid();
				} else {
					wxAppid = oldWxInfo.getWxAppid();
				}
				openPlatformXiaochengxuService.initXiaochengxu(wxAppid, "add", organizationAccount,
						authorizerInfoJson.getString("nick_name"));
			}
			return result;
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
		String organizationAccount = request.getParameter("organizationAccount");
		if (StringUtils.isEmpty(authType) || StringUtils.isEmpty(organizationAccount)) {
			log.error("微信公众号管理者授权入口【参数异常】");
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		// if(!plateformOrgUserInfo.getOrgId().equals(organizationAccount)){
		// log.error("当前用户没有此权限");
		// throw new CommonException(ResultEnum.PARAM_ERROR);
		// }
		if (StringUtils.isEmpty(authType)) {
			authType = "3";
		}

		StringBuffer sbUrl = new StringBuffer();
		sbUrl.append("https://mp.weixin.qq.com/cgi-bin/componentloginpage?component_appid=" + appId + "&pre_auth_code="
				+ getPreAuthCode());
		sbUrl.append("&auth_type=").append(authType);
		String redirectUri = URIUtil
				.encodeURIComponent(redirectUrl + "/wechatgateway/platform/auth/callBack?organizationAccount="
						+ organizationAccount + "&authType=" + authType);
		// redirectUrl + "/index/auth/callBack?organizationAccount=" +
		// organizationAccount +
		// "&authType=" + authType);
		sbUrl.append("&redirect_uri=").append(redirectUri);
		model.addAttribute("redirectUrl", sbUrl.toString());
		return "/test";
	}

	/**
	 * @methodName: getAuthorizerInfo @Description: 获取授权方的账号基本信息 @param
	 *              wxAppId @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月13日 下午2:16:04 @updateUser:
	 *              liping_max @updateDate: 2018年1月13日 下午2:16:04 @throws
	 */
	@RequestMapping("/wxAuth/getAuthorizerInfo")
	@ResponseBody
	public JsonResult getAuthorizerInfo(String wxAppId) {
		String authoriceAccessToken = testService.getApiComponentToken(appId, appsecret);
		String result = testService.getAuthorizerInfo(appId, wxAppId, authoriceAccessToken);
		return JsonResult.newInstanceDataSuccess(JSONObject.parseObject(result));
	}

	@RequestMapping("/wxAuth/getMiniprogramAuthorizerInfo")
	@ResponseBody
	public JsonResult getMiniprogramAuthorizerInfo(String wxAppId) {
		String resultStr = testService.getMiniprogramAuthorizerInfo(wxAppId);
		JSONObject json = JSONObject.parseObject(resultStr);
		return JsonResult.newInstanceDataSuccess(json);
	}

	/**
	 * @methodName: unauthorized @Description: 取消授权 @param wxAppId @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年1月13日
	 *              下午2:04:52 @updateUser: liping_max @updateDate: 2018年1月13日
	 *              下午2:04:52 @throws
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
				.getWXopenPlatformMerchantInfo(wxAppid);
		return testService.createOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
	}

	/**
	 * 商户公众号与开放平台绑定
	 * 
	 * @param wxAppid
	 * @param openAppid
	 * @return
	 */
	@RequestMapping("/gongzhonghao/bindOpen")
	@ResponseBody
	public JsonResult bindOpen(String wxAppid, String openAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid);
		return testService.bindOpen(wxAppid, openAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
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
				.getWXopenPlatformMerchantInfo(wxAppid);
		JsonResult jsonResult = testService.unbindOpen(wxAppid, openAppid,
				wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
		return jsonResult;
	}

	/**
	 * @methodName: getBindOpen @Description: 获取商户微信号或公众号绑定的开放平台信息 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月13日 下午4:24:56 @updateUser:
	 *              liping_max @updateDate: 2018年1月13日 下午4:24:56 @throws
	 */
	@RequestMapping("/gongzhonghao/getBindOpen")
	@ResponseBody
	public JsonResult getBindOpen(String wxAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid);
		return testService.getBindOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
	}

	/**
	 * @methodName: getBindOpen @Description: 绑定我们的小程序到商家的公众号 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月13日 下午4:24:56 @updateUser:
	 *              liping_max @updateDate: 2018年1月13日 下午4:24:56 @throws
	 */
	@RequestMapping("/gongzhonghao/bindWxamplink")
	@ResponseBody
	public JsonResult bindWxamplink(String wxAppid, String xcxAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid);
		JSONObject resultJson = testService.bindWxamplink(xcxAppid,
				wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
		return JsonResult.newInstance(resultJson.getString("errcode"), resultJson.getString("errmsg"));
	}

	/**
	 * @methodName: getBindOpen @Description: 解绑我们的小程序和商家的公众号 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月13日 下午4:24:56 @updateUser:
	 *              liping_max @updateDate: 2018年1月13日 下午4:24:56 @throws
	 */
	@RequestMapping("/gongzhonghao/unbindWxampunlink")
	@ResponseBody
	public JsonResult unbindWxampunlink(String wxAppid, String xcxAppid) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid);
		JSONObject resultJson = testService.bindWxamplink(xcxAppid,
				wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
		return JsonResult.newInstance(resultJson.getString("errcode"), resultJson.getString("errmsg"));
	}

	@RequestMapping("platform/auth/test")
	public String test(Model model, HttpServletRequest request) {
		model.addAttribute("authResult", "");
		return "/authResult1";
	}
}