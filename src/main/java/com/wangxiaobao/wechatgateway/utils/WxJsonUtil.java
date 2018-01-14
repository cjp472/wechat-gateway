package com.wangxiaobao.wechatgateway.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

public class WxJsonUtil {

	private WxJsonUtil(){}

	public static <T> T parseObject(String json,Class<T> clazz){
		return JSON.parseObject(json, clazz);
	}
	
	public static <T> List<T> parseList(String json,Class<T> clazz){
		return JSON.parseArray(json, clazz);
	}
	
	public static String toJSONString(Object object){
		return JSON.toJSONString(object);
	}
}
