package com.wangxiaobao.wechatgateway.repository.store;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.store.BannerInfo;
@Repository
public interface BannerInfoRepository extends JpaRepository<BannerInfo, String> {

	public List<BannerInfo> findByOrgAccountAndIsValidateAndMerchantAccountIsNull(String orgAccount,int isValidate);
	public List<BannerInfo> findByOrgAccountAndIsValidateAndTypeAndMerchantAccountIsNull(String orgAccount,int isValidate,int type);
	
	public List<BannerInfo> findByOrgAccountAndIsValidateAndMerchantAccount(String orgAccount,int isValidate,String merchantAccount);
	public List<BannerInfo> findByOrgAccountAndIsValidateAndMerchantAccountAndType(String orgAccount,int isValidate,String merchantAccount,int type);
}
