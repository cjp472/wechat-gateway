package com.wangxiaobao.wechatgateway.controller.openplatform;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.AlgorithmParameters;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
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
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.source3g.springboot.frame.backwidow.commom.BusinessException;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.enums.OrganizationAuthType;
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
import com.wangxiaobao.wechatgateway.service.weixinapi.WXApiService;
import com.wangxiaobao.wechatgateway.service.wxauth.WXAuthService;
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
	// String appId = "wxde53154a5290b54d";
	// String appsecret = "846e110a7c0e88488ca1ef915224f23d";
	static String sessionKey = "NcyLnPKk2skzUOtbBzBXDw==";
	@Autowired
	private WXApiService wXApiService;
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private WXAuthService wXAuthService;

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
	 * @methodName: getAuthorizerInfo @Description: 获取授权方的账号基本信息 @param
	 *              wxAppId @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月13日 下午2:16:04 @updateUser:
	 *              liping_max @updateDate: 2018年1月13日 下午2:16:04 @throws
	 */
	@RequestMapping("/wxAuth/getAuthorizerInfo")
	@ResponseBody
	public JsonResult getAuthorizerInfo(String wxAppId) {
		String authoriceAccessToken = wXAuthService.getApiComponentToken(appId, appsecret);
		String result = wXApiService.getAuthorizerInfo(appId, wxAppId, authoriceAccessToken);
		return JsonResult.newInstanceDataSuccess(JSONObject.parseObject(result));
	}

	@RequestMapping("/wxAuth/getMiniprogramAuthorizerInfo")
	@ResponseBody
	public JsonResult getMiniprogramAuthorizerInfo(String wxAppId) {
		String resultStr = wXApiService.getMiniprogramAuthorizerInfo(wxAppId);
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
		return wXApiService.createOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
	}

	/**
	 * 商户公众号与开放平台绑定
	 * 传了openAppid就用openAppid，没有openAppid就用传入的organizationAccount进行查询
	 * 
	 * @param wxAppid
	 * @param openAppid
	 * @return
	 */
	@RequestMapping("/gongzhonghao/bindOpen")
	@ResponseBody
	public JsonResult bindOpen(String wxAppid, String openAppid, String organizationAccount) {
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid);
		if (ObjectUtils.isEmpty(wXopenPlatformMerchantInfo)) {
			log.error("未查询到wxAppid={}对应的数据信息，无法完成绑定", wxAppid);
			return JsonResult.newInstanceMesFail("未查询到对应的数据信息,无法完成绑定");
		}
		// 在没有传入openAppid的情况下用organizationAccount进行openAppid的查询
		if (!StringUtils.hasText(openAppid) && StringUtils.hasText(organizationAccount)) {
			// 小程序授权
			WXopenPlatformMerchantInfoSearchCondition wxCondition = new WXopenPlatformMerchantInfoSearchCondition(
					Constants.AUTHORIZER_TYPE_GONGZHONGHAO, organizationAccount);
			List<WXopenPlatformMerchantInfo> wxInfoResponses = wXopenPlatformMerchantInfoService
					.findByCondition(wxCondition);
			if (null == wxInfoResponses || wxInfoResponses.size() <= 0) {
				return JsonResult.newInstanceAuthFail("授权小程序之前需要授权公众号");
			}
			openAppid = wxInfoResponses.get(0).getOpenAppid();
		}
		// 最终得到openAppid则进行绑定操作，没有则直接返回无法完成绑定
		if (StringUtils.hasText(openAppid)) {
			return JsonResult.newInstanceDataSuccess(
					wXApiService.bindOpen(wxAppid, openAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken()));
		} else {
			return JsonResult.newInstanceMesFail("未查询到可绑定的开放平台数据");
		}
	}

	/**
	 * @methodName: unbindOpen @Description: 商户公众号与开放平台解绑 @param wxAppid @param
	 *              openAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午11:00:12 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午11:00:12 @throws
	 */
	@RequestMapping("/gongzhonghao/unbindOpen")
	@ResponseBody
	public JsonResult unbindOpen(@RequestBody WXopenPlatformMerchantInfoSearchCondition wxMerchantInfoSearchCondition) {
		if (!StringUtils.hasText(wxMerchantInfoSearchCondition.getAuthType())
				|| !"1".equals(wxMerchantInfoSearchCondition.getAuthType())
				|| !"2".equals(wxMerchantInfoSearchCondition.getAuthType())) {
			return JsonResult.newInstanceMesFail("参数传递错误");
		}
		// 查询公众号是否存在数据库中
		WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxMerchantInfoSearchCondition.getWxAppid());
		// 公众号不存在与数据库中则调用微信接口查询公众号所绑定的开放平台
		if (!StringUtils.hasText(wxMerchantInfoSearchCondition.getOpenAppid())) {
			JsonResult jsonResult = wXApiService.getBindOpen(wxMerchantInfoSearchCondition.getWxAppid(),
					wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
			if (!"0".equals(jsonResult.getCode())) {
				return JsonResult.newInstanceMesFail(jsonResult.getMessage());
			}
			JSONObject json = (JSONObject) jsonResult.getData();
			wxMerchantInfoSearchCondition.setOpenAppid(json.getString("open_appid"));
		}
		// 公众号没有绑定开放平台则直接返回成功
		if (!StringUtils.hasText(wxMerchantInfoSearchCondition.getOpenAppid())) {
			return JsonResult.newInstanceSuccess();
		}
		// 小程序已绑定公众号所创建的开放平台，则无法进行解绑操作
		if (canCreatOpenAppId(wxMerchantInfoSearchCondition.getOpenAppid(),
				wxMerchantInfoSearchCondition.getAuthType())) {
			JsonResult jsonResult = wXApiService.unbindOpen(wxMerchantInfoSearchCondition.getWxAppid(),
					wxMerchantInfoSearchCondition.getOpenAppid(), wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
			return jsonResult;
		} else {
			return JsonResult.newInstanceMesFail("当前状态不能够进行解绑操作");
		}
	}

	// 判断是否可以创建开放平台
	private boolean canCreatOpenAppId(String openAppId, String authType) {
		boolean tag = true;
		// 公众号操作解绑，需要判断是否已绑定了小程序，如果已绑定则不能进行解绑
		if (OrganizationAuthType.GONGZHONGHAOAUTH.getType().equals(authType)) {
			WXopenPlatformMerchantInfoSearchCondition wxMerchantInfoSearchCondition = new WXopenPlatformMerchantInfoSearchCondition();
			wxMerchantInfoSearchCondition.setOpenAppid(openAppId);
			wxMerchantInfoSearchCondition.setAuthType(OrganizationAuthType.MINIPROGRAMAUTH.getType());
			List<WXopenPlatformMerchantInfo> infos = wXopenPlatformMerchantInfoService
					.findListBy(wxMerchantInfoSearchCondition);
			for (WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo2 : infos) {
				if (wxMerchantInfoSearchCondition.getOpenAppid().equals(wXopenPlatformMerchantInfo2.getOpenAppid())) {
					tag = false;
					break;
				}
			}
		}
		return tag;
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
		return wXApiService.getBindOpen(wxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
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
		JSONObject resultJson = wXApiService.bindWxamplink(xcxAppid,
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
		String resultJson = wXApiService.bindWxampunlink(xcxAppid, wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
		return JsonResult.newInstanceDataSuccess(resultJson);
	}

	/**
	 * @methodName: wxamplinkget @Description: 查询商家公众号关联的小程序列表 @param
	 *              wxAppid @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年3月7日 上午10:50:17 @updateUser:
	 *              liping_max @updateDate: 2018年3月7日 上午10:50:17 @throws
	 */
	@RequestMapping("/gongzhonghao/wxamplinkget")
	public @ResponseBody JsonResult wxamplinkget(String wxAppid) {
		String resultJson = wXApiService.getWxampunlink(wxAppid);
		return JsonResult.newInstanceDataSuccess(resultJson);
	}

	/**
	 * @methodName: updateWxamplink @Description: 更新商家公众号与小程序的关联关系 @param
	 *              wxAppid @param newXcxAppid @param oldXcxAppid @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年3月7日
	 *              上午11:17:23 @updateUser: liping_max @updateDate: 2018年3月7日
	 *              上午11:17:23 @throws
	 */
	@RequestMapping("/gongzhonghao/updateWxamplink")
	public @ResponseBody JsonResult updateWxamplink(String wxAppid, String newXcxAppid, String oldXcxAppid) {
		log.info("更新商家公众号与小程序的关联关系请求参数wxAppid={},newXcxAppid={},oldXcxAppid={}", wxAppid, newXcxAppid, oldXcxAppid);
		List<WXopenPlatformMerchantInfo> wXopenPlatformMerchantInfoList = new ArrayList<>();
		if (ObjectUtils.isEmpty(wxAppid)) {
			WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
			wxInfo.setWxAppid(wxAppid);
			wXopenPlatformMerchantInfoList.add(wxInfo);
		} else {
			wXopenPlatformMerchantInfoList = wXopenPlatformMerchantInfoService
					.findByAuthType(Constants.AUTHORIZER_TYPE_GONGZHONGHAO);
		}
		for (WXopenPlatformMerchantInfo info : wXopenPlatformMerchantInfoList) {
			try {
				if (ObjectUtils.isEmpty(oldXcxAppid)) {
					log.info("不解除原有小程序的关联关系，只新增关联关系");
				} else {
					log.info("需要解除原有小程序的关联关系，oldXcxAppid={}", oldXcxAppid);
					WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
							.getWXopenPlatformMerchantInfo(info.getWxAppid());
					String resultJson = wXApiService.bindWxampunlink(oldXcxAppid,
							wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
					log.info("解除公众号{}，小程序{}关联关系结果{}", info.getWxAppid(), oldXcxAppid, resultJson);
				}
				log.info("绑定公众号{}，新的小程序{}关联关系开始", info.getWxAppid(), newXcxAppid);
				WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService
						.getWXopenPlatformMerchantInfo(info.getWxAppid());
				JSONObject resultJson = wXApiService.bindWxamplink(newXcxAppid,
						wXopenPlatformMerchantInfo.getAuthoriceAccessToken());
				log.info("绑定公众号{}，新的小程序{}关联关系结果{}", info.getWxAppid(), newXcxAppid, resultJson);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("更新商户公众号{}的小程序关联关系异常", e);
			}
		}
		return JsonResult.newInstanceSuccess();
	}
}