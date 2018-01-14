package com.wangxiaobao.wechatgateway.service.store;

import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.repository.store.StoreInfoRepository;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Service
@Slf4j
public class StoreInfoService {

  @Autowired
  private StoreInfoRepository storeInfoRepository;

  public StoreInfo save(StoreInfo storeInfo){
    return storeInfoRepository.save(storeInfo);
  }

  public StoreInfo findOne(String storeId){
    return storeInfoRepository.findOne(storeId);
  }

  public StoreInfo findByMerchantAccount(String merchantAccount){
    return storeInfoRepository.findByMerchantAccount(merchantAccount);
  }

  public StoreInfo storeMenuSave(String merchantAccount,String storeMenu){
    StoreInfo storeInfo = storeInfoRepository.findByMerchantAccount(merchantAccount);
    storeInfo.setStoreMenu(storeMenu);
    return storeInfoRepository.save(storeInfo);
  }

  public StoreInfo storePhotoSave(String merchantAccount,String storePhoto){
    StoreInfo storeInfo = storeInfoRepository.findByMerchantAccount(merchantAccount);
    storeInfo.setStorePhoto(storePhoto);
    return storeInfoRepository.save(storeInfo);
  }
}
