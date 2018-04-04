package com.wangxiaobao.wechatgateway.controller.wxauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.service.kefu.KeFuService;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.service.weixinapi.WXApiService;
import com.wangxiaobao.wechatgateway.service.wxauth.WXAuthService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.WxUtil;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.util.http.URIUtil;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutTextMessage;
/**
  * @ProjectName: wechatgateway 
  * @PackageName: com.wangxiaobao.wechatgateway.controller.wxauth 
  * @ClassName: WXAuthController
  * @Description: TODO 微信授权controller
  * @Copyright: Copyright (c) 2018  ALL RIGHTS RESERVED.
  * @Company:成都国胜天丰技术有限责任公司
  * @author liping_max
  * @date 2018年3月28日 上午11:24:32
 */
@Controller
@Slf4j
public class WXAuthController {
	@Value("${wechat.openplatform.appid}")
	private String appId;
	@Value("${wechat.openplatform.appsecret}")
	private String appsecret;
	@Value("${wechat.openplatform.redirectUrl}")
	private String redirectUrl;
	@Value("${wechat.openplatform.token}")
	private String token;
	@Value("${wechat.openplatform.encodingAesKey}")
	private String encodingAesKey;
	@Autowired
	private WXApiService wXApiService;
	@Autowired
	private RedisService redisService;
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private OrganizeTemplateService organizeTemplateService;
	@Autowired
	private KeFuService keFuService;
	@Autowired
	private WXAuthService wXAuthService;
	
	@RequestMapping("platform/auth/test")
	public String test(Model model, HttpServletRequest request) {
		model.addAttribute("authResult", "");
		return "/authResult1";
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
				+ wXAuthService.getPreAuthCode());
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
				String resultXml = wXApiService.decodeStr(inputStream, msgSignature, timestamp, nonce, appId);
				log.info("微信推送解密后resultXml：" + resultXml);
				if (null != resultXml) {
					Map<String, String> map = WxUtil.readStringXmlOut(resultXml);
					String infoType = map.get("InfoType");
					String authorizerAppid = map.get("AuthorizerAppid");
					// component_verify_ticket:微信平台定时推送；authorized：微信公众号授权第三方平台；unauthorized：微信公众号取消授权第三方平台
					switch (infoType) {
					case "component_verify_ticket":
						String ticket = map.get("ComponentVerifyTicket");
						log.info("微信推送获取ticket值：" + ticket);
						redisService.set("componentVerifyTicket", ticket);
						return "success";
					case "authorized":
						String authorizationCode = map.get("AuthorizationCode");
						log.info("返回的授权code：" + authorizationCode);
						String authorizationInfo = wXAuthService.apiQueryAuth(authorizationCode, appId, appsecret);
						WXopenPlatformMerchantInfo wXInfo = wXApiService.saveOrUpdateWxopenPlatformMerchantInfoFromAuth(authorizationInfo, authType, organizationAccount);
						wXApiService.buildingAuthorizer(wXInfo.getWxAppid());
						return "success";
					case "unauthorized":
						log.info("授权公众号取消授权：AuthorizerAppid=" + authorizerAppid);
						if (!"wx570bc396a51b8ff8".equals(authorizerAppid)
								&& !"wxd101a85aa106f53e".equals(authorizerAppid)) {
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
					log.info("微信平台未推送内容到服务器");
					return "success";
				}
			} catch (IOException e) {
				log.error("接收处理微信平台推送内容异常" + e.getMessage());
				e.printStackTrace();
				return "success";
			}
		} else {
			log.info("返回的授权code：" + authCode);
			String authorizationInfo = wXAuthService.apiQueryAuth(authCode, appId, appsecret);
			wXApiService.buildingAuthorizer(authorizationInfo);
			return "success";
		}
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
		log.info("返回的授权code：" + authCode + ",authType=" + authType);
		String authorizationInfo = wXAuthService.apiQueryAuth(authCode, appId, appsecret);
		WXopenPlatformMerchantInfo wXInfo = wXApiService.saveOrUpdateWxopenPlatformMerchantInfoFromAuth(authorizationInfo, authType, organizationAccount);
		JsonResult jsonResult = wXApiService.buildingAuthorizer(wXInfo.getWxAppid());
		log.info("授权返回结果{}", JSONObject.toJSONString(jsonResult));
		model.addAttribute("authResult", jsonResult.getMessage());
		model.addAttribute("authReason", jsonResult.getData());
		return "/authResult";
	}
	
	@SuppressWarnings("finally")
	@RequestMapping("/event/{APPID}/callBack")
	@ResponseBody
	public String eventCallBack(HttpServletRequest request, @PathVariable(name = "APPID") String APPID,
			@RequestBody String postData) {
		String msgSignature = request.getParameter("msg_signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		log.info("timestamp={},nonce={},msg_signature={},postData={}", timestamp, nonce, msgSignature, postData);
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			String resultXml = pc.decryptMsg(msgSignature, timestamp, nonce, postData);
			log.info("微信事件与消息解密后resultXml：" + resultXml);
			if (null != resultXml) {
				Map<String, String> map = WxUtil.readStringXmlOut(resultXml);
				String msgType = map.get("MsgType");
				String event = map.get("Event");
				// 微信自动化测试的专用测试公众号和小程序处理
				String result = weixinTest(map, timestamp, nonce);
				if (null != result) {
					return result;
				}
				// 处理事件消息
				if ("event".equals(msgType)) {
					// String toUserName = map.get("ToUserName");
					// component_verify_ticket:微信平台定时推送；authorized：微信公众号授权第三方平台；unauthorized：微信公众号取消授权第三方平台
					switch (event) {
					case "weapp_audit_success":
						log.info("微信商家小程序{}审核成功回调", APPID);
						try {
							organizeTemplateService.updateOrganizeTemplateStatus(APPID,
									OrganizeTemplateStatusEnum.SUCCESS.getStatus(), null);
							openPlatformXiaochengxuService.release(APPID);
							organizeTemplateService.updateOrganizeTemplateIsOnline(APPID, "1");
						} catch (Exception e) {
							log.error("微信商家小程序{}更新审核状态为成功失败", APPID, e.getMessage());
						} finally {
							return "success";
						}
					case "weapp_audit_fail":
						log.info("微信商家小程序{}审核成功回调", APPID);
						try {
							organizeTemplateService.updateOrganizeTemplateStatus(APPID,
									OrganizeTemplateStatusEnum.FAIL.getStatus(), map.get("Reason"));
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
				log.info("微信平台未推送内容到服务器");
			}
		} catch (Exception e) {
			log.error("接收处理微信平台推送内容异常" + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	private String weixinTest(Map<String, String> map, String timeStamp, String nonce) throws AesException {
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
				if ("TESTCOMPONENT_MSG_TYPE_TEXT".equals(map.get("Content"))) {
					WxMpXmlOutTextMessage textMessage = WxMpXmlOutMessage.TEXT()
							.content("TESTCOMPONENT_MSG_TYPE_TEXT_callback").toUser(map.get("FromUserName"))
							.fromUser(map.get("ToUserName")).build();
					result1 = pc.encryptMsg(textMessage.toXml(), timeStamp, nonce);
				} else if (map.get("Content").contains("QUERY_AUTH_CODE")) {
					String context = map.get("Content");
					String query_auth_code = context.substring(context.indexOf(":") + 1);
					// 异步方法
					String authorizationInfo = wXAuthService.apiQueryAuth(query_auth_code, appId, appsecret);
					JSONObject jsono = JSONObject.parseObject(authorizationInfo);
					WxMpKefuMessage wxMpKefuMessage = WxMpKefuMessage.TEXT().content(query_auth_code + "_from_api")
							.toUser(map.get("FromUserName")).build();
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
	
	/**
	  * @methodName: continueAuth
	  * @Description: 微信授权回调之后流程出错，可调用此接口继续后续流程
	  * @param wxAppId
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年3月30日 上午9:54:11
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月30日 上午9:54:11
	  * @throws
	 */
	@RequestMapping("platform/auth/continueAuth")
	public@ResponseBody JsonResult continueAuth(@RequestParam(value="wxAppId") String wxAppId){
		return wXApiService.buildingAuthorizer(wxAppId);
	}
}
