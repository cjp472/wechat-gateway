package com.wangxiaobao.wechatgateway.repository.store;

import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StoreInfoRepositoryTest {

  @Autowired
  private StoreInfoRepository storeInfoRepository;

  @Test
  public void save() {
    /*StoreInfo storeInfo = new StoreInfo();
    storeInfo.setStoreId("12345");
    storeInfo.setStoreName("销魂掌华阳店");
    storeInfo.setMerchantAccount("XHZHYD");
    storeInfo.setMerchantId("123456");
    storeInfo.setStoreProvince("四川省");
    storeInfo.setStoreCity("成都市");
    storeInfo.setStoreDistrict("华阳");
    storeInfo.setStoreAddress("华府大道1号");
    storeInfo.setStoreDescription("好吃");
    storeInfo.setStoreOfficehours("10-22");
    storeInfo.setStorePhone("88888888");
    storeInfo.setHaveParking(1);
    StoreInfo result = storeInfoRepository.save(storeInfo);
    Assert.assertNotNull(result);*/
  }

  @Test
  public void findByMerchantIdIn() {
    List<String> merchantId = new ArrayList();
    merchantId.add("123456");
    merchantId.add("1234561");
    List<StoreInfo> result = storeInfoRepository.findByMerchantIdIn(merchantId);
    log.info(result.toString());
  }
}