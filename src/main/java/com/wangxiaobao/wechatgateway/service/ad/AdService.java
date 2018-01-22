package com.wangxiaobao.wechatgateway.service.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdInfo;
import com.wangxiaobao.wechatgateway.repository.ad.AdRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@Service
@Slf4j
public class AdService {

  @Autowired
  private AdRepository adRepository;

  public AdInfo findOne(String adId){
    return adRepository.findOne(adId);
  }

  public AdInfo save(AdInfo adInfo){
    return adRepository.save(adInfo);
  }

  public List<AdInfo> findByStoreId(String storeId){
    return adRepository.findByStoreId(storeId);
  }

  public void delete(String adId) {
    adRepository.delete(adId);
  }

  public List<AdInfo> findByMerchantAccount(String merchantAccount) {
    return adRepository.findByMerchantAccount(merchantAccount);
  }
}
