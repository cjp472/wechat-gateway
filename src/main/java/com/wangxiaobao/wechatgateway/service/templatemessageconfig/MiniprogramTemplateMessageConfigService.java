package com.wangxiaobao.wechatgateway.service.templatemessageconfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.wangxiaobao.wechatgateway.entity.templatemessageconfig.MiniprogramTemplateMessageConfig;
import com.wangxiaobao.wechatgateway.repository.templatemessageconfig.MiniprogramTemplateMessageConfigRepository;

@Service
public class MiniprogramTemplateMessageConfigService {
	@Autowired
	private MiniprogramTemplateMessageConfigRepository miniprogramTemplateMessageConfigRepository;
	/**
	  * @methodName: findByCondition
	  * @Description: TODO查询小程序模板消息配置
	  * @param miniprogramTemplateMessageConfig
	  * @return MiniprogramTemplateMessageConfig
	  * @createUser: liping_max
	  * @createDate: 2018年2月1日 下午2:38:54
	  * @updateUser: liping_max
	  * @updateDate: 2018年2月1日 下午2:38:54
	  * @throws
	 */
	public MiniprogramTemplateMessageConfig findByCondition(MiniprogramTemplateMessageConfig miniprogramTemplateMessageConfig){
		Example<MiniprogramTemplateMessageConfig> example = Example.of(miniprogramTemplateMessageConfig);
		return miniprogramTemplateMessageConfigRepository.findOne(example);
	}
}
