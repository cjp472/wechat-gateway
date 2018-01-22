package com.wangxiaobao.wechatgateway.repository.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdInfo;
import com.wangxiaobao.wechatgateway.enums.ad.AdTypeEnum;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by halleyzhang on 2018/1/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Transactional
public class AdRepositoryTest {

  @Autowired
  private AdRepository adRepository;

  @Test
  public void save(){

    /*AdInfo adInfo = new AdInfo();
    adInfo.setAdId("123");
    adInfo.setStoreId("1516242512987535603");
    adInfo.setMerchantAccount("test");
    adInfo.setAdType(0);
    adInfo.setAdName("我就菜品");
    adInfo.setAdType(AdTypeEnum.PICTURE.getCode());
    adRepository.save(adInfo);*/
  }

}