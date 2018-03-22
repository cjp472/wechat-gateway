package com.wangxiaobao.wechatgateway.VO.organizetemplate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import lombok.Data;

@Data
@JsonSerialize(include=Inclusion.ALWAYS)
public class OrganizeTemplateVO {

	private String miniprogramTemplateId;
	private String wxAppId;
	private String organizationAccount;
	private String templateId;
	private java.util.Date createDate;
	private java.util.Date updateDate;
	private String status;
	private String reason;
	private String auditid;
	private String nickName;
	private String orgName;
}
