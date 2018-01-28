package com.wangxiaobao.wechatgateway.controller.constantcode;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.constantcode.ConstantCodeWXAuthRequest;
import com.wangxiaobao.wechatgateway.service.constantcode.ConstantCodeService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
public class ConstantCodeController extends BaseController {
	@Autowired
	ConstantCodeService constantCodeService;
	/**
	  * @methodName: selectConstantCodeByType
	  * @Description: TODO查询常用字典
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午5:55:27
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午5:55:27
	  * @throws
	 */
	@PostMapping("/constantcode/selectListByType")
	public JsonResult selectConstantCodeByType(@Valid ConstantCodeWXAuthRequest request, BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【查询某个指定版本的审核状态】参数不正确, ConstantCodeWXAuthRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		ConstantCode constantCode = new ConstantCode(request.getType(), request.getConstantKey());
		List<ConstantCode> constantCodeList = constantCodeService.selectConstantCode(constantCode);
		return JsonResult.newInstanceDataSuccess(constantCodeList);
	}
}
