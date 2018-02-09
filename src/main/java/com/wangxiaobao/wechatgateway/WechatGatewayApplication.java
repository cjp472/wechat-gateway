package com.wangxiaobao.wechatgateway;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.source3g.springboot.frame.annotations.WxbRestTemplate;
import com.wangxiaobao.wechatgateway.utils.LoginUserInfoMethodArgumentResolver;
import com.wangxiaobao.wechatgateway.utils.UserInfoMethodArgumentResolver;
@SpringBootApplication
@WxbRestTemplate
public class WechatGatewayApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		SpringApplication.run(WechatGatewayApplication.class, args);
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		super.addArgumentResolvers(argumentResolvers);
		argumentResolvers.add(new UserInfoMethodArgumentResolver());
		argumentResolvers.add(new LoginUserInfoMethodArgumentResolver());
	}
}
