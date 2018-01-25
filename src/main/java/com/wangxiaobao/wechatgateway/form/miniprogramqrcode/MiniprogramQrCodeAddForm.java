package com.wangxiaobao.wechatgateway.form.miniprogramqrcode;

import lombok.Data;

@Data
public class MiniprogramQrCodeAddForm {
	private String prefix;
	private String permit_sub_rule;
	private String path;
	private String open_version;
	private String debug_url;
	private String is_edit;
}
