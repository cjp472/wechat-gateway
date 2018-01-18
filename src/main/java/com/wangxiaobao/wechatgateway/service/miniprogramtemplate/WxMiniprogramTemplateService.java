package com.wangxiaobao.wechatgateway.service.miniprogramtemplate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.repository.miniprogramtemplate.WxMiniprogramTemplateRepository;

@Service
@Transactional
public class WxMiniprogramTemplateService {
	@Autowired
	private WxMiniprogramTemplateRepository wxMiniprogramTemplateRepository;
	
	/**
	  * @methodName: findWxMiniprogramTemplateDefaultByType
	  * @Description: 根据类型查询默认模板
	  * @param type
	  * @return WxMiniprogramTemplate
	  * @createUser: liping_max
	  * @createDate: 2018年1月17日 上午10:52:14
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月17日 上午10:52:14
	  * @throws
	 */
	public WxMiniprogramTemplate findWxMiniprogramTemplateDefaultByType(String type){
		return wxMiniprogramTemplateRepository.findByTypeAndIsDefault(type, "1");
	}
	
	public WxMiniprogramTemplate findByDraftId(String draftId){
		return wxMiniprogramTemplateRepository.findByDraftId(draftId);
	}
}
