package com.wangxiaobao.wechatgateway.entity.openplatform;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;
@Entity
@Data
@DynamicUpdate
@Table(name="T_BASE_WX_OPEN_PLATFORM_MERCHANT_INFO")
public class WXopenPlatformMerchantInfo implements Serializable {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 8720285673051507253L;
	@Id
	@Column(name="wx_open_platform_id")
	private String wxOpenPlatformId;
	@Column(name="wx_appid")
	private String wxAppid;
	@Column(name="component_verify_ticket")
	private String componentVerifyTicket;
	@Column(name="authorice_access_token")
	private String authoriceAccessToken;
	@Column(name="authorice_refresh_token")
	private String authoriceRefreshToken;
	@Column(name="create_user")
	private String createUser;
	@Column(name="create_date")
	private java.util.Date createDate;
	@Column(name="update_user")
	private String updateUser;
	@Column(name="update_date")
	private java.util.Date updateDate;
	//公众号创建的开放平台的appId
	@Column(name="open_appid")
	private String openAppid;
	//授权账号类型：1：公众号；2小程序
	@Column(name="auth_type")
	private String authType;
	//商户品牌id
	@Column(name="organize_id")
	private String organizeId;
	public WXopenPlatformMerchantInfo(String wxOpenPlatformId, String wxAppid, String componentVerifyTicket,
			String authoriceAccessToken, String authoriceRefreshToken, String createUser, Date createDate,
			String updateUser, Date updateDate, String openAppid, String authType, String organizeId) {
		super();
		this.wxOpenPlatformId = wxOpenPlatformId;
		this.wxAppid = wxAppid;
		this.componentVerifyTicket = componentVerifyTicket;
		this.authoriceAccessToken = authoriceAccessToken;
		this.authoriceRefreshToken = authoriceRefreshToken;
		this.createUser = createUser;
		this.createDate = createDate;
		this.updateUser = updateUser;
		this.updateDate = updateDate;
		this.openAppid = openAppid;
		this.authType = authType;
		this.organizeId = organizeId;
	}
	public WXopenPlatformMerchantInfo() {
		super();
	}
}
