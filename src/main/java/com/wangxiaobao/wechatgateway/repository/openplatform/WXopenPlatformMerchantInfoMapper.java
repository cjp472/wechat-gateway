package com.wangxiaobao.wechatgateway.repository.openplatform;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;

@Repository
public interface WXopenPlatformMerchantInfoMapper extends JpaRepository<WXopenPlatformMerchantInfo, String>{

	public WXopenPlatformMerchantInfo findByWxAppid(String wxAppid);
	
	public List<WXopenPlatformMerchantInfo> findByAuthTypeAndOrganizationAccount(String authType,String organizationAccount);

	public WXopenPlatformMerchantInfo findByOrganizationAccountAndAuthType(String brandAccount,String authType);
	
	public List<WXopenPlatformMerchantInfo> findByAuthType(String authType);
	
	@Query(value = "select i from WXopenPlatformMerchantInfo i where i.organizationAccount in (:organizationAccounts) and i.authType=:authType")
	public List<WXopenPlatformMerchantInfo> selectListByOrganizationAccounts(@Param(value = "organizationAccounts") List<String> organizationAccounts,@Param("authType") String authType);
}
