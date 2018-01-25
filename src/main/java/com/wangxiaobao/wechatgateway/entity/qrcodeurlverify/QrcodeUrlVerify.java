package com.wangxiaobao.wechatgateway.entity.qrcodeurlverify;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Entity
@Data
@DynamicUpdate
@Table(name = "t_base_miniprogram_qrcode_url_verify")
public class QrcodeUrlVerify {
	@Id
	private String wxAppid;
	private String fileName;
	private String fileContent;
	private Date createDate;
	private String createUser;
}
