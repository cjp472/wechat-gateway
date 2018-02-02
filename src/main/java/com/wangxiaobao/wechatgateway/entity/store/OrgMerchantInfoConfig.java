package com.wangxiaobao.wechatgateway.entity.store;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="t_base_brand_store_info_config")
public class OrgMerchantInfoConfig {
	@Id
	private String configId;
	private String merchantAccount;
	private String merchantName;
	private String orgName;
	private String orgAccount;
	private int type;
	private int subType;
	private String value;
	private String createUser;
	private Date createDate;
	private String updateUser;
	private Date updateDate;
	private int isValidate;
	public OrgMerchantInfoConfig() {
		super();
	}
	public OrgMerchantInfoConfig(String configId, String merchantAccount, String merchantName, String orgName,
			String orgAccount, int type, int subType, String value, String createUser, Date createDate,
			String updateUser, Date updateDate, int isValidate) {
		super();
		this.configId = configId;
		this.merchantAccount = merchantAccount;
		this.merchantName = merchantName;
		this.orgName = orgName;
		this.orgAccount = orgAccount;
		this.type = type;
		this.subType = subType;
		this.value = value;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.isValidate = isValidate;
	}
	public OrgMerchantInfoConfig(String merchantAccount, String orgAccount, int type) {
		super();
		this.merchantAccount = merchantAccount;
		this.orgAccount = orgAccount;
		this.type = type;
	}
	
}
