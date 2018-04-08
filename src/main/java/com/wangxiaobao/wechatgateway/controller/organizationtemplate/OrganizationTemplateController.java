package com.wangxiaobao.wechatgateway.controller.organizationtemplate;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.VO.organizetemplate.OrganizeTemplateVO;
import com.wangxiaobao.wechatgateway.entity.PageModel;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.form.organizetemplate.OrganizeTemplateListRequest;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

@RestController
@RequestMapping("/organizationTemplate")
public class OrganizationTemplateController {
	@Autowired
	private OrganizeTemplateService organizeTemplateService;

	/**
	 * @throws InvocationTargetException @throws
	 * IllegalAccessException @methodName:
	 * selectOrganizeTemplateList @Description: TODO分页查询商家小程序提交模板记录列表 @param
	 * request @return JsonResult @createUser: liping_max @createDate:
	 * 2018年3月20日 下午3:03:20 @updateUser: liping_max @updateDate: 2018年3月20日
	 * 下午3:03:20 @throws
	 */
	@RequestMapping("/selectOrganizeTemplateList")
	public JsonResult selectOrganizeTemplateList(@RequestBody OrganizeTemplateListRequest request)
			throws IllegalAccessException, InvocationTargetException {
		PageModel<OrganizeTemplateVO> pageModel = new PageModel<>();
		pageModel.setPageSize(request.getSize());
		pageModel.setPageNum(request.getPage());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgName", request.getOrgName());
		params.put("isOnline", request.getIsOnline());
		params.put("status", request.getStatus());
		params.put("notStatus", request.getNotStatus());
		params.put("wxAppId", request.getWxAppId());
		params.put("startPage", (request.getPage()-1)*request.getSize()>0?(request.getPage()-1)*request.getSize():0);
		params.put("endPage", request.getPage()*request.getSize());
		
		pageModel.setParams(params);
		return JsonResult
				.newInstanceDataSuccess(organizeTemplateService.selectOrganizeTemplateList(request, pageModel));
	}

	/**
	 * @methodName: selectOrganizationWxAppIdCurrentTemplate @Description: TODO
	 * 查询当前小程序的各个版本状态 @param organizeTemplate @return JsonResult @createUser:
	 * liping_max @createDate: 2018年3月27日 上午10:03:29 @updateUser:
	 * liping_max @updateDate: 2018年3月27日 上午10:03:29 @throws
	 */
	@RequestMapping("/selectCurrentTemplate")
	public JsonResult selectOrganizationWxAppIdCurrentTemplate(@RequestBody OrganizeTemplate organizeTemplate) {
		if (!StringUtils.hasText(organizeTemplate.getWxAppId())) {
			return JsonResult.newInstanceMesFail("参数传递错误");
		}
		return JsonResult.newInstanceDataSuccess(
				organizeTemplateService.selectOrganizationCurrentTemplateList(organizeTemplate));
	}
}
