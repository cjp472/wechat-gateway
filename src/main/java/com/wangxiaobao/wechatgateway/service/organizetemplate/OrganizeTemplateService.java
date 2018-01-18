package com.wangxiaobao.wechatgateway.service.organizetemplate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrganizeTemplateRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
@Service
@Transactional
public class OrganizeTemplateService extends BaseService {
	@Autowired
	private OrganizeTemplateRepository organizeTemplateRepository;
	
	public OrganizeTemplate save(OrganizeTemplate organizeTemplate){
		return organizeTemplateRepository.save(organizeTemplate);
	}
	
	public List<OrganizeTemplate> selectOrganizeTemplateByOrganizeId(String organizeId,String isNew){
		return organizeTemplateRepository.findByOrganizeIdOrIsNew(organizeId, isNew);
	}
}
