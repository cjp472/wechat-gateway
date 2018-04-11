package com.wangxiaobao.wechatgateway.controller.organizationweixin;

import javax.validation.Valid;

import com.source3g.springboot.frame.backwidow.commom.BusinessException;
import com.wangxiaobao.wechatgateway.dto.qrcodeurlverify.QrcodeUrlVerifyRequest;
import com.wangxiaobao.wechatgateway.utils.validate.groups.GroupsA;
import com.wangxiaobao.wechatgateway.utils.validate.groups.GroupsB;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.qrcodeurlverify.QrcodeUrlVerify;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddForm;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddRequest;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeRequest;
import com.wangxiaobao.wechatgateway.service.miniprogramqrcode.MiniProgramQrCodeService;
import com.wangxiaobao.wechatgateway.service.qrcodeurlverify.QrcodeUrlVerifyService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@RestController
@Slf4j
public class QrcodeUrlVerifyController extends BaseController {
	@Autowired
	private QrcodeUrlVerifyService qrcodeUrlVerifyService;
	@Autowired
	private MiniProgramQrCodeService miniProgramQrCodeService;
	
	/**
	  * @methodName: qrcodejumpdownload
	  * @Description: TODO获取校验文件名称及内容
	  * @param request
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午2:12:36
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午2:12:36
	  * @throws
	 */
	@RequestMapping("/miniprogramqrcode/qrcodejumpdownload")
	public JsonResult qrcodejumpdownload(@Valid MiniprogramQrCodeRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【获取校验文件名称及内容】参数不正确, MiniprogramQrCodeRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String result = miniProgramQrCodeService.qrcodejumpdownload(request.getWxAppid());
		return JsonResult.newInstanceSuccess();
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

	/**
	  * @methodName: saveOrcodeFile
	  * @Description: 保存二维码校验文件
	  * @Params: [qrcodeUrlVerifyRequest, bindingResult]
	  * @Return: com.wangxiaobao.wechatgateway.utils.JsonResult
	  * @createUser: ZhouTong
	  * @createDate: 2018/4/11 11:07
	  * @updateUser: ZhouTong
	  * @updateDate: 2018/4/11 11:07
	  */
	@PostMapping("/qrcode/saveQrcodeFile")
	public JsonResult saveOrcodeFile(@RequestBody @Validated(GroupsA.class) QrcodeUrlVerifyRequest qrcodeUrlVerifyRequest, BindingResult bindingResult) throws Exception{
		if(bindingResult.hasErrors())
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
		//查询数据库是否存在该appid的校验文件
		QrcodeUrlVerify qrcodeUrlVerify = qrcodeUrlVerifyService.findByWxAppId(qrcodeUrlVerifyRequest.getWxAppid());
		if(qrcodeUrlVerify == null) {
			qrcodeUrlVerify = new QrcodeUrlVerify();
			BeanUtils.copyProperties(qrcodeUrlVerifyRequest, qrcodeUrlVerify);
			qrcodeUrlVerify.setCreateDate(new Date());
		}else{
			qrcodeUrlVerify.setFileName(qrcodeUrlVerifyRequest.getFileName());
			qrcodeUrlVerify.setFileContent(qrcodeUrlVerifyRequest.getFileContent());
		}
		qrcodeUrlVerify = qrcodeUrlVerifyService.save(qrcodeUrlVerify);
		return JsonResult.newInstanceDataSuccess(qrcodeUrlVerify);
	}
}
