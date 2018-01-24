package com.wangxiaobao.wechatgateway.service.test;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramAuthInfoResponse;
import com.wangxiaobao.wechatgateway.properties.WxProperties;
import com.wangxiaobao.wechatgateway.repository.miniprogramtemplate.WxMiniprogramTemplateRepository;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrganizeTemplateRepository;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@EnableConfigurationProperties(WxProperties.class)
@Service
public class TestService {
	Logger logger = LoggerFactory.getLogger(TestService.class);
	@Autowired
	RedisService redisService;
	@Autowired
	WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	WxProperties wxProperties;
	@Autowired
	private OrganizeTemplateRepository organizeTemplateRepository;
	@Autowired
	private WxMiniprogramTemplateRepository wxMiniprogramTemplateRepository;

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

		// TODO
		String authorizationInfo = jsonResult.getString("authorization_info");
		return authorizationInfo;
	}

	
	/*  public JsonResult bing(){ JSONObject jsono =
	  JSONObject.parseObject(authorizationInfo); WXopenPlatformMerchantInfo
	  oldWxInfo =
	  wXopenPlatformMerchantInfoService.getByWXAppId(jsono.getString(
	  "authorizer_appid")); // 先删除数据库关于这个appId存在的记录，然后再添加新的
	  wXopenPlatformMerchantInfoService.deleteByWXAppId(jsono.getString(
	  "authorizer_appid")); WXopenPlatformMerchantInfoForm wxForm = new
	  WXopenPlatformMerchantInfoForm(); wxForm.setAuthType(authType);
	  wxForm.setorganizationAccount(organizationAccount);
	  wXopenPlatformMerchantInfoService.deleteByorganizationAccountAndAuthType(wxForm);
	  
	  WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
	  wxInfo.setAuthoriceRefreshToken(jsono.getString(
	  "authorizer_refresh_token"));
	 * wxInfo.setAuthoriceAccessToken(jsono.getString("authorizer_access_token")
	 * ); wxInfo.setCreateDate(new Date());
	 * wxInfo.setWxAppid(jsono.getString("authorizer_appid"));
	 * wxInfo.setWxOpenPlatformId(CreateUUID.getUuid());
	 * wxInfo.setAuthType(authType); wxInfo.setorganizationAccount(organizationAccount);
	 * wxInfo.setOpenAppid(oldWxInfo.getOpenAppid());
	 * wXopenPlatformMerchantInfoService.save(wxInfo);
	 * redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY +
	 * jsono.getString("authorizer_appid"), JSONObject.toJSONString(wxInfo),
	 * 7200); //
	 * 这里获取到authorizer_refresh_token需要保存起来，定时2小时左右去刷新authorizer_access_token //
	 * 授权方的appid //给商户公众号创建开放平台
	 * if(Constants.AUTHORIZER_TYPE_GONGZHONGHAO.equals(authType)&&StringUtils.
	 * isEmpty(wxInfo.getOpenAppid())){ JsonResult result =
	 * createOpen(wxInfo.getWxAppid(), component_access_token); }else{
	 * bindOpen(wxInfo.getWxAppid(), openAppid, authoriceAccessToken) } }*/
	 

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

	/**
	 * @methodName: decodeStr @Description: 微信消息aes解码 @param inputStream @param
	 *              msgSignature @param timestamp @param nonce @return
	 *              String @createUser: liping_max @createDate: 2017年9月13日
	 *              下午7:25:38 @updateUser: liping_max @updateDate: 2017年9月13日
	 *              下午7:25:38 @throws
	 */
	public String decodeStr(InputStream inputStream, String msgSignature, String timestamp, String nonce,
			String appId) {
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(wxProperties.getToken(), wxProperties.getEncodingAesKey(), appId);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource(inputStream);
			Document document = db.parse(is);

			Element root = document.getDocumentElement();
			NodeList nodelist1 = root.getElementsByTagName("Encrypt");

			String encrypt = nodelist1.item(0).getTextContent();
			logger.info("微信推送encrypt：" + encrypt);

			String format = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></xml>";
			String fromXML = String.format(format, encrypt);
			logger.info("微信推送fromXML：" + fromXML);
			//
			// 公众平台发送消息给第三方，第三方处理
			//
			// 第三方收到公众号平台发送的消息
			String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);
			System.out.println("解密后明文: " + result2);
			return result2;
		} catch (Exception e) {
			logger.info("解析xml错误：" + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @methodName: createOpen @Description: 用第三方平台帮助商家公众号创建开放平台 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午9:57:25 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午9:57:25 @throws
	 */
	public JsonResult createOpen(String wxAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/open/create?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsono = JSONObject.parseObject(result);
		if ("0".equals(jsono.getString("errcode"))) {
			return JsonResult.newInstanceDataSuccess(jsono.getString("open_appid"));
		} else if("89000".equals(jsono.getString("errcode"))||"48001".equals(jsono.getString("errcode"))){
			JsonResult result1 = getBindOpen(wxAppid,authoriceAccessToken);
			if(JsonResult.APP_RETURN_SUCCESS.equals(result1.getCode())){
				JSONObject jsonO = (JSONObject) result1.getData();
				return JsonResult.newInstanceDataSuccess(jsonO.getString("open_appid"));
			}
		}else{
			log.error("授权失败，商家公众号创建开放平台【】【】",wxAppid,result);
			throw new CommonException(jsono.getInteger("errcode"), "授权失败"+jsono.getString("errmsg"));
		}
		return JsonResult.newInstanceMesFail(jsono.getString("errcode"));
	}

	/**
	 * @methodName: unbindOpen @Description: 商户公众号与开放平台解绑 @param wxAppid @param
	 *              openAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午11:00:12 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午11:00:12 @throws
	 */
	public JsonResult unbindOpen(String wxAppid, String openAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/open/unbind?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		params.put("open_appid", openAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsono = JSONObject.parseObject(result);
		if ("0".equals(jsono.getString("errcode"))) {
			WXopenPlatformMerchantInfo wInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppid);
			wInfo.setOpenAppid(null);
			wXopenPlatformMerchantInfoService.update(wInfo);
			return JsonResult.newInstanceSuccess();
		} else {
			return JsonResult.newInstanceMesFail(jsono.getString("errcode"));
		}
	}

	/**
	 * @methodName: bindOpen @Description: 绑定微信公众号或小程序到商家开放平台 @param
	 * wxAppid @param openAppid @param authoriceAccessToken @return
	 * JsonResult @createUser: liping_max @createDate: 2018年1月13日
	 * 下午4:20:37 @updateUser: liping_max @updateDate: 2018年1月13日
	 * 下午4:20:37 @throws
	 */
	public JsonResult bindOpen(String wxAppid, String openAppid, String authoriceAccessToken) {
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppid);
		// 为空才进行绑定
		if (null==wxInfo||null == wxInfo.getOpenAppid()) {
			String url = "https://api.weixin.qq.com/cgi-bin/open/bind?access_token=";
			url += authoriceAccessToken;
			JSONObject params = new JSONObject();
			params.put("appid", wxAppid);
			params.put("open_appid", openAppid);
			String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
			JSONObject jsono = JSONObject.parseObject(result);
			if ("0".equals(jsono.getString("errcode"))) {
				/*WXopenPlatformMerchantInfo wInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppid);
				wInfo.setOpenAppid(openAppid);
				wXopenPlatformMerchantInfoService.update(wInfo);*/
				return JsonResult.newInstanceMesSuccess("授权成功");
			} else if("89000".equals(jsono.getString("errcode"))){
				JsonResult result1 = getBindOpen(wxAppid, authoriceAccessToken);
				if(JsonResult.APP_RETURN_SUCCESS.equals(result1.getCode())){
					JSONObject jsonO = (JSONObject) result1.getData();
					if(openAppid.equals(jsonO.getString("open_appid"))){
						return JsonResult.newInstanceMesSuccess("授权成功");
					}else{
						//解绑
						JsonResult jsonr = unbindOpen(wxAppid,jsonO.getString("open_appid"),authoriceAccessToken);
						if("0".equals(jsonr.getCode())){
							bindOpen(wxAppid,openAppid,authoriceAccessToken);
							return JsonResult.newInstanceMesSuccess("授权成功");
						}
						return JsonResult.newInstanceMesFail("授权失败已绑定的开放平台和现有开放平台不一致");
					}
				}
				return JsonResult.newInstanceMesFail("授权失败"+jsono.toJSONString());
			}else{
				return JsonResult.newInstanceMesFail("授权失败"+jsono.toJSONString());
			}
		} else if (wxAppid.equals(wxInfo.getWxAppid())) {
			return JsonResult.newInstanceMesSuccess("授权成功");
		} else {
			logger.info("该小程序已经绑定了其它开放平台");
			return JsonResult.newInstanceMesFail("授权失败");
		}
	}

	public JsonResult getBindOpen(String wxAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/open/get?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsono = JSONObject.parseObject(result);
		if ("0".equals(jsono.getString("errcode"))) {
			return JsonResult.newInstanceDataSuccess(jsono);
		} else {
			return JsonResult.newInstanceMesFail(jsono.getString("errcode"));
		}
	}
	
	/**
	  * @methodName: getAuthorizerInfo
	  * @Description: TODO获取授权方详情
	  * @param componentAppid
	  * @param authorizerAppid
	  * @param authoriceAccessToken
	  * @return String
	  * @createUser: liping_max
	  * @createDate: 2018年1月17日 下午8:09:23
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月17日 下午8:09:23
	  * @throws
	 */
	public String getAuthorizerInfo(String componentAppid,String authorizerAppid,String authoriceAccessToken){
		String authorizerInfoUrl = "https://api.weixin.qq.com/cgi-bin/component/api_get_authorizer_info?component_access_token="
				+ authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("component_appid", componentAppid);
		params.put("authorizer_appid", authorizerAppid);
		String result = HttpClientUtils.executeByJSONPOST(authorizerInfoUrl, params.toJSONString(), 50000);
		return result;
	}
	
	/**
	 * 绑定小程序与商家公众号
	 * @param wxAppid
	 * @param authoriceAccessToken
	 * @return
	 */
	public JSONObject bindWxamplink(String wxAppid,String authoriceAccessToken){
		String url = "https://api.weixin.qq.com/cgi-bin/wxopen/wxamplink?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		params.put("notify_users", "0");
		params.put("show_profile", "1");
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsonO = JSONObject.parseObject(result);
		if(!"0".equals(jsonO.getString("errcode"))&&!"89010".equals(jsonO.getString("errcode"))){
			log.error("授权失败,绑定第三方平台小程序失败");
			throw new CommonException(jsonO.getInteger("errcode"), "授权失败:"+jsonO.getString("errmsg"));
		}
		jsonO.put("errcode", "0");
		return jsonO;
	}
	
	/**
	 * 解绑小程序与商家公众号
	 * @param wxAppid
	 * @param authoriceAccessToken
	 * @return
	 */
	public String bindWxampunlink(String wxAppid,String authoriceAccessToken){
		String url = "https://api.weixin.qq.com/cgi-bin/wxopen/wxampunlink?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		return result;
	}
	
	public String getMiniprogramAuthorizerInfo(String wxAppId){
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_SEC_FORMAT);
		MiniProgramAuthInfoResponse response = new MiniProgramAuthInfoResponse();
		List<OrganizeTemplate> organizeTemplates = organizeTemplateRepository.findByorganizationAccountOrIsNew(wxInfo.getOrganizationAccount(), "1");
		if (null != organizeTemplates && organizeTemplates.size() <= 0) {
			return new JSONObject().toJSONString();
		} else if (organizeTemplates.size() == 1) {
			OrganizeTemplate organizeTemplate = organizeTemplates.get(0);
			// 当前版本是最新版本
			response.setNickName(wxInfo.getNickName());
			// 查询版本
			WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateRepository.findByTemplateId(organizeTemplate.getTemplateId());
			if (organizeTemplate.getIsOnline().equals("1")) {
				// 已发布
				response.setUserVersion(wxMiniprogramTemplate.getUserVersion());
				response.setStatus("线上版本为最新版");
				response.setUpdateDate(sdf.format(organizeTemplate.getUpdateDate()));
			} else {
				// 未发布
				if (organizeTemplate.getStatus().equals(OrganizeTemplateStatusEnum.AUDITING.getStatus())) {
					response.setUserVersion("正在等待微信审核");
					response.setStatus("无");
					response.setUpdateDate("无");
				} else if (organizeTemplate.getStatus().equals(OrganizeTemplateStatusEnum.FAIL.getStatus())) {
					response.setUserVersion("微信审核失败");
					// TODO 需要查询数据库
					response.setStatusMessage("微信认为你不帅");
					response.setStatus("无");
					response.setUpdateDate("无");
				}
			}
			response.setUpdateMiniprogramTemplateId(organizeTemplate.getMiniprogramTemplateId());
		} else {
			response.setNickName(wxInfo.getNickName());
			for (OrganizeTemplate organizeT : organizeTemplates) {
				if (organizeT.getIsOnline().equals("1")) {
					// 查询版本
					WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateRepository
							.findByTemplateId(organizeT.getTemplateId());
					response.setUserVersion(wxMiniprogramTemplate.getUserVersion());
				} else {
					WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateRepository
							.findByTemplateId(organizeT.getTemplateId());
					String statueName = "";
					if (organizeT.getStatus().equals(OrganizeTemplateStatusEnum.AUDITING.getStatus())) {
						statueName = "正在等待微信审核";
					} else if (organizeT.getStatus().equals(OrganizeTemplateStatusEnum.SUCCESS.getStatus())) {
						statueName = "微信审核成功";
					} else if (organizeT.getStatus().equals(OrganizeTemplateStatusEnum.UPLOAD.getStatus())) {
						statueName = "平台已上传，等待提交审核";
					} else if (organizeT.getStatus().equals(OrganizeTemplateStatusEnum.FAIL.getStatus())) {
						statueName = "微信审核失败";
						response.setStatusMessage("审核异常");
					}
					response.setUpdateDate(sdf.format(wxMiniprogramTemplate.getUpdateDate()));
					response.setStatus("最新版本(" + wxMiniprogramTemplate.getUserVersion() + ")微信" + statueName);
				}
			}
		}
		return JSONObject.toJSONString(response);
	}
}
