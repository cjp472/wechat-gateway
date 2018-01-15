package com.wangxiaobao.wechatgateway.form.xiaochengxu;

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
@Data
public class OpenPlatformXiaochengxuResponse {
	private String appId;
	private String appSecret;
	private String topLimit;
	private String isValidate;
	private String type;
	private Date createDate;
	private Date updateDate;
	private String code;
	private String componentAppId;
}
