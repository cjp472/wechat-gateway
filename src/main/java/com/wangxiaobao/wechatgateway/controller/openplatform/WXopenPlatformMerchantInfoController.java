package com.wangxiaobao.wechatgateway.controller.openplatform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoResponse;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoSearchCondition;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.CreateUUID;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WXopenPlatformMerchantInfoController {
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	private TestService testService;
	
	@RequestMapping("/wxopenplatform/save")
	@ResponseBody
	public JsonResult save(){
		log.info("-------------开始进行保存");
		WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
		wxInfo.setAuthoriceRefreshToken("12321444");
		wxInfo.setAuthoriceAccessToken("hkahjeh");
		wxInfo.setComponentVerifyTicket("ojwdioj23d3");
		wxInfo.setWxAppid("32r2e23e233e3");
		wxInfo.setCreateDate(new Date());
		wxInfo.setWxOpenPlatformId(CreateUUID.getUuid());
		wXopenPlatformMerchantInfoService.save(wxInfo);
		return JsonResult.newInstanceSuccess();
	}
	/**
	 * 查询商家授权的微信公众号和小程序
	 * @param wxCondition
	 * @return
	 */
	@RequestMapping("/wxopenplatform/findMerchantWx")
	@ResponseBody
	public JsonResult findMerchantWx(WXopenPlatformMerchantInfoSearchCondition wxCondition){
		List<WXopenPlatformMerchantInfo> wxList = wXopenPlatformMerchantInfoService.findByCondition(wxCondition);
		if(null!=wxList&&wxList.size()>0){
			WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo = wxList.get(0);
			wXopenPlatformMerchantInfo = wXopenPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wXopenPlatformMerchantInfo.getWxAppid());
			
			WXopenPlatformMerchantInfoResponse wXopenPlatformMerchantInfoResponse = new WXopenPlatformMerchantInfoResponse();
			BeanUtils.copyProperties(wxList.get(0), wXopenPlatformMerchantInfoResponse);
			wXopenPlatformMerchantInfoResponse.setComponentAppid(appId);
			wXopenPlatformMerchantInfoResponse.setComponentAccessToken(testService.getApiComponentToken(appId,appsecret));
			return JsonResult.newInstanceDataSuccess(wXopenPlatformMerchantInfoResponse);
		}
		return JsonResult.newInstanceSuccess();
	}
}
