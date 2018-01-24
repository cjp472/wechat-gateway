package com.wangxiaobao.wechatgateway.service.templatemessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
import com.wangxiaobao.wechatgateway.repository.templatemessage.MiniprogramTemplateMessageRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
@Service
public class MiniprogramTemplateMessageService extends BaseService {
	@Autowired
	MiniprogramTemplateMessageRepository miniprogramTemplateMessageRepository;
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
		String result = HttpClientUtils.executeByJSONPOST(wxProperties.getWx_template_message_send_url()+componentAccessToken, JSONObject.toJSONString(miniprogramTemplateMessage), 50000);
		return result;
	}
}
