package com.wangxiaobao.wechatgateway.repository.qrcodeurlverify;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.qrcodeurlverify.QrcodeUrlVerify;
@Repository
public interface QrcodeUrlVerifyReposity extends JpaRepository<QrcodeUrlVerify, String> {

	public QrcodeUrlVerify findByFileName(String fileName);
}
