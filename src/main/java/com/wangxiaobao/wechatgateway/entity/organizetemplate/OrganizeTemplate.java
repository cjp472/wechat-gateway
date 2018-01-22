package com.wangxiaobao.wechatgateway.entity.organizetemplate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * @ProjectName: wechatgateway
 * @PackageName: com.wangxiaobao.wechatgateway.entity.organizetemplate
 * @ClassName: OrganizeTemplate
 * @Description: TODO商家小程序上传的模板
 * @Copyright: Copyright (c) 2018 ALL RIGHTS RESERVED.
 * @Company:成都国胜天丰技术有限责任公司
 * @author liping_max
 * @date 2018年1月17日 下午3:28:02
 * 管理商家小程序的版本
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "t_base_organize_template")
public class OrganizeTemplate {
	@Id
	@Column(name = "miniprogram_template_id")
	private String miniprogramTemplateId;

	/**
	 * 商家小程序的ID
	 */
	@Column(name = "wx_app_id")
	private String wxAppId;

	/**
	 * 商家在权限系统的品牌账号
	 */
	@Column(name = "organization_account")
	private String organizationAccount;
	/**
	 * 商家绑定模版小程序的版本号 TemplateID
	 */
	@Column(name = "template_id")
	private String templateId;
	/**
	 * 商家小程序入口的关键字，用于识别商家，目前放商家品牌账号
	 */
	@Column(name = "ext_json")
	private String extJson;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "status")
	private String status;
	@Column(name="is_online")
	private String isOnline;
	@Column(name="is_new")
	private String isNew;
}
