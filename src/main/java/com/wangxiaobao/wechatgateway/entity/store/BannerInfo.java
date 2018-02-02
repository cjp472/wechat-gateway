package com.wangxiaobao.wechatgateway.entity.store;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wangxiaobao.wechatgateway.utils.Constants;

import lombok.Data;

@Data
@Entity
@Table(name="t_base_banner_info")
public class BannerInfo {
	@Id
	private String configId;
	//品牌Account
	@NotEmpty(message="品牌account必填")
	private String orgAccount;
	private String orgName;
	//门店Account
	@NotEmpty(message="门店account必填")
	private String merchantAccount;
	private String merchantName;
	//1：店内展示；2：店外展示
	@Min(value=1,message="店内店外展示必填")
	private int type;
	//展示顺序
	@Min(value=1,message="顺序必填")
	private int sort;
	//显示名称
	@NotEmpty(message="显示名称必填")
	private String name;
	//跳转url
	@NotEmpty(message="跳转地址必填")
	private String url;
	//小程序appId
	private String appId;
	private int isValidate=Constants.IS_VALIDATE;
	private String createUser;
	@JsonFormat(pattern=Constants.DATE_TIME_SEC_FORMAT)
	private Date createDate;
	private String updateUser;
	@JsonFormat(pattern=Constants.DATE_TIME_SEC_FORMAT)
	private Date updateDate;
}
