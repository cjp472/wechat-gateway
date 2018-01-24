package com.wangxiaobao.wechatgateway.controller.templatemessage;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageLibraryGetRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageRequest;
import com.wangxiaobao.wechatgateway.service.templatemessage.MiniprogramTemplateMessageService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
@RestController
public class MiniprogramTemplateMessageController extends BaseController{
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	private MiniprogramTemplateMessageService miniprogramTemplateMessageService;
	@Autowired
	private TestService testService;
	
	@RequestMapping("miniprogram/template/sendMessage")
	public JsonResult sendMessage(MiniprogramTemplateMessageRequest request){
		String componentAccessToken = testService.getApiComponentToken(appId, appsecret);
		MiniprogramTemplateMessage miniprogramTemplateMessage = new MiniprogramTemplateMessage();
		miniprogramTemplateMessageService.buildingTemplateMessageData(request,miniprogramTemplateMessage);
		String result = miniprogramTemplateMessageService.sendMessageToUser(miniprogramTemplateMessage, componentAccessToken);
		miniprogramTemplateMessageService.saveMessage(miniprogramTemplateMessage);
		return JsonResult.newInstanceDataSuccess(result);
	}
	
	
	@RequestMapping("miniprogram/template/libraryGet")
	public JsonResult libraryGet(@Valid MiniprogramTemplateMessageLibraryGetRequest request){
		String result = miniprogramTemplateMessageService.librayGet(request.getWxAppid(), request.getId());
		return JsonResult.newInstanceDataSuccess(JSONObject.parseObject(result));
	}
}
