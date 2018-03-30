package com.wangxiaobao.wechatgateway.controller.organizationweixin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.dto.wxopenplatfrommerchantinfo.WxOpenPlatformMerchantGongzhonghaoInfo;
import com.wangxiaobao.wechatgateway.entity.PageModel;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.gongzhonghao.gongzhonghaoListResquest;
import com.wangxiaobao.wechatgateway.service.gongzhonghao.GongzhonghaoService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.store.BrandInfoService;
import com.wangxiaobao.wechatgateway.service.weixinapi.WXApiService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
@RestController
public class GongzhonghaoController extends BaseController {
	@Autowired
	private GongzhonghaoService gongzhonghaoService;
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService; 
	@Autowired
	private BrandInfoService brandInfoService;
	@Value("${wechat.openplatform.name}")
	private String openPlatformName;
	@Value("${organization.findLikeOrgName}")
	private String findLikeOrgNameUrl;
	
	@RequestMapping("/gongzhonghao/getAuthAccessToken")
	public JsonResult getAuthAccessToken(String appId){
		if(!StringUtils.hasText(appId)){
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		return JsonResult.newInstanceDataSuccess(gongzhonghaoService.getAccessToken(appId));
	}
	
	@RequestMapping("/gongzhonghao/selectGongzhonghaoList")
	public JsonResult selectGongzhonghaoList(@RequestBody gongzhonghaoListResquest request) throws Exception{
		PageModel<WxOpenPlatformMerchantGongzhonghaoInfo> pageModel = new PageModel<WxOpenPlatformMerchantGongzhonghaoInfo>();
		pageModel.setPageNum(request.getPage());
		pageModel.setPageSize(request.getSize());
		class Brand{
			private String name;
			private String organizationAccount;
			private Brand(String name, String organizationAccount) {
				super();
				this.name = name;
				this.organizationAccount = organizationAccount;
			}
			public String getName() {
				return name;
			}
			public String getOrganizationAccount() {
				return organizationAccount;
			}
		}
		JSONObject JSONResult = JSONObject.parseObject(brandInfoService.findLikeOrgName(request, findLikeOrgNameUrl));
		JSONObject JSONData = JSONResult.getJSONObject("data");
		pageModel.setCount(JSONData.getLongValue("count"));
		JSONArray JSONAData = JSONData.getJSONArray("datas");
		List<Brand> brands = new ArrayList<>();
		List<String> organizationAccounts = new ArrayList<>();
		for(int i=0;i<JSONAData.size();i++){
			JSONObject JSONOorg = JSONAData.getJSONObject(i);
			Brand brand = new Brand(JSONOorg.getString("orgName"), JSONOorg.getString("orgAccount"));
			brands.add(brand);
			organizationAccounts.add(JSONOorg.getString("orgAccount"));
		}
		if(!StringUtils.hasText(request.getAuthType())){
			request.setAuthType("1");
		}
		List<WXopenPlatformMerchantInfo> wxInfos = new ArrayList<>();
		if(!organizationAccounts.isEmpty()){
			wxInfos = wXopenPlatformMerchantInfoService.selectWXopenPlatformMerchantInfoListByOrganizationAccountsAndAuthType(organizationAccounts, request.getAuthType());
		}
		List<WxOpenPlatformMerchantGongzhonghaoInfo> wxGongzhonghaoInfos = new ArrayList<>();
		for (Brand brand : brands) {
			WxOpenPlatformMerchantGongzhonghaoInfo wxOpenPlatformMerchantGongzhonghaoInfo = new WxOpenPlatformMerchantGongzhonghaoInfo();
			wxOpenPlatformMerchantGongzhonghaoInfo.setBrandName(brand.getName());
			wxOpenPlatformMerchantGongzhonghaoInfo.setOrganizationAccount(brand.getOrganizationAccount());
			for (WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo : wxInfos) {
				if(brand.getOrganizationAccount().equals(wXopenPlatformMerchantInfo.getOrganizationAccount())){
					BeanUtils.copyProperties(wXopenPlatformMerchantInfo, wxOpenPlatformMerchantGongzhonghaoInfo);	
				}
			}
			wxOpenPlatformMerchantGongzhonghaoInfo.setOpenPlatformName(openPlatformName);
			if(StringUtils.hasText(wxOpenPlatformMerchantGongzhonghaoInfo.getWxAppid())){
				wxOpenPlatformMerchantGongzhonghaoInfo.setStatus("已授权");
			}else{
				wxOpenPlatformMerchantGongzhonghaoInfo.setStatus("未授权");
			}
			wxGongzhonghaoInfos.add(wxOpenPlatformMerchantGongzhonghaoInfo);
		}
		pageModel.setDatas(wxGongzhonghaoInfos);
		return JsonResult.newInstanceDataSuccess(pageModel);
	}
	
	@RequestMapping("/gongzhonghao/refreshAuthInfo")
	public JsonResult refreshGongzhonghaoAuthInfo(String wxAppId){
		//刷新调用凭证
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoService.manualRefreshApiAuthorizerToken(wxAppId);
		if(!StringUtils.hasText(wxInfo.getWxAppid())){
			return JsonResult.newInstanceMesFail("更新操作失败");
		}else{
			return JsonResult.newInstanceDataSuccess(wxInfo);
		}
	}

	@RequestMapping("/gongzhonghao/getWXopenPlatformMerchantInfo")
	public @ResponseBody JsonResult getWXopenPlatformMerchantInfo(String wxAppId) {
		return JsonResult
				.newInstanceDataSuccess(wXopenPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppId));
	}
}
