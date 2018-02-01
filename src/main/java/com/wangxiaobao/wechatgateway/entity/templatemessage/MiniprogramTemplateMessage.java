package com.wangxiaobao.wechatgateway.entity.templatemessage;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Table(name = "t_base_miniprogram_template_message")
public class MiniprogramTemplateMessage {
	@Id
	private String messageId;
	private String toUser;
	private String templateId;
	private String page;
	private String formId;
	private String data;
	private String emphasisKeyword;
	private Date createDate;
	private String createUser;
}
