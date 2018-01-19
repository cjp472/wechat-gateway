package com.wangxiaobao.wechatgateway.service.store;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class StoreInfoServiceTest {

  @Autowired
  private StoreInfoService storeInfoService;

  @Test
  public void create(){
    StoreInfo storeInfo = new StoreInfo();
    storeInfo.setStoreId("11111");
    storeInfo.setStoreName("销魂掌华阳店1");
    storeInfo.setMerchantAccount("XHZHYD999");
    storeInfo.setMerchantAccount("1234567");
    storeInfo.setStoreProvince("四川省");
    storeInfo.setStoreCity("成都市");
    storeInfo.setStoreDistrict("华阳");
    storeInfo.setStoreAddress("华府大道1号");
    storeInfo.setStoreDescription("好吃");
    storeInfo.setStoreOfficehours("10-22");
    storeInfo.setStorePhone("88888888");
    storeInfo.setHaveParking(1);
    storeInfo.setMerchantId("1323");
    StoreInfo result = storeInfoService.save(storeInfo);
    Assert.assertNotNull(result);
  }
}