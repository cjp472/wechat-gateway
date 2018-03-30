package com.wangxiaobao.wechatgateway.service.weixinapi;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.OrganizationAuthType;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoSearchCondition;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramAuthInfoResponse;
import com.wangxiaobao.wechatgateway.properties.WxProperties;
import com.wangxiaobao.wechatgateway.repository.miniprogramtemplate.WxMiniprogramTemplateRepository;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrganizeTemplateRepository;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.service.wxauth.WXAuthService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.CreateUUID;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableConfigurationProperties(WxProperties.class)
@Service
public class WXApiService {
	Logger logger = LoggerFactory.getLogger(WXApiService.class);
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	WxProperties wxProperties;
	@Autowired
	private RedisService redisService;
	@Autowired
	private OrganizeTemplateRepository organizeTemplateRepository;
	@Autowired
	private WxMiniprogramTemplateRepository wxMiniprogramTemplateRepository;
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private WXAuthService wXAuthService;

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
		} else if ("89000".equals(jsono.getString("errcode")) || "48001".equals(jsono.getString("errcode"))) {
			JsonResult result1 = getBindOpen(wxAppid, authoriceAccessToken);
			if (JsonResult.APP_RETURN_SUCCESS.equals(result1.getCode())) {
				JSONObject jsonO = (JSONObject) result1.getData();
				return JsonResult.newInstanceDataSuccess(jsonO.getString("open_appid"));
			}
		} else {
			log.error("授权失败，商家公众号创建开放平台{}{}", wxAppid, result);
			throw new CommonException(jsono.getInteger("errcode"), "授权失败" + jsono.getString("errmsg"));
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
	 *              wxAppid @param openAppid @param authoriceAccessToken @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年1月13日
	 *              下午4:20:37 @updateUser: liping_max @updateDate: 2018年1月13日
	 *              下午4:20:37 @throws
	 */
	public JsonResult bindOpen(String wxAppid, String openAppid, String authoriceAccessToken) {
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppid);
		// 为空才进行绑定
		if (null == wxInfo || null == wxInfo.getOpenAppid()) {
			String url = "https://api.weixin.qq.com/cgi-bin/open/bind?access_token=";
			url += authoriceAccessToken;
			JSONObject params = new JSONObject();
			params.put("appid", wxAppid);
			params.put("open_appid", openAppid);
			String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
			JSONObject jsono = JSONObject.parseObject(result);
			if ("0".equals(jsono.getString("errcode"))) {
				/*
				 * WXopenPlatformMerchantInfo wInfo =
				 * wXopenPlatformMerchantInfoService.getByWXAppId(wxAppid);
				 * wInfo.setOpenAppid(openAppid);
				 * wXopenPlatformMerchantInfoService.update(wInfo);
				 */
				return JsonResult.newInstanceAuthSuccess("小程序首次绑定开放平台成功");
			} else if ("89000".equals(jsono.getString("errcode"))) {
				JsonResult result1 = getBindOpen(wxAppid, authoriceAccessToken);
				if (JsonResult.APP_RETURN_SUCCESS.equals(result1.getCode())) {
					JSONObject jsonO = (JSONObject) result1.getData();
					if (openAppid.equals(jsonO.getString("open_appid"))) {
						return JsonResult.newInstanceAuthSuccess("授权小程序已绑定开放平台和本次操作开放平台相同");
					} else {
						// 解绑
						JsonResult jsonr = unbindOpen(wxAppid, jsonO.getString("open_appid"), authoriceAccessToken);
						if ("0".equals(jsonr.getCode())) {
							bindOpen(wxAppid, openAppid, authoriceAccessToken);
							return JsonResult.newInstanceAuthSuccess("小程序解绑开放平台后重新绑定新开放平台成功");
						}
						return JsonResult.newInstanceAuthFail("授权失败已绑定的开放平台和现有开放平台不一致,解绑失败");
					}
				}
				return JsonResult.newInstanceAuthFail("授权失败" + jsono.toJSONString());
			} else {
				return JsonResult.newInstanceAuthFail("授权失败" + jsono.toJSONString());
			}
		} else if (wxAppid.equals(wxInfo.getWxAppid())) {
			return JsonResult.newInstanceAuthSuccess("当前操作小程序已存在记录，不做任何更改，默认成功");
		} else {
			logger.info("该小程序已经绑定了其它开放平台");
			return JsonResult.newInstanceAuthFail("当前绑定开放平台和小程序已绑定开放平台不一致,请联系旺小宝更改绑定");
		}
	}

	public JsonResult getBindOpen(String wxAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/open/get?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsono = JSONObject.parseObject(result);
		if ("0".equals(jsono.getString("errcode")) || "89002".equals(jsono.getString("errcode"))) {
			return JsonResult.newInstanceDataSuccess(jsono);
		} else {
			return JsonResult.newInstance(jsono.getString("errcode"), jsono.getString("errmsg"));
		}
	}

	/**
	 * @methodName: getAuthorizerInfo @Description: TODO获取授权方详情 @param
	 *              componentAppid @param authorizerAppid @param
	 *              authoriceAccessToken @return String @createUser:
	 *              liping_max @createDate: 2018年1月17日 下午8:09:23 @updateUser:
	 *              liping_max @updateDate: 2018年1月17日 下午8:09:23 @throws
	 */
	public String getAuthorizerInfo(String componentAppid, String authorizerAppid, String authoriceAccessToken) {
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
	 * 
	 * @param wxAppid
	 * @param authoriceAccessToken
	 * @return
	 */
	public JSONObject bindWxamplink(String wxAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/wxopen/wxamplink?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		params.put("notify_users", "0");
		params.put("show_profile", "1");
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		JSONObject jsonO = JSONObject.parseObject(result);
		if (!"0".equals(jsonO.getString("errcode")) && !"89010".equals(jsonO.getString("errcode"))
				&& !"89015".equals(jsonO.getString("errcode"))) {
			log.error("授权失败,关联第三方平台小程序失败");
			throw new CommonException(jsonO.getInteger("errcode"), "授权失败:" + jsonO.getString("errmsg"));
		}
		jsonO.put("errcode", "0");
		return jsonO;
	}

	/**
	 * 解绑小程序与商家公众号
	 * 
	 * @param wxAppid
	 * @param authoriceAccessToken
	 * @return
	 */
	public String bindWxampunlink(String wxAppid, String authoriceAccessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/wxopen/wxampunlink?access_token=";
		url += authoriceAccessToken;
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		return result;
	}

	/**
	 * @methodName: getWxampunlink @Description: 获取商家公众号关联的小程序列表 @param
	 *              wxAppid @return String @createUser: liping_max @createDate:
	 *              2018年3月7日 上午10:48:38 @updateUser: liping_max @updateDate:
	 *              2018年3月7日 上午10:48:38 @throws
	 */
	public String getWxampunlink(String wxAppid) {
		String url = "https://api.weixin.qq.com/cgi-bin/wxopen/wxamplinkget?access_token=";
		url += wXopenPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject params = new JSONObject();
		params.put("appid", wxAppid);
		String result = HttpClientUtils.executeByJSONPOST(url, params.toJSONString(), 50000);
		return result;
	}

	public String getMiniprogramAuthorizerInfo(String wxAppId) {
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppId);
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_SEC_FORMAT);
		MiniProgramAuthInfoResponse response = new MiniProgramAuthInfoResponse();
		List<OrganizeTemplate> organizeTemplates = organizeTemplateRepository
				.findByOrganizationAccountAndIsNew(wxInfo.getOrganizationAccount(), "1");
		if (null != organizeTemplates && organizeTemplates.size() <= 0) {
			return new JSONObject().toJSONString();
		} else if (organizeTemplates.size() == 1) {
			OrganizeTemplate organizeTemplate = organizeTemplates.get(0);
			// 当前版本是最新版本
			response.setNickName(wxInfo.getNickName());
			// 查询版本
			WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateRepository
					.findByTemplateId(organizeTemplate.getTemplateId());
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
					response.setStatusMessage(organizeTemplate.getReason());
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

	public boolean isCanAuth(String wxAppId, String organizationAccount, String authType) {
		boolean isCanAuth = true;
		WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(
				OrganizationAuthType.MINIPROGRAMAUTH.getType(), organizationAccount);
		List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService
				.findByCondition(wxCondition);
		// 公众号授权需要判断品牌下是否已有公众号
		if (OrganizationAuthType.GONGZHONGHAOAUTH.getType().equals(authType)) {
			// 当前公众号没有绑定小程序，可以跟换公众号
			if (null == wxInfoResponses || wxInfoResponses.size() <= 0) {
				wxCondition.setAuthType(OrganizationAuthType.GONGZHONGHAOAUTH.getType());
				List<WXopenPlatformMerchantInfo> wxInfoList = wXopenPlatformMerchantInfoService
						.findByCondition(wxCondition);
				// 更新微信公众号需要将之前的删除
				if (!ObjectUtils.isEmpty(wxInfoList) && !wxInfoList.get(0).getWxAppid().equals(wxAppId)) {
					wXopenPlatformMerchantInfoService.deleteByWXAppId(wxInfoList.get(0).getWxAppid());
				}
			} else {
				log.info("当前品牌{}已有授权小程序,不能更换公众号{}", organizationAccount, wxAppId);
				throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), "当前品牌已有授权小程序,无法更换公众号,请联系服务提供方!");
			}
		} else if (OrganizationAuthType.MINIPROGRAMAUTH.getType().equals(authType)) {
			// 小程序授权
			if (null == wxInfoResponses || wxInfoResponses.size() <= 0) {
				throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), "授权小程序之前需要授权公众号");
			}
			/*
			 * else if (null != oldWxInfo &&
			 * !oldWxInfo.getWxAppid().equals(jsono.getString("authorizer_appid"
			 * ))) { return
			 * JsonResult.newInstanceAuthFail("更换小程序可能造成用户数据丢失,请联系【旺小宝】更换"); }
			 */
		}
		return isCanAuth;
	}

	/**
	 * @methodName: saveOrUpdateWxopenPlatformMerchantInfoFromAuth @Description:
	 *              TODO 品牌公众号和小程序管理员扫码授权回调之后立即将授权信息存储到数据库，
	 *              这样在后续授权流程异常的情况下就不再需要管理员再次扫码授权 @param
	 *              authorizationInfo @param authType @param
	 *              organizationAccount @return
	 *              WXopenPlatformMerchantInfo @createUser:
	 *              liping_max @createDate: 2018年3月28日 下午4:05:07 @updateUser:
	 *              liping_max @updateDate: 2018年3月28日 下午4:05:07 @throws
	 */
	public WXopenPlatformMerchantInfo saveOrUpdateWxopenPlatformMerchantInfoFromAuth(String authorizationInfo,
			String authType, String organizationAccount) {
		JSONObject jsono = JSONObject.parseObject(authorizationInfo);
		isCanAuth(jsono.getString("authorizer_appid"), organizationAccount, authType);
		WXopenPlatformMerchantInfo oldWxInfo = wXopenPlatformMerchantInfoService
				.getByWXAppId(jsono.getString("authorizer_appid"));
		// 重复授权
		if (null != oldWxInfo && !StringUtils.isEmpty(oldWxInfo.getWxAppid())) {
			oldWxInfo.setAuthoriceAccessToken(jsono.getString("authorizer_access_token"));
			oldWxInfo.setAuthoriceRefreshToken(jsono.getString("authorizer_refresh_token"));
			wXopenPlatformMerchantInfoService.save(oldWxInfo);
			redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY + oldWxInfo.getWxAppid(),
					JSONObject.toJSONString(oldWxInfo), 7000);
		} else {// 新授权
			// 获取平台调用凭证
			String component_access_token = wXAuthService.getApiComponentToken(appId, appsecret);
			// 获取授权方的详细信息
			String authorizerStr = getAuthorizerInfo(appId, jsono.getString("authorizer_appid"),
					component_access_token);
			JSONObject authorizerJson = JSONObject.parseObject(authorizerStr);
			JSONObject authorizerInfoJson = authorizerJson.getJSONObject("authorizer_info");
			oldWxInfo = new WXopenPlatformMerchantInfo(CreateUUID.getUuid(), jsono.getString("authorizer_appid"), null,
					jsono.getString("authorizer_access_token"), jsono.getString("authorizer_refresh_token"),
					"createUser", new Date(), "updateUser", new Date(), null, authType, organizationAccount,
					authorizerInfoJson.getString("nick_name"), authorizerInfoJson.getString("head_img"),
					authorizerInfoJson.getString("verify_type_info"), authorizerInfoJson.getString("user_name"));
			wXopenPlatformMerchantInfoService.save(oldWxInfo);
		}
		redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY + oldWxInfo.getWxAppid(),
				JSONObject.toJSONString(oldWxInfo), 7000);
		return oldWxInfo;
	}

	// 组装授权和创建开放平台
	public JsonResult buildingAuthorizer(String wxAppId) {
		WXopenPlatformMerchantInfo oldWxInfo = wXopenPlatformMerchantInfoService.getByWXAppId(wxAppId);
		if (Constants.AUTHORIZER_TYPE_GONGZHONGHAO.equals(oldWxInfo.getAuthType())) {
			// 创建商户公众号的开放平台
			JsonResult result = createOpen(wxAppId, oldWxInfo.getAuthoriceAccessToken());
			if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
				oldWxInfo.setOpenAppid(result.getData().toString());
				wXopenPlatformMerchantInfoService.save(oldWxInfo);
				// 关联我们的小程序和商家的
				OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService
						.findCanBindXiaochengxu();
				if (null == openPlatformXiaochengxu) {
					return JsonResult.newInstanceAuthFail("更新授权，平台黄页小程序不存在");
				}
				bindWxamplink(openPlatformXiaochengxu.getAppId(), oldWxInfo.getAuthoriceAccessToken());
				return JsonResult.newInstanceAuthSuccess("授权成功");
			} else {
				return JsonResult.newInstanceAuthFail("创建公众号开放平台失败");
			}
		} else {
			// 小程序授权
			WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(
					Constants.AUTHORIZER_TYPE_GONGZHONGHAO, oldWxInfo.getOrganizationAccount());
			List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService
					.findByCondition(wxCondition);
			JsonResult result = bindOpen(wxAppId, wxInfoResponses.get(0).getOpenAppid(),
					oldWxInfo.getAuthoriceAccessToken());
			if (JsonResult.APP_RETURN_SUCCESS.equals(result.getCode())) {
				oldWxInfo.setOpenAppid(wxInfoResponses.get(0).getOpenAppid());
				wXopenPlatformMerchantInfoService.save(oldWxInfo);
				openPlatformXiaochengxuService.initXiaochengxu(oldWxInfo.getWxAppid(), "add", oldWxInfo.getOrganizationAccount(),
						oldWxInfo.getUserName());
				return result;
			} else {
				return JsonResult.newInstanceAuthFail("小程序绑定开放平台失败");
			}
		}
	}
}
