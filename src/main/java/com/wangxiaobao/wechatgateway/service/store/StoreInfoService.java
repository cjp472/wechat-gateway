package com.wangxiaobao.wechatgateway.service.store;

import com.wangxiaobao.wechatgateway.entity.geo.GeoCode;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.repository.store.StoreInfoRepository;
import com.wangxiaobao.wechatgateway.utils.AmapUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Service
@Slf4j
public class StoreInfoService {

  @Autowired
  private StoreInfoRepository storeInfoRepository;

  @Autowired
  private AmapUtil amapUtil;

  public StoreInfo save(StoreInfo storeInfo){
    return storeInfoRepository.save(storeInfo);
  }

  public StoreInfo findOne(String storeId){
    return storeInfoRepository.findOne(storeId);
  }

  public StoreInfo findByMerchantAccount(String merchantAccount){
    return storeInfoRepository.findByMerchantAccount(merchantAccount);
  }

  public StoreInfo findByMerchantId(String merchantId){
    return storeInfoRepository.findByMerchantId(merchantId);
  }

  public StoreInfo storeMenuSave(String storeId,String storeMenu){
    StoreInfo storeInfo = storeInfoRepository.findOne(storeId);
    storeInfo.setStoreMenu(storeMenu);
    return storeInfoRepository.save(storeInfo);
  }

  public StoreInfo storePhotoSave(String storeId,String storePhoto){
    StoreInfo storeInfo = storeInfoRepository.findOne(storeId);
    storeInfo.setStorePhoto(storePhoto);
    return storeInfoRepository.save(storeInfo);
  }

  public List<StoreInfo> findByBrandAccount(String brandAccount) {
    return storeInfoRepository.findByBrandAccount(brandAccount);
  }

  public void delete(String storeId){
    storeInfoRepository.delete(storeId);
  }
}
