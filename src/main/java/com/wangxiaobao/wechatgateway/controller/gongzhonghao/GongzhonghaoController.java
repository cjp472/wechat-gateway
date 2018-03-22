package com.wangxiaobao.wechatgateway.controller.gongzhonghao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.dto.wxopenplatfrommerchantinfo.WxOpenPlatformMerchantGongzhonghaoInfo;
import com.wangxiaobao.wechatgateway.entity.PageModel;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.gongzhonghao.gongzhonghaoListResquest;
import com.wangxiaobao.wechatgateway.service.gongzhonghao.GongzhonghaoService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
@RestController
public class GongzhonghaoController extends BaseController {
	@Autowired
	private GongzhonghaoService gongzhonghaoService;
	@Autowired
	private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService; 
	@Autowired
	private TestService testService;
	@Value("${wechat.openplatform.name}")
	private String openPlatformName;
	
	@RequestMapping("/gongzhonghao/getAuthAccessToken")
	public JsonResult getAuthAccessToken(String appId){
		if(!StringUtils.hasText(appId)){
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		return JsonResult.newInstanceDataSuccess(gongzhonghaoService.getAccessToken(appId));
	}
	
	@RequestMapping("/gongzhonghao/selectGongzhonghaoList")
	public JsonResult selectGongzhonghaoList(@RequestBody gongzhonghaoListResquest resquest){
		PageModel<WxOpenPlatformMerchantGongzhonghaoInfo> pageModel = new PageModel<WxOpenPlatformMerchantGongzhonghaoInfo>();
		pageModel.setPageNum(resquest.getPage());
		pageModel.setPageSize(resquest.getSize());
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
		List<Brand> brands = Arrays.asList(new Brand("旺小宝测试","WCBBJ"),new Brand("养个啪啪", "ygpp"),new Brand("李平1", "liping1"));
		List<String> organizationAccounts = new ArrayList<>();
		for (Brand brand : brands) {
			organizationAccounts.add(brand.getOrganizationAccount());
		}
		List<WXopenPlatformMerchantInfo> wxInfos = wXopenPlatformMerchantInfoService.selectWXopenPlatformMerchantInfoListByOrganizationAccountsAndAuthType(organizationAccounts, "1");
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
		if(StringUtils.hasText(wxInfo.getWxAppid())){
			return JsonResult.newInstanceMesFail("更新操作失败");
		}else{
			return JsonResult.newInstanceDataSuccess(wxInfo);
		}
	}
}
