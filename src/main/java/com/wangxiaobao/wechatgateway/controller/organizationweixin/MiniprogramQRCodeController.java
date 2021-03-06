package com.wangxiaobao.wechatgateway.controller.organizationweixin;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.qrcodeurlverify.QrcodeUrlVerify;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddForm;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeDeleteRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeRequest;
import com.wangxiaobao.wechatgateway.service.miniprogramqrcode.MiniProgramQrCodeService;
import com.wangxiaobao.wechatgateway.service.qrcodeurlverify.QrcodeUrlVerifyService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class MiniprogramQRCodeController extends BaseController {
	@Autowired
	private MiniProgramQrCodeService miniProgramQrCodeService;
	@Autowired
	private QrcodeUrlVerifyService qrcodeUrlVerifyService;
	@RequestMapping("/miniproqrcode/tABaw4ZCqL.txt")
	public String wxfiletest(){
		return "84d607cdd0d05554ebfc09bd2467308d";
	}
	
	/**
	  * @methodName: qrcodejumpget
	  * @Description: 获取已设置的二维码规则
	  * @param request
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 下午9:40:41
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 下午9:40:41
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumpget")
	public JsonResult qrcodejumpget(@Valid MiniprogramQrCodeRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【获取已设置的二维码规则】参数不正确, MiniprogramQrCodeRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(miniProgramQrCodeService.qrcodejumpget(request));
	}
	/**
	  * @methodName: qrcodejumpadd
	  * @Description: TODO增加或修改二维码规则
	  * @param request
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 上午10:38:39
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 上午10:38:39
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumpadd")
	public JsonResult qrcodejumpadd(@Valid @RequestBody MiniprogramQrCodeAddRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【增加或修改二维码规则】参数不正确, MiniprogramQrCodeAddRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		MiniprogramQrCodeAddForm miniprogramQrCodeAddForm = new MiniprogramQrCodeAddForm();
		BeanUtils.copyProperties(request, miniprogramQrCodeAddForm);
		return JsonResult.newInstanceDataSuccess(miniProgramQrCodeService.qrcodejumpadd(miniprogramQrCodeAddForm, request.getWxAppid()));
	}
	
	/**
	  * @methodName: qrcodejumpdelete
	  * @Description: TODO删除二维码规则
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午6:00:09
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午6:00:09
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumpdelete")
	public JsonResult qrcodejumpdelete(@Valid  @RequestBody MiniprogramQrCodeDeleteRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【删除二维码规则】参数不正确, MiniProgramGetCategoryRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(miniProgramQrCodeService.qrcodejumpdelete(request.getPrefix(), request.getWxAppid()));
	}
	
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
	  * @methodName: qrcodejumppublish
	  * @Description: TODO
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午6:37:08
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午6:37:08
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumppublish")
	public JsonResult qrcodejumppublish(@Valid @RequestBody MiniprogramQrCodeDeleteRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【发布已设置的二维码规则】参数不正确, MiniprogramQrCodeDeleteRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(miniProgramQrCodeService.qrcodejumppublish(request.getPrefix(), request.getWxAppid()));
	}
	
	@RequestMapping("/q/{organizationAccount}/{fileName}")
	public String verifyOrganizationQrcodeFile(@PathVariable(name = "organizationAccount") String organizationAccount,@PathVariable(name = "fileName") String fileName,HttpServletResponse response){
		log.info("文件效验地址请求参数"+organizationAccount+","+fileName);
		QrcodeUrlVerify qrcodeUrlVerify = qrcodeUrlVerifyService.findByFileName(fileName+".txt");
		return qrcodeUrlVerify.getFileContent();
	}
	
}
