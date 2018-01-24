package com.wangxiaobao.wechatgateway.controller.constantcode;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
import com.wangxiaobao.wechatgateway.form.constantcode.ConstantCodeWXAuthRequest;
import com.wangxiaobao.wechatgateway.service.constantcode.ConstantCodeService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

@RestController
public class ConstantCodeController extends BaseController {
	@Autowired
	ConstantCodeService constantCodeService;
	
	@PostMapping("/constantcode/selectListByType")
	public JsonResult selectConstantCodeByType(@Valid ConstantCodeWXAuthRequest request){
		ConstantCode constantCode = new ConstantCode(request.getType(), request.getConstantKey());
		List<ConstantCode> constantCodeList = constantCodeService.selectConstantCode(constantCode);
		return JsonResult.newInstanceDataSuccess(constantCodeList);
	}
}
