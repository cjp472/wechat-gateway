package com.wangxiaobao.wechatgateway.service.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import com.wangxiaobao.wechatgateway.repository.ad.AdDetailRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@Service
@Slf4j
public class AdDetailService {

  @Autowired
  private AdDetailRepository adDetailRepository;

  public List<AdDetail> findByAdId(String adId){
    return adDetailRepository.findByAdId(adId);
  }

  public AdDetail findById(String detailId){
    return adDetailRepository.findOne(detailId);
  }

  public AdDetail save(AdDetail adDetail){
    return adDetailRepository.save(adDetail);
  }

  public void delete(String detailId){
    adDetailRepository.delete(detailId);
  }

}
