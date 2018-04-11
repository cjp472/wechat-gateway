package com.wangxiaobao.wechatgateway.dto.qrcodeurlverify;

import com.wangxiaobao.wechatgateway.utils.validate.groups.GroupsA;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class QrcodeUrlVerifyRequest {

	@NotBlank(message = "AppId不能为空",groups = {GroupsA.FirstValidate.class})
	private String wxAppid;
	@NotBlank(message = "文件名称不能为空",groups = {GroupsA.SecondValidate.class})
	private String fileName;
	@NotBlank(message = "文件内容不能为空",groups = {GroupsA.ThirdValidate.class})
	private String fileContent;
}
