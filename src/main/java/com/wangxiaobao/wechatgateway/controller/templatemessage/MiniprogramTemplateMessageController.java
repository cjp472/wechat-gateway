package com.wangxiaobao.wechatgateway.controller.templatemessage;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateCardVoucherDueMessageRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageLibraryGetRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageRequest;
import com.wangxiaobao.wechatgateway.form.templatemessage.TemplateMessageRequest;
import com.wangxiaobao.wechatgateway.service.gongzhonghao.GongzhonghaoService;
import com.wangxiaobao.wechatgateway.service.templatemessage.MiniprogramTemplateMessageService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MiniprogramTemplateMessageController extends BaseController{
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	private MiniprogramTemplateMessageService miniprogramTemplateMessageService;
	@Autowired
	private GongzhonghaoService gongzhonghaoService;
	
	/**
	  * @methodName: sendMessage
	  * @Description: TODO发送商家活动结果通知
	  * @param request
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月26日 上午11:11:48
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月26日 上午11:11:48
	  * @throws
	 */
	@RequestMapping("miniprogram/template/sendMessage")
	public JsonResult sendMessage(@Valid @RequestBody MiniprogramTemplateMessageRequest request,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			log.error("发送商家活动结果通知异常【MiniprogramTemplateMessageRequest】{}",request);
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		String componentAccessToken = gongzhonghaoService.getAccessToken(request.getAppId());
		TemplateMessageRequest templateMessageRequest = new TemplateMessageRequest();
		MiniprogramTemplateMessage miniprogramTemplateMessage = new MiniprogramTemplateMessage();
		templateMessageRequest = miniprogramTemplateMessageService.buildingTemplateMessageData(request,templateMessageRequest);
		log.info("发送模板消息，组装后的参数：{}",templateMessageRequest);
		String result = miniprogramTemplateMessageService.sendMessageToUser(templateMessageRequest, componentAccessToken);
		BeanUtils.copyProperties(templateMessageRequest, miniprogramTemplateMessage);
		miniprogramTemplateMessage.setData(templateMessageRequest.getDataJSON().toJSONString());
		miniprogramTemplateMessageService.saveMessage(miniprogramTemplateMessage);
		return JsonResult.newInstanceDataSuccess(result);
	}
	
	/**
	 * 
	  * @methodName: sendCardVoucherDueMessage
	  * @Description: 发送用户卡券到期提醒
	  * @param request
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月26日 上午11:11:23
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月26日 上午11:11:23
	  * @throws
	 */
	@RequestMapping("miniprogram/template/sendCardVoucherDueMessage")
	public JsonResult sendCardVoucherDueMessage(@Valid @RequestBody MiniprogramTemplateCardVoucherDueMessageRequest request,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			log.error("发送商家活动结果通知异常【MiniprogramTemplateMessageRequest】{}",request);
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		String componentAccessToken = gongzhonghaoService.getAccessToken(request.getAppId());
		MiniprogramTemplateMessage miniprogramTemplateMessage = new MiniprogramTemplateMessage();
		TemplateMessageRequest templateMessageRequest = new TemplateMessageRequest();
		templateMessageRequest = miniprogramTemplateMessageService.buildingTemplateCardVoucherDueMessageData(request,templateMessageRequest);
		String result = miniprogramTemplateMessageService.sendMessageToUser(templateMessageRequest, componentAccessToken);
		BeanUtils.copyProperties(templateMessageRequest, miniprogramTemplateMessage);
		miniprogramTemplateMessage.setData(templateMessageRequest.getDataJSON().toJSONString());
		miniprogramTemplateMessageService.saveMessage(miniprogramTemplateMessage);
		return JsonResult.newInstanceDataSuccess(result);
	}
	
	/**
	  * @methodName: libraryGet
	  * @Description: TODO获取模板库
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午6:35:06
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午6:35:06
	  * @throws
	 */
	@RequestMapping("miniprogram/template/libraryGet")
	public JsonResult libraryGet(@Valid MiniprogramTemplateMessageLibraryGetRequest request,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【获取模板库】参数不正确, MiniprogramTemplateMessageLibraryGetRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String result = miniprogramTemplateMessageService.librayGet(request.getWxAppid(), request.getId());
		return JsonResult.newInstanceDataSuccess(JSONObject.parseObject(result));
	}
}
