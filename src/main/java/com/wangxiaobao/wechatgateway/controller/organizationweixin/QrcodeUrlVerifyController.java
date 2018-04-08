package com.wangxiaobao.wechatgateway.controller.organizationweixin;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.qrcodeurlverify.QrcodeUrlVerify;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddForm;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddRequest;
import com.wangxiaobao.wechatgateway.service.miniprogramqrcode.MiniProgramQrCodeService;
import com.wangxiaobao.wechatgateway.service.qrcodeurlverify.QrcodeUrlVerifyService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
@RestController
@Slf4j
public class QrcodeUrlVerifyController extends BaseController {
	@Autowired
	private QrcodeUrlVerifyService qrcodeUrlVerifyService;
	@Autowired
	private MiniProgramQrCodeService miniProgramQrCodeService;
	
	/**
	  * @methodName: qrcodejumpadd
	  * @Description: TODO完整设置发布二维码规则
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午6:41:21
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午6:41:21
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumpaddAndPublish")
	public JsonResult qrcodejumpaddAndPublish(@Valid MiniprogramQrCodeAddRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【增加或修改二维码规则】参数不正确, MiniprogramQrCodeAddRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		MiniprogramQrCodeAddForm miniprogramQrCodeAddForm = new MiniprogramQrCodeAddForm();
		BeanUtils.copyProperties(request, miniprogramQrCodeAddForm);
		return JsonResult.newInstanceDataSuccess(miniProgramQrCodeService.qrcodejumpaddAndPush(miniprogramQrCodeAddForm, request.getWxAppid()));
	}
	
	/**
	  * @methodName: findQrcodeFile
	  * @Description: TODO 根据wxAppid查询效验文件
	  * @param wxAppId
	  * @return String
	  * @createUser: liping_max
	  * @createDate: 2018年3月27日 上午9:48:09
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月27日 上午9:48:09
	  * @throws
	 */
	@RequestMapping("/qrcode/findQrcodeFile")
	public JsonResult findQrcodeFile(String wxAppId){
		if(!StringUtils.hasText(wxAppId)){
			log.error("参数传递错误wxAppId={}",wxAppId);
			return JsonResult.newInstanceMesFail("参数传递错误");
		}
		QrcodeUrlVerify qrcodeUrlVerify = qrcodeUrlVerifyService.findByWxAppId(wxAppId);
		if(ObjectUtils.isEmpty(qrcodeUrlVerify)){
			return JsonResult.newInstanceSuccess();
		}
		return JsonResult.newInstanceDataSuccess(qrcodeUrlVerify.getFileContent());
	}
}
