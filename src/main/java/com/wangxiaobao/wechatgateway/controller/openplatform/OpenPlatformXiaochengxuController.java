package com.wangxiaobao.wechatgateway.controller.openplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

@RestController
public class OpenPlatformXiaochengxuController {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	
	@RequestMapping("/xiaochengxu/findXiaochengxuByCode")
	public JsonResult findXiaochengxuByCode(String code){
		if(StringUtils.isEmpty(code)){
			return JsonResult.newInstanceMesFail("code未传");
		}
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findXiaochengxuByCode(code);
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxu);
	}
}
