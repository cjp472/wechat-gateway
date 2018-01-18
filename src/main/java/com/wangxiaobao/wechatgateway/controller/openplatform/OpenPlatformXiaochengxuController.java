package com.wangxiaobao.wechatgateway.controller.openplatform;

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
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramCommitRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramGetAuditstatusRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramGetCategoryRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramSetweappsupportversionRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.MiniProgramSubmitAuditRequest;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.OpenPlatformXiaochengxuResponse;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class OpenPlatformXiaochengxuController extends BaseController {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private WXopenPlatformMerchantInfoService wxPlatformMerchantInfoService;

	@RequestMapping("/xiaochengxu/findXiaochengxuByCode")
	public JsonResult findXiaochengxuByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return JsonResult.newInstanceMesFail("code未传");
		}
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findXiaochengxuByCode(code);
		OpenPlatformXiaochengxuResponse response = new OpenPlatformXiaochengxuResponse();
		BeanUtils.copyProperties(openPlatformXiaochengxu, response);
		response.setComponentAppId(appId);
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
			log.error("【为授权的小程序帐号上传小程序代码】参数不正确, miniProgramCommitRequest=【】", miniProgramCommitRequest);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.commit(miniProgramCommitRequest.getWxAppid(),
				miniProgramCommitRequest.getTemplateId(), miniProgramCommitRequest.getOrganizeId()));
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
			log.error("【获取授权小程序帐号的可选类目】参数不正确, miniProgramCommitRequest=【】", request);
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
			log.error("【获取小程序的第三方提交代码的页面配置】参数不正确, MiniProgramGetCategoryRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_get_page_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.getPage(url));
	}

	/**
	 * @methodName: submitAudit @Description: 将第三方提交的代码包提交审核 @param
	 * request @return JsonResult @createUser: liping_max @createDate:
	 * 2018年1月16日 下午4:54:36 @updateUser: liping_max @updateDate: 2018年1月16日
	 * 下午4:54:36 @throws
	 */
	@RequestMapping("/miniprogram/submitaudit")
	public JsonResult submitAudit(@Valid MiniProgramSubmitAuditRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【将第三方提交的代码包提交审核】参数不正确, MiniProgramSubmitAuditRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		
		JSONObject jsonO = new JSONObject();
		JSONArray jsonA = new JSONArray();
		JSONObject jsonItem = new JSONObject();
		jsonItem.put("first_class", request.getFirstClass());
		// address为上个接口返回的结果
		jsonItem.put("address", "pages/home");
		jsonItem.put("second_class", request.getSecondClass());
		jsonItem.put("first_id", request.getFirstId());
		jsonItem.put("second_id", request.getSecondId());
		jsonItem.put("title", request.getTitle());
		jsonA.add(jsonItem);
		jsonO.put("item_list", jsonA);
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxuService.submitAudit(request.getWxAppid(), jsonO));
	}

	/**
	 * @methodName: getAuditstatus @Description: TODO查询某个指定版本的审核状态 @param
	 * request @param bindingResult @return JsonResult @createUser:
	 * liping_max @createDate: 2018年1月16日 下午7:31:09 @updateUser:
	 * liping_max @updateDate: 2018年1月16日 下午7:31:09 @throws
	 */
	@RequestMapping("/miniprogram/getAuditstatus")
	public JsonResult getAuditstatus(@Valid MiniProgramGetAuditstatusRequest request, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			log.error("【查询某个指定版本的审核状态】参数不正确, MiniProgramSubmitAuditRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_get_auditstatus_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult
				.newInstanceDataSuccess(openPlatformXiaochengxuService.getAuditstatus(url, request.getAuditid()));
	}
	
	/**
	  * @methodName: getLatestAuditstatus
	  * @Description: TODO查询最新一次提交的审核状态
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月16日 下午7:59:49
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月16日 下午7:59:49
	  * @throws
	 */
	@RequestMapping("/miniprogram/getLatestAuditstatus")
	public JsonResult getLatestAuditstatus(@Valid MiniProgramGetCategoryRequest request,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【查询某个指定版本的审核状态】参数不正确, MiniProgramGetCategoryRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_get_latest_auditstatus_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult
				.newInstanceDataSuccess(openPlatformXiaochengxuService.getLatestAuditstatus(url));
	}
	
	/**
	  * @methodName: release
	  * @Description: TODO发布已通过审核的小程序
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月16日 下午8:03:51
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月16日 下午8:03:51
	  * @throws
	 */
	@RequestMapping("/miniprogram/release")
	public JsonResult release(@Valid MiniProgramGetCategoryRequest request,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【发布已通过审核的小程序】参数不正确, MiniProgramGetCategoryRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_release_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult
				.newInstanceDataSuccess(openPlatformXiaochengxuService.getLatestAuditstatus(url));
	}
	
	/**
	  * @methodName: setweappsupportversion
	  * @Description: TODO设置最低基础库版本
	  * @param request
	  * @param bindingResult
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月16日 下午8:25:14
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月16日 下午8:25:14
	  * @throws
	 */
	@RequestMapping("/miniprogram/setweappsupportversion")
	public JsonResult setweappsupportversion(@Valid MiniProgramSetweappsupportversionRequest request,BindingResult bindingResult){
		if (bindingResult.hasErrors()) {
			log.error("【设置最低基础库版本】参数不正确, MiniProgramSetweappsupportversionRequest=【】", request);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		String url = wxProperties.getWx_miniprogram_setweappsupportversion_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		return JsonResult
				.newInstanceDataSuccess(openPlatformXiaochengxuService.setweappsupportversion(url,request.getVersion()));
	}
}
