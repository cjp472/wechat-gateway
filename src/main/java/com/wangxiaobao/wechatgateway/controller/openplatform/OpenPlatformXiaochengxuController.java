package com.wangxiaobao.wechatgateway.controller.openplatform;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.MiniprogramTemplateTypeEnum;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramBindTesterRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramCommitRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramGetAuditstatusRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramGetCategoryRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramSetweappsupportversionRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.OpenPlatformXiaochengxuResponse;
import com.wangxiaobao.wechatgateway.service.constantcode.ConstantCodeService;
import com.wangxiaobao.wechatgateway.service.miniprogramtemplate.WxMiniprogramTemplateService;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OpenPlatformXiaochengxuController extends BaseController {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private WXopenPlatformMerchantInfoService wxPlatformMerchantInfoService;
	@Autowired
	private ConstantCodeService constantCodeService;
	@Autowired
	private WxMiniprogramTemplateService wxMiniprogramTemplateService;
	@Autowired
	private OrganizeTemplateService organizeTemplateService;

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

	/**
	 * @methodName: modifyDomain @Description: 设置小程序服务器域名 @param wxAppid @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年1月15日
	 *              下午7:17:53 @updateUser: liping_max @updateDate: 2018年1月15日
	 *              下午7:17:53 @throws
	 */
	@RequestMapping("/miniprogram/modifyDomain")
	public JsonResult modifyDomain(String wxAppid, String action) {
		String url = wxProperties.getWx_modify_domain_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = openPlatformXiaochengxuService.modifyDomain(wxAppid, action);
		return JsonResult.newInstanceDataSuccess(result);
	}

	/**
	 * @methodName: modifyDomain @Description: 设置小程序业务域名 @param wxAppid @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年1月15日
	 *              下午7:17:53 @updateUser: liping_max @updateDate: 2018年1月15日
	 *              下午7:17:53 @throws
	 */
	@RequestMapping("/miniprogram/setwebviewdomain")
	public JsonResult setwebviewdomain(String wxAppid, String action) {
		String url = wxProperties.getWx_setwebviewdomain_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = openPlatformXiaochengxuService.setwebviewdomain(wxAppid, action);
		return JsonResult.newInstanceDataSuccess(result);
	}

	/**
	 * @methodName: commit @Description: 为授权的小程序帐号上传小程序代码 @param
	 *              miniProgramCommitRequest @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午4:12:56 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午4:12:56 @throws
	 */
	@RequestMapping("/miniprogram/commit")
	public JsonResult commit(@Valid MiniProgramCommitRequest miniProgramCommitRequest, BindingResult bindingResult) {
		// TODO版本号后续需要通过数据库读取
		if (bindingResult.hasErrors()) {
			log.error("【为授权的小程序帐号上传小程序代码】参数不正确, miniProgramCommitRequest={}", miniProgramCommitRequest);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		JSONObject resultJson = openPlatformXiaochengxuService.commit(miniProgramCommitRequest.getWxAppid(),
				miniProgramCommitRequest.getTemplateId(), miniProgramCommitRequest.getOrganizationAccount());
		// 将商家上传的版本记录到数据库
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setCreateDate(new Date());
		organizeTemplate.setTemplateId(miniProgramCommitRequest.getTemplateId());
		organizeTemplate.setExtJson(resultJson.getString("ext_json"));
		organizeTemplate.setMiniprogramTemplateId(KeyUtil.genUniqueKey());
		organizeTemplate.setOrganizationAccount(miniProgramCommitRequest.getOrganizationAccount());
		organizeTemplate.setWxAppId(miniProgramCommitRequest.getWxAppid());
		organizeTemplate.setStatus(OrganizeTemplateStatusEnum.UPLOAD.getStatus());
		organizeTemplate.setIsOnline("0");
		organizeTemplate.setIsNew("1");
		// 设置当前为new为1，其它为0
		organizeTemplateService.updateOrganizeTemplateIsNew(miniProgramCommitRequest.getWxAppid(), "0");
		OrganizeTemplate organizeTemplateResult = organizeTemplateService.save(organizeTemplate);
		if (null == organizeTemplateResult) {
			log.error("将商家小程序{}版本{}提交审核失败", miniProgramCommitRequest.getWxAppid(),
					miniProgramCommitRequest.getTemplateId());
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(),
					"提交商家小程序" + miniProgramCommitRequest.getWxAppid() + "版本" + miniProgramCommitRequest.getTemplateId()
							+ ",审核失败");
		}
		return JsonResult.newInstanceDataSuccess(resultJson);
	}

	/**
	 * @methodName: getCategory @Description:获取授权小程序帐号的可选类目 @param
	 *              request @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午4:21:17 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午4:21:17 @throws
	 */
	@RequestMapping("/miniprogram/getcategory")
	public JsonResult getCategory(@Valid MiniProgramGetCategoryRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【获取授权小程序帐号的可选类目】参数不正确, miniProgramCommitRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.getCategory(request.getWxAppid()));
	}

	/**
	 * @methodName: getPage @Description: 获取小程序的第三方提交代码的页面配置 @param
	 *              request @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午4:39:17 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午4:39:17 @throws
	 */
	@RequestMapping("/miniprogram/getpage")
	public JsonResult getPage(@Valid MiniProgramGetCategoryRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【获取小程序的第三方提交代码的页面配置】参数不正确, MiniProgramGetCategoryRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_get_page_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.getPage(url));
	}

	/**
	 * @methodName: submitAudit @Description: 将第三方提交的代码包提交审核 @param
	 *              request @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午4:54:36 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午4:54:36 @throws
	 */
	@RequestMapping("/miniprogram/submitaudit")
	public JsonResult submitAudit(@Valid MiniProgramGetCategoryRequest request, BindingResult bindingResult,
			String organizationAccount) {
		if (bindingResult.hasErrors()) {
			log.error("【将第三方提交的代码包提交审核】参数不正确, MiniProgramGetCategoryRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		// 获取商家小程序账号需要配置的address和类目
		ConstantCode constantCode = new ConstantCode("templatePage", "yellowpages");
		constantCode = constantCodeService.findConstantCode(constantCode);
		WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateService
				.findWxMiniprogramTemplateDefaultByType(MiniprogramTemplateTypeEnum.PLATFORM_PAGE_TEMPLATE.getType());
		JSONObject submitauditparamJson = JSONObject.parseObject(constantCode.getValue());
		// 设置tag，将小程序名字设置到tag里面
		WXopenPlatformMerchantInfo wxInfo = wxPlatformMerchantInfoService.getByWXAppId(request.getWxAppid());
		String tag = wxInfo.getNickName();
		JSONArray itemListJsonA = submitauditparamJson.getJSONArray("item_list");
		for (int i = 0; i < itemListJsonA.size(); i++) {
			JSONObject itemJson = itemListJsonA.getJSONObject(i);
			if (StringUtils.isEmpty(itemJson.getString("tag"))) {
				itemJson.put("tag", tag);
			} else {
				itemJson.put("tag", itemJson.getString("tag") + " " + tag);
			}
		}
		JSONObject jsonResult = openPlatformXiaochengxuService.submitAudit(request.getWxAppid(), submitauditparamJson);
		// 将之前的发布设为旧
		OrganizeTemplate orTemplate = new OrganizeTemplate();
		orTemplate.setIsNew("1");
		orTemplate.setOrganizationAccount(organizationAccount);
		OrganizeTemplate organizeTemplateOld = organizeTemplateService.findOrganizeTemplateBy(orTemplate);
		if (null != organizeTemplateOld) {
			organizeTemplateOld.setIsNew("0");
			organizeTemplateOld.setStatus(OrganizeTemplateStatusEnum.CANCEL.getStatus());
			organizeTemplateService.save(organizeTemplateOld);
		}
		// 保存新的商户模板信息
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setCreateDate(new Date());
		organizeTemplate.setTemplateId(wxMiniprogramTemplate.getTemplateId());
		organizeTemplate.setExtJson("");
		organizeTemplate.setMiniprogramTemplateId(KeyUtil.genUniqueKey());
		organizeTemplate.setOrganizationAccount(organizationAccount);
		organizeTemplate.setWxAppId(request.getWxAppid());
		organizeTemplate.setStatus(OrganizeTemplateStatusEnum.AUDITING.getStatus());
		organizeTemplate.setIsOnline("0");
		organizeTemplate.setIsNew("1");
		if (jsonResult.containsKey("auditid")) {
			organizeTemplate.setAuditid(jsonResult.getString("auditid"));
		}

		organizeTemplateService.save(organizeTemplate);
		return JsonResult.newInstanceSuccess();
	}

	/**
	 * @methodName: getAuditstatus @Description: TODO查询某个指定版本的审核状态 @param
	 *              request @param bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午7:31:09 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午7:31:09 @throws
	 */
	@RequestMapping("/organizationTemplate/getAuditstatus")
	public JsonResult getAuditstatus(@Valid MiniProgramGetAuditstatusRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【查询某个指定版本的审核状态】参数不正确, MiniProgramSubmitAuditRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		JSONObject jsonResult = openPlatformXiaochengxuService.getAuditstatus(request.getWxAppid(),
				request.getAuditid());
		// 返回成功
		if (ResultEnum.SUCCESS.getCode() == jsonResult.getIntValue("errcode")) {
			OrganizeTemplate organizeTemplate = new OrganizeTemplate();
			organizeTemplate.setAuditid(jsonResult.getLong("auditid").toString());
			OrganizeTemplate orTemplate = organizeTemplateService.findOrganizeTemplateBy(organizeTemplate);
			orTemplate.setStatus(jsonResult.getIntValue("auditid") + "");
			return JsonResult.newInstanceDataSuccess(organizeTemplateService.save(orTemplate));
		}
		return JsonResult.newInstanceDataFail(jsonResult);
	}

	/**
	 * @methodName: getLatestAuditstatus @Description: TODO查询最新一次提交的审核状态 @param
	 *              request @param bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午7:59:49 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午7:59:49 @throws
	 */
	@RequestMapping("/miniprogram/getLatestAuditstatus")
	public JsonResult getLatestAuditstatus(@Valid MiniProgramGetCategoryRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【查询某个指定版本的审核状态】参数不正确, MiniProgramGetCategoryRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_get_latest_auditstatus_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.getLatestAuditstatus(url));
	}

	/**
	 * @methodName: setweappsupportversion @Description: TODO设置最低基础库版本 @param
	 *              request @param bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午8:25:14 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午8:25:14 @throws
	 */
	@RequestMapping("/miniprogram/setweappsupportversion")
	public JsonResult setweappsupportversion(@Valid MiniProgramSetweappsupportversionRequest request,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【设置最低基础库版本】参数不正确, MiniProgramSetweappsupportversionRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(
				openPlatformXiaochengxuService.setweappsupportversion(request.getWxAppid(), request.getVersion()));
	}

	/**
	 * @methodName: release @Description: TODO发布已通过审核的小程序 @param request @param
	 *              bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午8:03:51 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午8:03:51 @throws
	 */
	@RequestMapping("/organizationTemplate/release")
	public JsonResult release(@Valid MiniProgramGetCategoryRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【设置最低基础库版本】参数不正确, MiniProgramGetCategoryRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setWxAppId(request.getWxAppid());
		organizeTemplate = organizeTemplateService.findOrganizeTemplateBy(organizeTemplate);
		organizeTemplate.setStatus(OrganizeTemplateStatusEnum.SUCCESS.getStatus());
		organizeTemplateService.save(organizeTemplate);
		openPlatformXiaochengxuService.release(organizeTemplate.getWxAppId());
		organizeTemplateService.updateOrganizeTemplateIsOnline(organizeTemplate.getWxAppId(), "1");
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.release(request.getWxAppid()));
	}

	@RequestMapping("/miniprogram/bindTester")
	public JsonResult bindTester(@Valid MiniProgramBindTesterRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【设置最低基础库版本】参数不正确, MiniProgramBindTesterRequest={}", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(
				openPlatformXiaochengxuService.bindTester(request.getWechatid(), request.getWxAppid()));
	}

	/**
	 * @methodName: undocodeaudit @Description: 小程序版本取消审核 @param wxAppid @return
	 *              JsonResult @createUser: liping_max @createDate: 2018年2月7日
	 *              上午11:29:42 @updateUser: liping_max @updateDate: 2018年2月7日
	 *              上午11:29:42 @throws
	 */
	@RequestMapping("/miniprogram/undocodeaudit")
	public JsonResult undocodeaudit(String wxAppid) {
		if (!StringUtils.hasText(wxAppid)) {
			return JsonResult.newInstanceMesFail("小程序appId必填");
		}
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.undocodeaudit(wxAppid));
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
