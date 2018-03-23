package com.wangxiaobao.wechatgateway.form.gongzhonghao;

import lombok.Data;

@Data
public class gongzhonghaoListResquest {

	private String brandName;
	private int page;
	private int size;
	private String authType;
}
