package com.wangxiaobao.wechatgateway.controller.openplatform;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.utils.CreateUUID;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WXopenPlatformMerchantInfoController {
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	
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
}
