package com.wangxiaobao.wechatgateway.service.templatemessage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
import com.wangxiaobao.wechatgateway.entity.templatemessageconfig.MiniprogramTemplateMessageConfig;
import com.wangxiaobao.wechatgateway.enums.MiniprogramTemplateMessageTypeEnum;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateCardVoucherDueMessageRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramtemplatemessage.MiniprogramTemplateMessageRequest;
import com.wangxiaobao.wechatgateway.repository.templatemessage.MiniprogramTemplateMessageRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.templatemessageconfig.MiniprogramTemplateMessageConfigService;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;

@Service
public class MiniprogramTemplateMessageService extends BaseService {
	@Autowired
	MiniprogramTemplateMessageRepository miniprogramTemplateMessageRepository;
	@Autowired
	WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
	@Autowired
	MiniprogramTemplateMessageConfigService miniprogramTemplateMessageConfigService;
	@Value("${wechat.miniprogram.templateMessageId}")
	String templateMessageId;
	@Value("${wechat.miniprogram.templateMessageCardDueId}")
	String templateMessageCardDueId;

	/**
	 * @methodName: saveMessage @Description: TODO保存发送的小程序模板消息 @param
	 * miniprogramTemplateMessage void @createUser: liping_max @createDate:
	 * 2018年1月24日 上午11:58:53 @updateUser: liping_max @updateDate: 2018年1月24日
	 * 上午11:58:53 @throws
	 */
	public void saveMessage(MiniprogramTemplateMessage miniprogramTemplateMessage) {
		miniprogramTemplateMessageRepository.save(miniprogramTemplateMessage);
	}

	/**
	 * @methodName: sendMessageToUser @Description: 发送小程序模板消息 @param
	 * miniprogramTemplateMessage @param componentAccessToken @return
	 * String @createUser: liping_max @createDate: 2018年1月24日
	 * 下午3:03:04 @updateUser: liping_max @updateDate: 2018年1月24日
	 * 下午3:03:04 @throws
	 */
	public String sendMessageToUser(MiniprogramTemplateMessage miniprogramTemplateMessage,
			String componentAccessToken) {
		JSONObject json = new JSONObject();
		json.put("touser", miniprogramTemplateMessage.getToUser());
		json.put("template_id", miniprogramTemplateMessage.getTemplateId());
		json.put("page", miniprogramTemplateMessage.getPage());
		json.put("form_id", miniprogramTemplateMessage.getFormId());
		json.put("data", miniprogramTemplateMessage.getData());
		String result = HttpClientUtils.executeByJSONPOST(
				wxProperties.getWx_template_message_send_url() + componentAccessToken, json.toJSONString(), 50000);
		return result;
	}

	public String librayGet(String wxAppid, String id) {
		JSONObject params = new JSONObject();
		params.put("id", id);
		String result = HttpClientUtils.executeByJSONPOST(
				wxProperties.getWx_template_library_get_url() + wXopenPlatformMerchantInfoService
						.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken(),
				params.toJSONString(), 50000);
		return result;
	}

	/**
	 * @methodName: buildingTemplateMessageData @Description: 组装发送参数 @param
	 * request @param miniprogramTemplateMessage @return
	 * MiniprogramTemplateMessage @createUser: liping_max @createDate:
	 * 2018年1月24日 下午7:36:21 @updateUser: liping_max @updateDate: 2018年1月24日
	 * 下午7:36:21 @throws
	 */
	public MiniprogramTemplateMessage buildingTemplateMessageData(MiniprogramTemplateMessageRequest request,
			MiniprogramTemplateMessage miniprogramTemplateMessage) {
		JSONObject dataJson = new JSONObject();
		
		JSONObject keyJson1 = new JSONObject();
		keyJson1.put("value", request.getRewardName());
		keyJson1.put("color", "#EA3340");
		dataJson.put("keyword1", keyJson1);
		JSONObject keyJson2 = new JSONObject();
		keyJson2.put("value", request.getStatus());
		keyJson2.put("color", "#EA3340");
		dataJson.put("keyword2", keyJson2);
		JSONObject keyJson3 = new JSONObject();
		keyJson3.put("value", request.getMerchantName());
		keyJson3.put("color", "#173177");
		dataJson.put("keyword3", keyJson3);
		JSONObject keyJson4 = new JSONObject();
		keyJson4.put("value", request.getRemark());
		keyJson4.put("color", "#173177");
		dataJson.put("keyword4", keyJson4);

		miniprogramTemplateMessage.setData(dataJson.toJSONString());
		miniprogramTemplateMessage.setCreateDate(new Date());
		miniprogramTemplateMessage.setFormId(request.getFormId());
		miniprogramTemplateMessage.setMessageId(KeyUtil.genUniqueKey());
		miniprogramTemplateMessage.setPage(request.getPage());
		miniprogramTemplateMessage.setToUser(request.getTouser());
		
		// 查询小程序的通知模板消息设置
		MiniprogramTemplateMessageConfig miniprogramTemplateMessageConfig = new MiniprogramTemplateMessageConfig();
		miniprogramTemplateMessageConfig.setWxAppId(request.getAppId());
		miniprogramTemplateMessageConfig
				.setMessageType(MiniprogramTemplateMessageTypeEnum.ACTIVITYRESULTNOTIFY.getType());
		miniprogramTemplateMessage.setTemplateId(miniprogramTemplateMessageConfigService
				.findByCondition(miniprogramTemplateMessageConfig).getMessageTemplateId());
		return miniprogramTemplateMessage;
	}

	public MiniprogramTemplateMessage buildingTemplateCardVoucherDueMessageData(
			MiniprogramTemplateCardVoucherDueMessageRequest request,
			MiniprogramTemplateMessage miniprogramTemplateMessage) {
		JSONObject dataJson = new JSONObject();
		
		JSONObject keyJson1 = new JSONObject();
		keyJson1.put("value", request.getRewardType());
		keyJson1.put("color", "#EA3340");
		dataJson.put("keyword1", keyJson1);
		
		JSONObject keyJson2 = new JSONObject();
		keyJson2.put("value", request.getEndDate());
		keyJson2.put("color", "#518AFD");
		dataJson.put("keyword2", keyJson2);
		
		JSONObject keyJson3 = new JSONObject();
		keyJson3.put("value", request.getRemark());
		keyJson3.put("color", "#518AFD");
		dataJson.put("keyword3", keyJson3);
		
		JSONObject keyJson4 = new JSONObject();
		keyJson4.put("value", request.getRewardName());
		keyJson4.put("color", "#173177");
		dataJson.put("keyword4", keyJson4);
		
		JSONObject keyJson5 = new JSONObject();
		keyJson5.put("value", request.getMerchantName());
		keyJson5.put("color", "#173177");
		dataJson.put("keyword5", keyJson5);

		miniprogramTemplateMessage.setData(dataJson.toJSONString());
		miniprogramTemplateMessage.setCreateDate(new Date());
		miniprogramTemplateMessage.setFormId(request.getFormId());
		miniprogramTemplateMessage.setMessageId(KeyUtil.genUniqueKey());
		miniprogramTemplateMessage.setPage(request.getPage());
		miniprogramTemplateMessage.setToUser(request.getTouser());
		// 查询小程序的通知模板消息设置
		MiniprogramTemplateMessageConfig miniprogramTemplateMessageConfig = new MiniprogramTemplateMessageConfig();
		miniprogramTemplateMessageConfig.setWxAppId(request.getAppId());
		miniprogramTemplateMessageConfig
				.setMessageType(MiniprogramTemplateMessageTypeEnum.CARDTIMEOUTNOFITY.getType());
		miniprogramTemplateMessage.setTemplateId(miniprogramTemplateMessageConfigService
				.findByCondition(miniprogramTemplateMessageConfig).getMessageTemplateId());
		return miniprogramTemplateMessage;
	}
}
