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
 * 模版小程序、平台小程序等
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

	/**
	 * topLimit=0 可以被绑定
	 * topLimit=1 小程序已经被绑定500次，不能再次绑定，需要启用新的
	 * type为2的平台小程序会用到这个值
	 */
	@Column(name = "top_limit")
	private String topLimit;
	@Column(name = "is_validate")
	private String isValidate;

	/**
	 * 1  模版小程序
	 * 2  平台小程序
	 */
	@Column(name = "type")
	private String type;
	@Column(name = "create_date")
	private Date createDate;
	@Column(name = "update_date")
	private Date updateDate;

	/**
	 * 小程序身份标记，由前端存入，识别是哪个小程序
	 */
	@Column(name = "code")
	private String code;
}
