package com.wangxiaobao.wechatgateway.entity.base;

public class TextMessage extends BaseWechatMessage {
	/**
	 * 文本消息内容
	 */
	private String Content;
	
	public String getContent() {
		return Content;
	}
	
	public void setContent(String content) {
		Content = content;
	}
}
