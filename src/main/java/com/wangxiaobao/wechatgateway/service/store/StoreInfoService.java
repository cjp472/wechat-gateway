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

  public StoreInfo create(StoreInfo storeInfo){
    String storeId = KeyUtil.genUniqueKey();
    storeInfo.setStoreId(storeId);
    return storeInfoRepository.save(storeInfo);
  }
}
