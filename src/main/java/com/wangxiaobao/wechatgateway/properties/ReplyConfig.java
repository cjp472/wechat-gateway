package com.wangxiaobao.wechatgateway.properties;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Properties;

/**
 * 
 * @ClassName: SystemConfig.java
import com.yyh.common.util.PropertiesHelper;
 * @Description: 配置文件
 *
 * @author zhangll
 * @version V1.0
 * @Date 2015年4月30日 上午9:52:59
 */
public class ReplyConfig {
	private static Properties ph;

	static {
		reLoad();
	}

	public static void reLoad() {
		String resolvedLocation = SystemPropertyUtils.resolvePlaceholders("classpath:conf/properties/reply.properties");
		try {
			URL url = ResourceUtils.getURL(resolvedLocation);
			Properties prop = new Properties();
			prop.load(new InputStreamReader(new FileInputStream(url.getPath()), "UTF-8"));
			ph = prop;
			// ph = new PropertiesHelper(new FileInputStream(url.getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取项目的相对路径下文件的绝对路径
	 *
	 * @param parentDir
	 *            目标文件的父目录 , 例如说 , 工程的目录下 , 有 lib 与 bin 和 conf 目录 , 那么程序运行于 lib
	 *            or bin, 那么需要的配置文件却是 conf 里面 , 则需要找到该配置文件的绝对路径
	 * @param fileName
	 *            文件名
	 * @return 一个绝对路径
	 */
	public static String getPath(String parentDir, String fileName) {
		String path = null;
		// 获得当前工程路径
		String userdir = System.getProperty("user.dir");
		String userdirName = new File(userdir).getName();
		if (userdirName.equalsIgnoreCase("lib") || userdirName.equalsIgnoreCase("bin")) {
			File newf = new File(userdir);
			File newp = new File(newf.getParent());
			if (fileName.trim().equals("")) {
				path = newp.getPath() + File.separator + parentDir;
			} else {
				path = newp.getPath() + File.separator + parentDir + File.separator + fileName;
			}
		} else {
			if (fileName.trim().equals("")) {
				path = userdir + File.separator + parentDir;
			} else {
				path = userdir + File.separator + parentDir + File.separator + fileName;
			}
		}
		return path;

	}

	public static String getPropertiesParams(String param) {
		if (!StringUtils.isEmpty(ph.getProperty(param))) {
			return ph.getProperty(param);
		}
		return "";
	}

	public static void setPh(Properties ph) {
		ReplyConfig.ph = ph;
	}

	public static void main(String[] args) {
		System.out.println(ReplyConfig.getPropertiesParams("/::)"));
	}
}
