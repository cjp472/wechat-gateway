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

  public void storeLocationSave(StoreInfo storeInfo){
    String address = storeInfo.getStoreProvince()+storeInfo.getStoreCity()+storeInfo.getStoreDistrict()+storeInfo.getStoreAddress();
    if(StringUtils.isEmpty(address)){
      return;
    }
    List<GeoCode> codes = amapUtil.getGeoCode(address);
    if(null != codes){
      storeInfo.setStoreLocation(codes.get(0).getLocation());
      storeInfoRepository.save(storeInfo);
    }
  }

  public List<StoreInfo> findByMerchantIds(List<String> merchantIds){
    List<StoreInfo> result = storeInfoRepository.findByMerchantIdIn(merchantIds);
    return result;
  }

  public List<StoreInfo> findAll(){
    return storeInfoRepository.findAll();
  }
}
