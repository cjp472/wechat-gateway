package com.wangxiaobao.wechatgateway.repository.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.store.OrgMerchantInfoConfig;
@Repository
public interface OrgMerchantInfoConfigRepository extends JpaRepository<OrgMerchantInfoConfig, String>{

	public List<OrgMerchantInfoConfig> findByOrgAccountAndTypeAndIsValidateAndMerchantAccountIsNull(String orgAccount,int type,int isValidate);
	public List<OrgMerchantInfoConfig> findByOrgAccountAndTypeAndIsValidateAndMerchantAccount(String orgAccount,int type,int isValidate,String merchantAccount);
}
