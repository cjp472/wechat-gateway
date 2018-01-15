package com.wangxiaobao.wechatgateway.entity.openplatform;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import lombok.Data;

/**
 * 我们作为第三方开放平台需要提供让商户公众号关联的小程序对象
 * 
 * @author liping_max
 *
 */
@Entity
@Data
@DynamicUpdate
@Table(name = "t_base_openplatform_xiaochengxu")
public class OpenPlatformXiaochengxu {
	@Id
	@Column(name = "app_id", nullable = false)
	private String appId;
	@Column(name = "app_secret")
	private String appSecret;
	@Column(name = "top_limit")
	private String topLimit;
	@Column(name = "is_validate")
	private String isValidate;
	@Column(name = "type")
	private String type;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;
	@Column(name = "code")
	private String code;
}
