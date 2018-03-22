package com.wangxiaobao.wechatgateway.controller.constantcode;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
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
			log.error("【查询某个指定版本的审核状态】参数不正确, ConstantCodeWXAuthRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		ConstantCode constantCode = new ConstantCode(request.getType(), request.getConstantKey());
		List<ConstantCode> constantCodeList = constantCodeService.selectConstantCode(constantCode);
		return JsonResult.newInstanceDataSuccess(constantCodeList);
	}
	
	/**
	  * @methodName: findConstantCodeByType
	  * @Description: TODO根据type和constantKey查询单个常用字典
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年3月16日 下午2:42:10
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月16日 下午2:42:10
	  * @throws
	 */
	@RequestMapping("/constantcode/findConstantCodeByType")
	public JsonResult findConstantCodeByType(@Valid ConstantCodeWXAuthRequest request,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			log.error("【根据Type和constantKey查询常用词典】参数不正确,ConstantCodeWXAuthRequest={}",request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
		}
		ConstantCode constantCode = new ConstantCode(request.getType(), request.getConstantKey());
		ConstantCode constantCodeResult = constantCodeService.findConstantCode(constantCode);
		return JsonResult.newInstanceDataSuccess(constantCodeResult);
	}
	/**
	  * @methodName: save
	  * @Description: TODO保存修改
	  * @param code
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年3月21日 下午3:11:57
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月21日 下午3:11:57
	  * @throws
	 */
	@RequestMapping("/constantcode/save")
	public JsonResult save(@RequestBody ConstantCode code){
		return JsonResult.newInstanceDataSuccess(constantCodeService.save(code));
	}
	
	@PostMapping("/constantcode/selectGameBaseConfig")
	public JsonResult selectGameBaseConfig(){
		ConstantCode constantCode = new ConstantCode("game_base_config","4");
		ConstantCode constantCodeJigsaw = constantCodeService.findConstantCode(constantCode);
		constantCode.setConstantKey("3");
		ConstantCode constantCodeSpeak = constantCodeService.findConstantCode(constantCode);
		JSONObject jsonJigsaw = JSONObject.parseObject(constantCodeJigsaw.getValue());
		JSONObject jsonSpeak = JSONObject.parseObject(constantCodeSpeak.getValue());
		
		jsonSpeak.put("jigsawGameWait", jsonJigsaw.getIntValue("gameWait"));
		constantCodeSpeak.setValue(jsonSpeak.toJSONString());
		return JsonResult.newInstanceDataSuccess(constantCodeSpeak);
	}
	
	@PostMapping("/constantcode/updateGameBaseConfig")
	public JsonResult updateGameBaseConfig(@RequestBody ConstantCode constantCodeRequest){
		ConstantCode constantCode = new ConstantCode("game_base_config","4");
		ConstantCode constantCodeJigsaw = constantCodeService.findConstantCode(constantCode);
		constantCode.setConstantKey("3");
		ConstantCode constantCodeSpeak = constantCodeService.findConstantCode(constantCode);
		
		constantCodeJigsaw.setValue(constantCodeRequest.getValue());
		constantCodeSpeak.setValue(constantCodeRequest.getValue());
		
		JSONObject jsonJigsaw = JSONObject.parseObject(constantCodeJigsaw.getValue());
		jsonJigsaw.put("gameWait", jsonJigsaw.getIntValue("jigsawGameWait"));
		jsonJigsaw.remove("jigsawGameWait");
		constantCodeJigsaw.setValue(jsonJigsaw.toJSONString());
		constantCodeService.save(constantCodeSpeak);
		
		JSONObject jsonSpeak = JSONObject.parseObject(constantCodeSpeak.getValue());
		jsonSpeak.remove("jigsawGameWait");
		constantCodeSpeak.setValue(jsonSpeak.toJSONString());
		constantCodeService.save(constantCodeJigsaw);
		return JsonResult.newInstanceSuccess();
	}
}
