package com.wangxiaobao.wechatgateway.utils;


import java.net.URLEncoder;

import com.wangxiaobao.wechatgateway.properties.SysConfig;

public class GetWeiXinCode {
	public static String GetCodeRequest = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";

	public static String getCodeRequest(String url) {

		String result = null;

		GetCodeRequest = GetCodeRequest.replace("APPID", urlEnodeUTF8(SysConfig.getAppId()));

		GetCodeRequest = GetCodeRequest.replace("REDIRECT_URI", urlEnodeUTF8(url));

		GetCodeRequest = GetCodeRequest.replace("SCOPE", SysConfig.getPropertiesParams("scope"));

		result = GetCodeRequest;

		return result;

	}

	public static String urlEnodeUTF8(String str) {

		String result = str;

		try {

			result = URLEncoder.encode(str, "UTF-8");

		} catch (Exception e) {

			e.printStackTrace();

		}

		return result;

	}
}
