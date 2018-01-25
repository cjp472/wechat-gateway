package com.wangxiaobao.wechatgateway.controller.qrcodeurlverify;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeRequest;
import com.wangxiaobao.wechatgateway.service.qrcodeurlverify.QrcodeUrlVerifyService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
@RestController
public class QrcodeUrlVerifyController extends BaseController {
	@Autowired
	private QrcodeUrlVerifyService qrcodeUrlVerifyService;
	
	public JsonResult qrcodejumpdownload(@Valid MiniprogramQrCodeRequest request){
		
		return null;
	}
}
