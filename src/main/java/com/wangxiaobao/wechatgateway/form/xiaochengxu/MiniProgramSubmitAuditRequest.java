package com.wangxiaobao.wechatgateway.form.xiaochengxu;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class MiniProgramSubmitAuditRequest {

	/**
	 * 模板Id
	 */
	@NotEmpty(message="地址必填")
	private String address;
	@NotEmpty(message="商家小程序Appid必填")
	private String wxAppid;
	@NotEmpty(message="标签必填")
	private String tag;
	@NotEmpty(message="一级类目名称必填")
	private String firstClass;
	@NotEmpty(message="二级类目名称必填")
	private String secondClass;
	@Min(value='0',message="一级类目名称不能小于0")
	private int firstId;
	@Min(value='0',message="二级类目名称不能小于0")
	private int secondId;
	@NotEmpty(message="标题必填")
	private String title;
}
