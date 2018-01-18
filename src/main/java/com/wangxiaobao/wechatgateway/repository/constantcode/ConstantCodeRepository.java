package com.wangxiaobao.wechatgateway.repository.constantcode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
@Repository
public interface ConstantCodeRepository extends JpaRepository<ConstantCode, String> {

}
