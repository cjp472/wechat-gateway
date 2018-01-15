package com.wangxiaobao.wechatgateway.service.openplatform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.repository.openplatform.OpenPlatformXiaochengxuRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OpenPlatformXiaochengxuService {
	@Autowired
	private OpenPlatformXiaochengxuRepository openPlatformXiaochengxuRepository;
	/**
	 * 查询可以绑定的小程序
	 * @return
	 */
	public OpenPlatformXiaochengxu findCanBindXiaochengxu(){
		List<OpenPlatformXiaochengxu> openPlatformXiaochengxus = openPlatformXiaochengxuRepository.findByTopLimitAndIsValidateAndType("0","1","1");
		if(null==openPlatformXiaochengxus||openPlatformXiaochengxus.size()<=0){
			log.info("当前没有可以被商户公众号关联的小程序");
			return null;
		}
		return openPlatformXiaochengxus.get(0);
	}
	
	/**
	 * 根据code查询平台小程序
	 * @param code
	 * @return
	 */
	public OpenPlatformXiaochengxu findXiaochengxuByCode(String code){
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuRepository.findByCode(code);
		return openPlatformXiaochengxu;
	}
}
