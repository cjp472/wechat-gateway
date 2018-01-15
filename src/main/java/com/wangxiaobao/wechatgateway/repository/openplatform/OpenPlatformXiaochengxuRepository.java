package com.wangxiaobao.wechatgateway.repository.openplatform;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
@Repository
public interface OpenPlatformXiaochengxuRepository extends JpaRepository<OpenPlatformXiaochengxu, String>{
	/**
	 * 查询还可以绑定的小程序的列表
	 * @return
	 */
	public List<OpenPlatformXiaochengxu> findByTopLimitAndIsValidateAndType(String topLimit,String isValidate,String type);

	public OpenPlatformXiaochengxu findByCode(String code);
}
