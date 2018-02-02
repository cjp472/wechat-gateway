package com.wangxiaobao.wechatgateway.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.store.OrgMerchantInfoConfig;
import com.wangxiaobao.wechatgateway.repository.store.OrgMerchantInfoConfigRepository;

@Service
public class OrgMerchantInfoConfigService {
	@Autowired
	private OrgMerchantInfoConfigRepository orgMerchantInfoConfigRepository;

	public List<OrgMerchantInfoConfig> selectByCondition(OrgMerchantInfoConfig orgMerchantInfoConfig) {
		Example<OrgMerchantInfoConfig> example = Example.of(orgMerchantInfoConfig);
		return orgMerchantInfoConfigRepository.findAll(example);
	}

	public List<OrgMerchantInfoConfig> selectByOrgAccount(String orgAccount,int type) {
		return orgMerchantInfoConfigRepository.findByOrgAccountAndTypeAndIsValidateAndMerchantAccountIsNull(orgAccount,type,1);
	}
	public List<OrgMerchantInfoConfig> selectByOrgAccountAndOrgAccount(String orgAccount,int type,String merchantAccount) {
		return orgMerchantInfoConfigRepository.findByOrgAccountAndTypeAndIsValidateAndMerchantAccount(orgAccount,type,1,merchantAccount);
	}
}
