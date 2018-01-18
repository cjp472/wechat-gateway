package com.wangxiaobao.wechatgateway.service.organizetemplate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.repository.openplatform.WXopenPlatformMerchantInfoMapper;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrganizeTemplateRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
@Service
@Transactional
public class OrganizeTemplateService extends BaseService {
	@Autowired
	private OrganizeTemplateRepository organizeTemplateRepository;
	@Autowired
	private WXopenPlatformMerchantInfoMapper wXopenPlatformMerchantInfoMapper;
	
	
	public OrganizeTemplate save(OrganizeTemplate organizeTemplate){
		return organizeTemplateRepository.save(organizeTemplate);
	}
	
	public List<OrganizeTemplate> selectOrganizeTemplateByorganizationAccount(String organizationAccount,String isNew){
		return organizeTemplateRepository.findByorganizationAccountOrIsNew(organizationAccount, isNew);
	}
	
	public OrganizeTemplate findOrganizeTemplateBy(OrganizeTemplate organizeTemplate){
		Example<OrganizeTemplate> example = Example.of(organizeTemplate);
		return organizeTemplateRepository.findOne(example);
	}
	
	public void updateOrganizeTemplateStatus(String wxAppid,String status){
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppid);
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setOrganizationAccount(wxInfo.getOrganizationAccount());
		organizeTemplate.setIsNew("1");
		OrganizeTemplate organizeTemplate2 = findOrganizeTemplateBy(organizeTemplate);
		organizeTemplate2.setStatus(status);
		save(organizeTemplate2);
	}
}
