package com.wangxiaobao.wechatgateway.service.kefu;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;

import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
@Service
public class KeFuService extends BaseService{
	
	@Async
	public void sendKefuMessage(String authorizer_access_token,WxMpKefuMessage wxMpKefuMessage){
		String url = wxProperties.getWx_send_kefu_message_url()+authorizer_access_token;
		HttpClientUtils.executeByJSONPOST(url, wxMpKefuMessage.toJson(), 50000);
	}
}
