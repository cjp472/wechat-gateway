package com.wangxiaobao.wechatgateway.entity.constantcode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
  * @ProjectName: wechatgateway 
  * @PackageName: com.wangxiaobao.wechatgateway.entity.base 
  * @ClassName: ConstantCode
  * @Description: TODO常量配置表
  * @Copyright: Copyright (c) 2018  ALL RIGHTS RESERVED.
  * @Company:成都国胜天丰技术有限责任公司
  * @author liping_max
  * @date 2018年1月17日 下午5:05:25
 */
@Entity
@Table(name="t_base_constant_code")
@Data
public class ConstantCode{
	@Id
	@Column(name="constant_code_id")
	private String constantCodeId;

	/**
	 * 模版类型
	 */
	@Column(name="type")
	private String type;
	@Column(name="value")
	private String value;
	@Column(name="name")
	private String name;
	@Column(name="status")
	private String status;
	@Column(name="parent_id")
	private String parentId;
	@Column(name="constant_key")
	private String constantKey;
	public ConstantCode() {
		super();
	}
	public ConstantCode(String type,String constantKey) {
		super();
		this.type = type;
		this.constantKey=constantKey;
	}

	public ConstantCode(String type) {
		super();
		this.type = type;
	}
}
