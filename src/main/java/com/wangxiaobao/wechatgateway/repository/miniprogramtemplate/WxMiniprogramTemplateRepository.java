package com.wangxiaobao.wechatgateway.repository.miniprogramtemplate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
@Repository
public interface WxMiniprogramTemplateRepository extends JpaRepository<WxMiniprogramTemplate, String>{

	public WxMiniprogramTemplate findByTypeAndIsDefault(String type,String isDefault);
	
	public WxMiniprogramTemplate findByTemplateId(String draftId);
}
