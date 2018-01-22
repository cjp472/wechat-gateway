package com.wangxiaobao.wechatgateway.service.store;

import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.repository.store.BrandInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@Service
@Slf4j
public class BrandInfoService {

  @Autowired
  private BrandInfoRepository brandInfoRepository;

  public BrandInfo save(BrandInfo brandInfo){
    return brandInfoRepository.save(brandInfo);
  }

  public BrandInfo findOne(String brandId){
    return brandInfoRepository.findOne(brandId);
  }

  public BrandInfo findByOrgId(String orgId){
    return brandInfoRepository.findByOrgId(orgId);
  }

  public BrandInfo findByBrandAccount(String orgAccount){
    return brandInfoRepository.findByOrgAccount(orgAccount);
  }

}
