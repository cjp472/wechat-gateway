package com.wangxiaobao.wechatgateway.controller.store;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.entity.header.LoginUserInfo;
import com.wangxiaobao.wechatgateway.entity.store.OrgMerchantInfoConfig;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.service.store.OrgMerchantInfoConfigService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

@RestController
@RequestMapping("/orgMerchant")
public class OrgMerchantInfoConfigController {
	@Autowired
	private OrgMerchantInfoConfigService orgMerchantInfoConfigService;
	
	@RequestMapping("/findOrgMerchantConfig")
	public JsonResult findOrgMerchantConfig(@Min(value=0) int type,LoginUserInfo loginUserInfo){
		if(!StringUtils.hasText(loginUserInfo.getOrganizationAccount())){
			throw new CommonException(ResultEnum.BRAND_NOT_FOUND);
		}
		List<OrgMerchantInfoConfig> orgMerchantInfoConfigs = new ArrayList<>();
		if(StringUtils.hasText(loginUserInfo.getMerchantAccount())){
//			OrgMerchantInfoConfig orgMerchantInfoConfig = new OrgMerchantInfoConfig(loginUserInfo.getMerchantAccount(),loginUserInfo.getOrganizationAccount(),type);
			orgMerchantInfoConfigs = orgMerchantInfoConfigService.selectByOrgAccountAndOrgAccount(loginUserInfo.getOrganizationAccount(),type,loginUserInfo.getMerchantAccount());
		}else{
			orgMerchantInfoConfigs = orgMerchantInfoConfigService.selectByOrgAccount(loginUserInfo.getOrganizationAccount(),type);
		}
		return JsonResult.newInstanceDataSuccess(orgMerchantInfoConfigs);
	}
}
