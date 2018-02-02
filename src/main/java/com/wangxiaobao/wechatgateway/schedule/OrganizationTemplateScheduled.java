package com.wangxiaobao.wechatgateway.schedule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class OrganizationTemplateScheduled {
	@Autowired
	RedisService redisService;
	@Autowired
	OrganizeTemplateService organizeTemplateService;
	@Autowired
	OpenPlatformXiaochengxuService openPlatformXiaochengxuService;

	/**
	 * 目前只有拼图游戏走这个逻辑,口令游戏不走这里
	 */
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void gameFinish() {
		log.info("品牌小程序模板审核结果定时任务启动");
		OrganizeTemplate organizeTemplateSearch = new OrganizeTemplate();
		organizeTemplateSearch.setIsNew("1");
		organizeTemplateSearch.setStatus(OrganizeTemplateStatusEnum.AUDITING.getStatus());
		List<OrganizeTemplate> organizeTemplates =  organizeTemplateService.findOrganizeTemplateListBy(organizeTemplateSearch);
		if(!ObjectUtils.isEmpty(organizeTemplates)){
			for (OrganizeTemplate organizeTemplate : organizeTemplates) {
				try{
					if(!StringUtils.hasText(organizeTemplate.getAuditid())){
						log.info("模板小程序定时任务】品牌小程序模板{}没有auditId，不进行审核状态查询",JSONObject.toJSONString(organizeTemplate));
						continue;
					}
					JSONObject auditJSON = openPlatformXiaochengxuService.getAuditstatus(organizeTemplate.getWxAppId(), organizeTemplate.getAuditid());
					String errcode = auditJSON.getString("errcode");
					//审核成功，修改状态
					if("0".equals(errcode)){
						log.info("模板小程序定时任务】品牌小程序模板{}审核成功",JSONObject.toJSONString(organizeTemplate));
						organizeTemplate.setStatus(OrganizeTemplateStatusEnum.SUCCESS.getStatus());
						organizeTemplateService.save(organizeTemplate);
						openPlatformXiaochengxuService.release(organizeTemplate.getWxAppId());
						organizeTemplateService.updateOrganizeTemplateIsOnline(organizeTemplate.getWxAppId(), "1");
					}else if("0".equals(errcode)){
						log.info("模板小程序定时任务】品牌小程序模板{}审核失败",JSONObject.toJSONString(organizeTemplate));
						organizeTemplate.setStatus(OrganizeTemplateStatusEnum.FAIL.getStatus());
						organizeTemplate.setReason(auditJSON.getString("reason"));
						organizeTemplateService.save(organizeTemplate);
					}else{
						log.info("模板小程序定时任务】品牌小程序模板{}查询微信异常{}",JSONObject.toJSONString(organizeTemplate),errcode);
					}
				}catch(Exception e){
					log.info("模板小程序定时任务】品牌小程序模板{}运行异常，错误原因：{}",JSONObject.toJSONString(organizeTemplate),e);
				}
			}
		}else{
			log.info("【模板小程序定时任务】品牌小程序模板不存在审核中的状态");
		}
	}
}
