package com.wangxiaobao.wechatgateway.form.templatemessage;

import java.util.Date;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;
@Data
public class TemplateMessageRequest {
	private String messageId;
	private String toUser;
	private String templateId;
	private String page;
	private String formId;
	private JSONObject dataJSON;
	private String emphasisKeyword;
	private Date createDate;
	private String createUser;
}
