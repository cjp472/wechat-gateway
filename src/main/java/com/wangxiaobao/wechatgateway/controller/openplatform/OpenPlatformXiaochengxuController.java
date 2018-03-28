package com.wangxiaobao.wechatgateway.controller.openplatform;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.OpenPlatformXiaochengxuResponse;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OpenPlatformXiaochengxuController extends BaseController {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;

	@RequestMapping("/xiaochengxu/findXiaochengxuByCode")
	public JsonResult findXiaochengxuByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return JsonResult.newInstanceMesFail("code未传");
		}
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findXiaochengxuByCode(code);
		OpenPlatformXiaochengxuResponse response = new OpenPlatformXiaochengxuResponse();
		BeanUtils.copyProperties(openPlatformXiaochengxu, response);
		response.setComponentAppId(appId);
		return JsonResult.newInstanceDataSuccess(response);
	}

	@RequestMapping("/xiaochengxu/findXiaochengxuByAppId")
	public JsonResult findXiaochengxuByAppId(String appId) {
		if (StringUtils.isEmpty(appId)) {
			return JsonResult.newInstanceMesFail("appId未传");
		}
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findXiaochengxuByAppId(appId);
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxu);
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("firstClass", "餐饮");
		json.put("secondClass", "菜谱");
		json.put("firstId", "220");
		json.put("secondId", "225");
		json.put("address", "pages/home");
		json.put("title", "首页");
		System.out.println(json.toJSONString());
	}
}
