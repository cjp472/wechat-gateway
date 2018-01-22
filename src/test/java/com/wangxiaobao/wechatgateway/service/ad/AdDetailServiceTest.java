package com.wangxiaobao.wechatgateway.service.ad;

import static org.junit.Assert.*;

import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import com.wangxiaobao.wechatgateway.repository.ad.AdDetailRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AdDetailServiceTest {

  @Autowired
  private AdDetailRepository adDetailRepository;

  @Test
  public void save() throws Exception {
    AdDetail adDetail = new AdDetail();
    adDetail.setAdId("1234");
    adDetail.setDetailId("1234");
    adDetail.setDetailUrl("http://www.sohu.com");
    adDetailRepository.save(adDetail);

  }

}