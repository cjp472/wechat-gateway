package com.wangxiaobao.wechatgateway.controller.openplatform;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.service.miniprogramtemplate.WxMiniprogramTemplateService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;
/**
  * @ProjectName: wechatgateway 
  * @PackageName: com.wangxiaobao.wechatgateway.controller.openplatform 
  * @ClassName: WxMiniprogramTemplateController
  * @Description: 平台小程序模板管理
  * @Copyright: Copyright (c) 2018  ALL RIGHTS RESERVED.
  * @Company:成都国胜天丰技术有限责任公司
  * @author liping_max
  * @date 2018年3月20日 上午10:33:39
 */
@RestController
@RequestMapping("/wxMinprogramTemplate")
@Slf4j
public class WxMiniprogramTemplateController {
	@Autowired
	private WxMiniprogramTemplateService wxMiniprogramTemplateService;
	/**
	  * @methodName: selectWxMiniprogramTemplateList
	  * @Description: TODO查询平台小程序列表
	  * @param wxMiniprogramTemplate
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年3月20日 上午10:32:58
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月20日 上午10:32:58
	  * @throws
	 */
	@RequestMapping("/selectList")
	public JsonResult selectWxMiniprogramTemplateList(@RequestBody WxMiniprogramTemplate wxMiniprogramTemplate){
		return JsonResult.newInstanceDataSuccess(wxMiniprogramTemplateService.selectWxMiniprogramTemplateByCondition(wxMiniprogramTemplate));
	}
}
