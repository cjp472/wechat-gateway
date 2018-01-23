package com.wangxiaobao.wechatgateway.service.openplatformxiaochengxu;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OpenPlatformXiaochengxuTest {
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Test
	public void findCanBindXiaochengxu(){
//		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findCanBindXiaochengxu();
//		Assert.assertNotNull(openPlatformXiaochengxu);
	}
}
