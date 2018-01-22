package com.wangxiaobao.wechatgateway.repository.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by halleyzhang on 2018/1/19.
 */
public interface AdDetailRepository extends JpaRepository<AdDetail,String> {

  List<AdDetail> findByAdId(String adId);

}
