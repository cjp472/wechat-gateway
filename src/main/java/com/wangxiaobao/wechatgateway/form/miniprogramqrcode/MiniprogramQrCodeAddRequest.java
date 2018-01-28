package com.wangxiaobao.wechatgateway.form.miniprogramqrcode;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniprogramQrCodeAddRequest {
	@NotEmpty(message="wxAppid必填")
	private String wxAppid;
	@NotEmpty(message="二维码规则路径必填")
	private String prefix;
	@NotEmpty(message="独占规则必填")
	private String permit_sub_rule;
	private String path;
	@NotEmpty(message="测试范围open_version必填")
	private String open_version;
	private String debug_url;
	@NotEmpty(message="编辑标志位is_edit必填")
	private String is_edit;
}
