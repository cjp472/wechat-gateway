package com.wangxiaobao.wechatgateway.service.qrcodeurlverify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.qrcodeurlverify.QrcodeUrlVerify;
import com.wangxiaobao.wechatgateway.repository.qrcodeurlverify.QrcodeUrlVerifyReposity;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
@Service
public class QrcodeUrlVerifyService extends BaseService {
	@Autowired
	private QrcodeUrlVerifyReposity qrcodeUrlVerifyReposity;
	
	public QrcodeUrlVerify save(QrcodeUrlVerify qrcodeUrlVerify){
		return qrcodeUrlVerifyReposity.save(qrcodeUrlVerify);
	}
	/**
	  * @methodName: findByFileName
	  * @Description: TODO根据文件名查询
	  * @param fileName
	  * @return QrcodeUrlVerify
	  * @createUser: liping_max
	  * @createDate: 2018年1月25日 下午2:38:58
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月25日 下午2:38:58
	  * @throws
	 */
	public QrcodeUrlVerify findByFileName(String fileName){
		return qrcodeUrlVerifyReposity.findByFileName(fileName);
	}
	
	public QrcodeUrlVerify findByWxAppId(String wxAppId){
		return qrcodeUrlVerifyReposity.findByWxAppid(wxAppId);
	}
}
