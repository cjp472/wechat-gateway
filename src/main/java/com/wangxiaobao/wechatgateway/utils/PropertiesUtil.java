package com.wangxiaobao.wechatgateway.utils;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
  * @ClassName: PropertiesUtil
  * @Description: 属性文件工具类
  * @author Comsys-zt
  * @date 2016年4月13日 下午3:16:10
  *
 */
public class PropertiesUtil {


	private static Properties ph;

	static {
		reLoad();
	}

	private static void reLoad() {
		String resolvedLocation = SystemPropertyUtils.resolvePlaceholders("classpath:conf/properties/config.properties");
		try {
			URL url = ResourceUtils.getURL(resolvedLocation);
			Properties prop = new Properties();
			prop.load(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8"));
			ph = prop;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getPropertiesParams(String param) {
		if (!StringUtils.isEmpty(ph.getProperty(param))) {
			return ph.getProperty(param);
		}
		return "";
	}
	
	public static Map<String, String> convertToMap(Properties prop) {
		if (prop == null)
			return null;

		Map<String, String> result = new HashMap<String, String>();
		for (Object eachKey : prop.keySet()) {
			if (eachKey == null)
				continue;

			String key = eachKey.toString();
			String value = (String) prop.get(key);
			if (value == null)
				value = "";
			else
				value = value.trim();
			result.put(key, value);
		}
		return result;
	}
}
