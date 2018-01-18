package com.wangxiaobao.wechatgateway.service.miniprogramtemplate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WxMiniprogramTemplateTest {
	@Autowired
	private WxMiniprogramTemplateService wxMiniprogramTemplateService;
	@Test
	public void findWxMiniprogramTemplateDefaultByType(){
//		WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateService.findWxMiniprogramTemplateDefaultByType("1");
//		Assert.assertNull(wxMiniprogramTemplate);
	}
}
