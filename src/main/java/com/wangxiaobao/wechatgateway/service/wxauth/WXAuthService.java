package com.wangxiaobao.wechatgateway.service.wxauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.properties.WxProperties;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(WxProperties.class)
@Service
public class WXAuthService {
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	private RedisService redisService;

	/**
	 * @methodName: getPreAuthCode @Description: 获取第三方平台的预授权码 @return
	 *              String @createUser: liping_max @createDate: 2017年9月13日
	 *              下午7:24:30 @updateUser: liping_max @updateDate: 2017年9月13日
	 *              下午7:24:30 @throws
	 */
	public String getPreAuthCode() {
		String componentAccessToken = getApiComponentToken(appId, appsecret);
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
	 * @methodName: apiQueryAuth @Description: 使用授权码换取公众号或小程序的接口调用凭据和授权信息 @param
	 *              authCode @return String @createUser: liping_max @createDate:
	 *              2017年9月13日 下午7:26:09 @updateUser: liping_max @updateDate:
	 *              2017年9月13日 下午7:26:09 @throws
	 */
	public String apiQueryAuth(String authCode, String appId, String appsecret) {
		String component_access_token = getApiComponentToken(appId, appsecret);
		String url = "https://api.weixin.qq.com/cgi-bin/component/api_query_auth?component_access_token="
				+ component_access_token;
		JSONObject jsonO = new JSONObject();
		jsonO.put("component_appid", appId);
		jsonO.put("authorization_code", authCode);
		String StrResult = HttpClientUtils.executeByJSONPOST(url, jsonO.toJSONString(), 5000);
		JSONObject jsonResult = JSONObject.parseObject(StrResult);
		String authorizationInfo = jsonResult.getString("authorization_info");
		JSONObject jsono = JSONObject.parseObject(authorizationInfo);
		if (ObjectUtils.isEmpty(jsono.getString("authorizer_access_token"))
				|| ObjectUtils.isEmpty(jsono.getString("authorizer_refresh_token"))) {
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), "获取商家公众号或小程序调用凭证异常:"+authorizationInfo);
		}
		return authorizationInfo;
	}

	/**
	 * @methodName: getApiComponentToken @Description:
	 *              获取第三方开放平台的component_access_token @return String @createUser:
	 *              liping_max @createDate: 2017年9月13日 下午7:23:45 @updateUser:
	 *              liping_max @updateDate: 2017年9月13日 下午7:23:45 @throws
	 */
	public String getApiComponentToken(String appId, String appsecret) {
		String apiComponentToken = redisService.get(Constants.OPENPLATFORM_COMPONENT_ACCESS_TOKEN);
		if (null != apiComponentToken) {
			return apiComponentToken;
		} else {
			JSONObject jsonO = new JSONObject();
			jsonO.put("component_appid", appId);
			jsonO.put("component_appsecret", appsecret);
			jsonO.put("component_verify_ticket", redisService.get("componentVerifyTicket"));
			String StrResult = HttpClientUtils.executeByJSONPOST(
					"https://api.weixin.qq.com/cgi-bin/component/api_component_token", jsonO.toJSONString(), 5000);
			JSONObject jsonResult = JSONObject.parseObject(StrResult);
			String componentAccessToken = jsonResult.getString("component_access_token");
			redisService.set(Constants.OPENPLATFORM_COMPONENT_ACCESS_TOKEN, componentAccessToken, 7200);
			apiComponentToken = componentAccessToken;
			return componentAccessToken;
		}
	}

}
