package com.wangxiaobao.wechatgateway.entity.openplatform;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 管理授权给我们第三方平台的小程序信息
 */
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

	/**
	 * 小程序ID
	 */
	@Column(name="wx_appid")
	private String wxAppid;

	/**
	 * 微信对第三方平台的主动验证码。10分钟发一次，此处不存储
	 */
	@Column(name="component_verify_ticket")
	private String componentVerifyTicket;

	/**
	 * 操作商家小程序的授权码，每个小程序都有一个授权码用于操作商家小程序
	 */
	@Column(name="authorice_access_token")
	private String authoriceAccessToken;

	/**
	 * 每个商家的固定token，用于刷新每个商家的authorice_access_token
	 */
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
	/**
	 * 为了绑定商家的公众号和小程序获取unionID，自动为商家创建的隐形开放平台ID
	 */
	@Column(name="open_appid")
	private String openAppid;
	//授权账号类型：1：公众号；2小程序
	@Column(name="auth_type")
	private String authType;
	//商户品牌id
	@Column(name="organization_account")
	private String organizationAccount;

	/**
	 * 商家公众号或小程序名字
	 */
	@Column(name="nick_name")
	private String nickName;

	/**
	 * 商家公众号或小程序头像
	 */
	@Column(name="head_img")
	private String headImg;

	/**
	 * 商家授权类型
	 */
	@Column(name="verify_type_info")
	private String verifyTypeInfo;

	/**
	 * 商家公众号或小程序的原始ID
	 */
	@Column(name="user_name")
	private String userName;
	
	public WXopenPlatformMerchantInfo() {
		super();
	}
	public WXopenPlatformMerchantInfo(String wxOpenPlatformId, String wxAppid, String componentVerifyTicket,
			String authoriceAccessToken, String authoriceRefreshToken, String createUser, Date createDate,
			String updateUser, Date updateDate, String openAppid, String authType, String organizationAccount, String nickName,
			String headImg, String verifyTypeInfo, String userName) {
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
		this.organizationAccount = organizationAccount;
		this.nickName = nickName;
		this.headImg = headImg;
		this.verifyTypeInfo = verifyTypeInfo;
		this.userName = userName;
	}
}
