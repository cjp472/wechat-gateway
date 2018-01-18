package com.wangxiaobao.wechatgateway.repository.store;

import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by halleyzhang on 2018/1/16.
 */
public interface BrandInfoRepository extends JpaRepository<BrandInfo,String> {

  BrandInfo findByOrgId(String orgId);

  BrandInfo findByOrgAccount(String orgAccount);
}
