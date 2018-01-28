package com.wangxiaobao.wechatgateway.controller.gongzhonghao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.service.gongzhonghao.GongzhonghaoService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
@RestController
public class GongzhonghaoController extends BaseController {
	@Autowired
	private GongzhonghaoService gongzhonghaoService;
	
	@RequestMapping("/gongzhonghao/getAuthAccessToken")
	public JsonResult getAuthAccessToken(String appId){
		if(!StringUtils.hasText(appId)){
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		return JsonResult.newInstanceDataSuccess(gongzhonghaoService.getAccessToken(appId));
	}
}
