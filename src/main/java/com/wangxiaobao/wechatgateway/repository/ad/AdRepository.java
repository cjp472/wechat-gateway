package com.wangxiaobao.wechatgateway.repository.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by halleyzhang on 2018/1/18.
 */
public interface AdRepository extends JpaRepository<AdInfo,String>{

}
