package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import lombok.Data;

@Data
public class MiniProgramAuthInfoResponse {
	private String nickName;
	private String userVersion;
	private String statusMessage;
	private String onlineStatus;
	private String updateDate;
	private String updateUserVersion;
	private String updateStatus;
	private String status;
	private String updateMiniprogramTemplateId;
}
