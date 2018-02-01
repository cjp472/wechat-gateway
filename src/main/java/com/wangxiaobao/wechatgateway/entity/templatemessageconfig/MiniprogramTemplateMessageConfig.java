package com.wangxiaobao.wechatgateway.entity.templatemessageconfig;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="t_base_miniprogram_template_message_config")
public class MiniprogramTemplateMessageConfig {
	@Id
	private String miniprogramTemplateMessageId;
	private String wxAppId;
	private int messageType;
	private String messageTemplateId;
	private String messageTemplateName;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
}
