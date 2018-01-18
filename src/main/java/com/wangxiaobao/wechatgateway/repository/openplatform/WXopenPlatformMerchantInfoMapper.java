package com.wangxiaobao.wechatgateway.repository.openplatform;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;

@Repository
public interface WXopenPlatformMerchantInfoMapper extends JpaRepository<WXopenPlatformMerchantInfo, String>{

	public WXopenPlatformMerchantInfo findByWxAppid(String wxAppid);
	
	public List<WXopenPlatformMerchantInfo> findByAuthTypeAndOrganizationAccount(String authType,String organizationAccount);
}
