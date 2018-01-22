package com.wangxiaobao.wechatgateway.repository.store;

import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by halleyzhang on 2018/1/13.
 */
public interface StoreInfoRepository extends JpaRepository<StoreInfo,String> {

  StoreInfo findByMerchantAccount(String merchantAccount);

  StoreInfo findByMerchantId(String merchantId);

  List<StoreInfo> findByMerchantIdIn(List<String> merchantIds);

  List<StoreInfo> findByBrandAccount(String brandAccount);
}
