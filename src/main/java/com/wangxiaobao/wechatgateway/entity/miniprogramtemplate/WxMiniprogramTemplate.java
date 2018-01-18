package com.wangxiaobao.wechatgateway.entity.miniprogramtemplate;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@DynamicUpdate
@Table(name="t_base_wx_miniprogram_template")
public class WxMiniprogramTemplate {
	@Id
	@Column(name="draft_id")
	private String draftId;
	@Column(name="template_id")
	private String templateId;
	@Column(name="user_version")
	private String userVersion;
	@Column(name="user_desc")
	private String userDesc;
	@Column(name="wx_create_time")
	private Date wxCreateTime;
	@Column(name="type")
	private String type;
	@Column(name="is_default")
	private String isDefault;
	@Column(name="create_date")
	private Date createDate;
	@Column(name="update_date")
	private Date updateDate;
}
