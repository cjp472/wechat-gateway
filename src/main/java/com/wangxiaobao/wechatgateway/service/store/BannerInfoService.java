package com.wangxiaobao.wechatgateway.service.store;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.store.BannerInfo;
import com.wangxiaobao.wechatgateway.repository.store.BannerInfoRepository;

@Service
public class BannerInfoService {
	@Autowired
	private BannerInfoRepository bannerInfoRepository;
	
	public BannerInfo save(BannerInfo bannerInfo){
		return bannerInfoRepository.save(bannerInfo);
	}
	
	public List<BannerInfo> findOrgBannerInfoList(String orgAccount,int isValidate){
		return bannerInfoRepository.findByOrgAccountAndIsValidateAndMerchantAccountIsNull(orgAccount, isValidate);
	}
	public List<BannerInfo> findOrgBannerInfoTypeList(String orgAccount,int isValidate,int type){
		return bannerInfoRepository.findByOrgAccountAndIsValidateAndTypeAndMerchantAccountIsNull(orgAccount, isValidate,type);
	}
	
	public List<BannerInfo> findMerchantBannerInfoList(String orgAccount,int isValidate,String merchantAccount){
		return bannerInfoRepository.findByOrgAccountAndIsValidateAndMerchantAccount(orgAccount, isValidate, merchantAccount);
	}
	public List<BannerInfo> findMerchantBannerInfoTypeList(String orgAccount,int isValidate,String merchantAccount,int type){
		return bannerInfoRepository.findByOrgAccountAndIsValidateAndMerchantAccountAndType(orgAccount, isValidate, merchantAccount,type);
	}
}
