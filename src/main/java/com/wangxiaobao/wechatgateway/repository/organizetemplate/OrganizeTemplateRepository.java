package com.wangxiaobao.wechatgateway.repository.organizetemplate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
@Repository
public interface OrganizeTemplateRepository extends JpaRepository<OrganizeTemplate, String>{

	public List<OrganizeTemplate> findByOrganizeIdOrIsNew(String organizeId,String isNew);
}
