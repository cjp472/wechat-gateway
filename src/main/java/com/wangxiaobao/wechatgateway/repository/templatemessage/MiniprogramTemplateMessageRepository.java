package com.wangxiaobao.wechatgateway.repository.templatemessage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.wangxiaobao.wechatgateway.entity.templatemessage.MiniprogramTemplateMessage;
@Repository
public interface MiniprogramTemplateMessageRepository extends JpaRepository<MiniprogramTemplateMessage, String>{

}
