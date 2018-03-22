package com.wangxiaobao.wechatgateway.controller.openplatform;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.platformminiprogram.PlatformMiniprogramListRequest;
import com.wangxiaobao.wechatgateway.form.platformminiprogram.PlatformMiniprogramSaveRequest;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

/**
 * @ProjectName: wechatgateway
 * @PackageName: com.wangxiaobao.wechatgateway.controller.openplatform
 * @ClassName: OpenPlatformXiaochengxuManageController
 * @Description: TODO 管理后台调用接口Controller
 * @Copyright: Copyright (c) 2018 ALL RIGHTS RESERVED.
 * @Company:成都国胜天丰技术有限责任公司
 * @author liping_max
 * @date 2018年3月16日 下午4:21:07
 */
@RestController
@RequestMapping("/platformXiaoCXManage")
@Slf4j
public class OpenPlatformXiaochengxuManageController {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;

	@RequestMapping("/selectByCondition")
	public JsonResult selectOpenPlatformXiaochengxuByCondition(
			@RequestBody PlatformMiniprogramListRequest platformMiniprogramListRequest) {
		OpenPlatformXiaochengxu openPlatformXiaochengxu = new OpenPlatformXiaochengxu();
		BeanUtils.copyProperties(platformMiniprogramListRequest, openPlatformXiaochengxu);
		Sort sort = new Sort(Direction.DESC,"createDate");
		PageRequest pageRequest = new PageRequest(platformMiniprogramListRequest.getPage()-1, platformMiniprogramListRequest.getSize(),sort);
		return JsonResult.newInstanceDataSuccess(
				openPlatformXiaochengxuService.selectOpenPlatformXiaochengxu(openPlatformXiaochengxu, pageRequest));
	}
	
	@RequestMapping("/save")
	public JsonResult saveOpenPlatformXiaochengxu(@Valid PlatformMiniprogramSaveRequest request,BindingResult bindingResult){
		if(bindingResult.hasErrors()){
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
		}
		openPlatformXiaochengxuService.saveOrUpdate(request);
		return JsonResult.newInstanceSuccess();
	}
}
