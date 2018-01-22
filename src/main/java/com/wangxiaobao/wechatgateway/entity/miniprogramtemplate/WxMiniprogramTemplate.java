package com.wangxiaobao.wechatgateway.entity.miniprogramtemplate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

/**
 * 管理模版小程序版本
 */
@Data
@Entity
@DynamicUpdate
@Table(name="t_base_wx_miniprogram_template")
public class WxMiniprogramTemplate {
	@Id
	@Column(name="template_id")
	private String templateId;
	@Column(name="draft_id")
	private String draftId;

	/**
	 * 模版小程序代码版本号，我们手动填入
	 */
	@Column(name="user_version")
	private String userVersion;
	@Column(name="user_desc")
	private String userDesc;
	@Column(name="wx_create_time")
	private Date wxCreateTime;

	/**
	 * 用于小程序模版区分，以后可能会有多个小程序模版
	 */
	@Column(name="type")
	private String type;

	/**
	 * 标记模版小程序的稳定版本
	 */
	@Column(name="is_default")
	private String isDefault;
	@Column(name="create_date")
	private Date createDate;
	@Column(name="update_date")
	private Date updateDate;
}
