package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import java.util.Date;

import javax.persistence.TemporalType;

import lombok.Data;

@Data
public class MiniProgramAuthInfoResponse {
	private String nickName;
	private String onLineUserVersion;
	private String onlineStatus;
	@javax.persistence.Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	private String updateUserVersion;
	private String updateStatus;
	private String status;
	private String statusMessage;
	private String updateMiniprogramTemplateId;
}
