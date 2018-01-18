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
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "t_base_organize_template")
public class OrganizeTemplate {
	@Id
	@Column(name = "miniprogram_template_id")
	private String miniprogramTemplateId;
	@Column(name = "wx_app_id")
	private String wxAppId;
	@Column(name = "organize_id")
	private String organizeId;
	@Column(name = "draft_id")
	private String draftId;
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
