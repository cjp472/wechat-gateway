package com.wangxiaobao.wechatgateway.service.organizetemplate;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.VO.organizetemplate.OrganizeTemplateVO;
import com.wangxiaobao.wechatgateway.entity.PageModel;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.form.organizetemplate.OrganizeTemplateListRequest;
import com.wangxiaobao.wechatgateway.repository.openplatform.WXopenPlatformMerchantInfoMapper;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrganizeTemplateRepository;
import com.wangxiaobao.wechatgateway.repository.organizetemplate.OrgranizeTemplateMapper;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
@Service
@Transactional
public class OrganizeTemplateService extends BaseService {
	@Autowired
	private OrganizeTemplateRepository organizeTemplateRepository;
	@Autowired
	private WXopenPlatformMerchantInfoMapper wXopenPlatformMerchantInfoMapper;
	@Autowired
	private OrgranizeTemplateMapper orgranizeTemplateMapper;
	
	
	public OrganizeTemplate save(OrganizeTemplate organizeTemplate){
		return organizeTemplateRepository.save(organizeTemplate);
	}
	
	public OrganizeTemplate findOrganizeTemplateBy(OrganizeTemplate organizeTemplate){
		Example<OrganizeTemplate> example = Example.of(organizeTemplate);
		return organizeTemplateRepository.findOne(example);
	}
	
	public List<OrganizeTemplate> findOrganizeTemplateListBy(OrganizeTemplate organizeTemplate){
		Example<OrganizeTemplate> example = Example.of(organizeTemplate);
		return organizeTemplateRepository.findAll(example);
	}
	
	public void updateOrganizeTemplateStatus(String wxAppid,String status,String reason){
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppid);
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setOrganizationAccount(wxInfo.getOrganizationAccount());
		organizeTemplate.setIsNew("1");
		organizeTemplate.setReason(reason);
		OrganizeTemplate organizeTemplate2 = findOrganizeTemplateBy(organizeTemplate);
		organizeTemplate2.setStatus(status);
		save(organizeTemplate2);
	}
	
	public void updateOrganizeTemplateIsOnline(String wxAppid,String isOnline){
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppid);
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setOrganizationAccount(wxInfo.getOrganizationAccount());
		organizeTemplate.setIsNew("1");
		OrganizeTemplate organizeTemplate2 = findOrganizeTemplateBy(organizeTemplate);
		organizeTemplate2.setIsOnline(isOnline);
		save(organizeTemplate2);
	}
	
	public void updateOrganizeTemplateIsNew(String wxAppid,String isNew){
		WXopenPlatformMerchantInfo wxInfo = wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppid);
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setOrganizationAccount(wxInfo.getOrganizationAccount());
		organizeTemplate.setIsNew("1");
		OrganizeTemplate organizeTemplate2 = findOrganizeTemplateBy(organizeTemplate);
		if(null!=organizeTemplate2){
			organizeTemplate2.setIsNew(isNew);
			save(organizeTemplate2);
		}
	}
	
	/**
	  * @methodName: selectOrganizeTemplateList
	  * @Description: TODO 商家上传模板列表查询
	  * @param request
	  * @param pageModel
	  * @return PageModel<OrganizeTemplateVO>
	  * @createUser: liping_max
	  * @createDate: 2018年3月20日 下午2:52:39
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月20日 下午2:52:39
	  * @throws
	 */
	public PageModel<OrganizeTemplateVO> selectOrganizeTemplateList(OrganizeTemplateListRequest request,PageModel<OrganizeTemplateVO> pageModel){
		return orgranizeTemplateMapper.pageQueryForList("OrganizeTemplateMapper.selectOrganizeTemplateList", pageModel);
	}
}
