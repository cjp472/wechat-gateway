package com.wangxiaobao.wechatgateway.repository.templatemessageconfig;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.templatemessageconfig.MiniprogramTemplateMessageConfig;

@Repository
public interface MiniprogramTemplateMessageConfigRepository extends JpaRepository<MiniprogramTemplateMessageConfig, String>{

}
