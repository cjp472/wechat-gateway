package com.wangxiaobao.wechatgateway.service.templatemessage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageRequest;
import com.wangxiaobao.wechatgateway.repository.templatemessage.MiniprogramTemplateMessageRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
@Service
public class MiniprogramTemplateMessageService extends BaseService {
	@Autowired
	MiniprogramTemplateMessageRepository miniprogramTemplateMessageRepository;
	@Autowired
	WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	/**
	  * @methodName: saveMessage
	  * @Description: TODO保存发送的小程序模板消息
	  * @param miniprogramTemplateMessage void
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 上午11:58:53
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 上午11:58:53
	  * @throws
	 */
	public void saveMessage(MiniprogramTemplateMessage miniprogramTemplateMessage){
		miniprogramTemplateMessageRepository.save(miniprogramTemplateMessage);
	}
	/**
	  * @methodName: sendMessageToUser
	  * @Description: 发送小程序模板消息
	  * @param miniprogramTemplateMessage
	  * @param componentAccessToken
	  * @return String
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 下午3:03:04
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 下午3:03:04
	  * @throws
	 */
	public String sendMessageToUser(MiniprogramTemplateMessage miniprogramTemplateMessage,String componentAccessToken){
		JSONObject json = new JSONObject();
		json.put("touser", miniprogramTemplateMessage.getToUser());
		json.put("template_id", miniprogramTemplateMessage.getTemplate());
		json.put("page", miniprogramTemplateMessage.getPage());
		json.put("form_id", miniprogramTemplateMessage.getFormId());
		json.put("data", miniprogramTemplateMessage.getData());
		String result = HttpClientUtils.executeByJSONPOST(wxProperties.getWx_template_message_send_url()+componentAccessToken, json.toJSONString(), 50000);
		return result;
	}
	
	public String librayGet(String wxAppid,String id){
		JSONObject params = new JSONObject();
		params.put("id", id);
		String result = HttpClientUtils.executeByJSONPOST(wxProperties.getWx_template_library_get_url()+wXopenPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken(), params.toJSONString(), 50000);
		return result;
	}
	
	/**
	  * @methodName: buildingTemplateMessageData
	  * @Description: 组装发送参数
	  * @param request
	  * @param miniprogramTemplateMessage
	  * @return MiniprogramTemplateMessage
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 下午7:36:21
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 下午7:36:21
	  * @throws
	 */
	public MiniprogramTemplateMessage buildingTemplateMessageData(MiniprogramTemplateMessageRequest request,MiniprogramTemplateMessage miniprogramTemplateMessage){
		JSONObject dataJson = new JSONObject();
		JSONObject keyJson = new JSONObject();
		keyJson.put("value", request.getStatus());
		keyJson.put("color", "#173177");
		dataJson.put("keyword1", keyJson);
		
		keyJson.put("value", request.getRewardName());
		keyJson.put("color", "#173177");
		dataJson.put("keyword2", keyJson);
		
		keyJson.put("value", request.getMerchantName());
		keyJson.put("color", "#173177");
		dataJson.put("keyword3", keyJson);
		
		keyJson.put("value", request.getRemark());
		keyJson.put("color", "#173177");
		dataJson.put("keyword4", keyJson);
		
		miniprogramTemplateMessage.setData(dataJson.toJSONString());
		miniprogramTemplateMessage.setCreateDate(new Date());
		miniprogramTemplateMessage.setFormId(request.getFormId());
		miniprogramTemplateMessage.setMessageId(KeyUtil.genUniqueKey());
		miniprogramTemplateMessage.setPage(request.getPage());
		miniprogramTemplateMessage.setTemplate(wxProperties.getMiniprogram_jigsaw_template_message_id());
		miniprogramTemplateMessage.setToUser(request.getTouser());
		return miniprogramTemplateMessage;
	}
}
