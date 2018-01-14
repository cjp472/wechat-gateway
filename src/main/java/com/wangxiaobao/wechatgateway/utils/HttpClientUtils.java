package com.wangxiaobao.wechatgateway.utils;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.MessageFormat;


public class HttpClientUtils {

	public final static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	private static HttpClient httpClient = null;

    public static HttpClient getHttpClient() {  
    	if (httpClient == null) {
    		httpClient = HttpClientBuilder.create().build();
    	}
    	return httpClient;

    }

	public static String executeByGET(String url, Object[] params) {
		HttpClient httpclient = getHttpClient();
		String messages = MessageFormat.format(url, params);
		HttpGet get = new HttpGet(messages);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseJson = null;
		try {
			responseJson = httpclient.execute(get, responseHandler);
			logger.info("HttpClient GET请求结果：" + responseJson);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.info("HttpClient GET请求异常：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("HttpClient GET请求异常：" + e.getMessage());
		}
		return responseJson;
	}

	/**
	 * @param url
	 * @return
	 */
	public static String executeByGET(String url) {
		HttpClient httpclient = getHttpClient();
		HttpGet get = new HttpGet(url);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseJson = null;
		try {
			responseJson = httpclient.execute(get, responseHandler);
			logger.info("HttpClient GET请求结果：" + responseJson);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			logger.info("HttpClient GET请求异常：" + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("HttpClient GET请求异常：" + e.getMessage());
		}
		return responseJson;
	}
	
	public static String executeByJSONPOST(String url, String params,int second) {
		logger.info("post发出数据 :URL---> "+url+";params---->" + params);
		HttpClient httpclient = getHttpClient();
		HttpPost post = new HttpPost(url);
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(second).setConnectTimeout(second).build();
		post.setConfig(requestConfig);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseJson = null;
		StringEntity postEntity = new StringEntity(params, "UTF-8");
		postEntity.setContentType("application/json");
		try {
			if (params != null) {
				post.setEntity(postEntity);
			}
			responseJson = httpclient.execute(post, responseHandler);
			logger.info("HttpClient POST请求结果：" + responseJson);
		} catch (Exception e) {
			logger.info("HttpClient POST请求异常：" + e.getMessage());
		}
		return responseJson;
	}
}