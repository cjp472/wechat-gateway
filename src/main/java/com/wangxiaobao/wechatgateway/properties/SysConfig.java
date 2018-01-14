package com.wangxiaobao.wechatgateway.properties;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;

import com.wangxiaobao.wechatgateway.utils.PropertiesHelper;

/**
 * 
 * @ClassName: SystemConfig.java
 * @Description: 配置文件
 *
 * @author zhangll
 * @version V1.0
 * @Date 2015年4月30日 上午9:52:59
 */
public class SysConfig {

	private static PropertiesHelper ph;

	static {
		reLoad();
	}

	public static void reLoad() {
		// this.getClass().getResourceAsStream("/cn/zhao/properties/testPropertiesPath2.properties")
		// String path = getPath("src\\main\\resources", "wx.properties");
		String resolvedLocation = SystemPropertyUtils.resolvePlaceholders("classpath:wx.properties");
		try {
			URL url = ResourceUtils.getURL(resolvedLocation);
			ph = new PropertiesHelper(new FileInputStream(url.getPath()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		// InputStream is = SysConfig.class.getResourceAsStream();
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

	/**
	 * 
	 *
	 * @return
	 * @Title: getIsDebug
	 * @Description: 是否是开发调试模式
	 * @param: @return
	 * @return: String
	 * @throws
	 * @author zhangll
	 * @Date 2015年4月30日 上午10:15:36
	 */
	public static boolean getIsDebug() {
		if ("true".equals(ph.getValue("isDebug"))) {
			return true;
		}
		return false;
	}

	public static String getToken() {
		if (!StringUtils.isEmpty(ph.getValue("wx_token"))) {
			return ph.getValue("wx_token");
		}
		return "";
	}

	public static String getAppId() {
		if (!StringUtils.isEmpty(ph.getValue("wx_appid"))) {
			return ph.getValue("wx_appid");
		}
		return "";
	}

	public static String getAppSecret() {
		if (!StringUtils.isEmpty(ph.getValue("wx_appsecret"))) {
			return ph.getValue("wx_appsecret");
		}
		return "";
	}

	public static String getEncodingAESKey() {
		if (!StringUtils.isEmpty(ph.getValue("wx_EncodingAESKey"))) {
			return ph.getValue("wx_EncodingAESKey");
		}
		return "";
	}

	public static String getMchId() {
		if (!StringUtils.isEmpty(ph.getValue("mch_id"))) {
			return ph.getValue("mch_id");
		}
		return "";
	}

	public static String getPayKey() {
		if (!StringUtils.isEmpty(ph.getValue("pay_key"))) {
			return ph.getValue("pay_key");
		}
		return "";
	}

	public static String getInputCharset() {
		if (!StringUtils.isEmpty(ph.getValue("input_charset"))) {
			return ph.getValue("input_charset");
		}
		return "";
	}

	public static String getTradeType() {
		if (!StringUtils.isEmpty(ph.getValue("trade_type"))) {
			return ph.getValue("trade_type");
		}
		return "";
	}

	public static String getSignType() {
		if (!StringUtils.isEmpty(ph.getValue("sign_type"))) {
			return ph.getValue("sign_type");
		}
		return "";
	}

	public static String getBankType() {
		if (!StringUtils.isEmpty(ph.getValue("bank_type"))) {
			return ph.getValue("bank_type");
		}
		return "";
	}

	public static String getPropertiesParams(String param) {
		if (!StringUtils.isEmpty(ph.getValue(param))) {
			return ph.getValue(param);
		}
		return "";
	}

	public static String getAppAdress() {
		if (!StringUtils.isEmpty(ph.getValue("app_address"))) {
			return ph.getValue("app_address");
		}
		return "";
	}

	public static String getCmanagerAddress() {
		if (!StringUtils.isEmpty(ph.getValue("cmanager_address"))) {
			return ph.getValue("cmanager_address");
		}
		return "";
	}

	public static String getCmanagerProjectName() {
		if (!StringUtils.isEmpty(ph.getValue("cmanager_project_name"))) {
			return ph.getValue("cmanager_project_name");
		}
		return "";
	}

	public static String getCmanagerSendMsgApi() {
		if (!StringUtils.isEmpty(ph.getValue("cmanager_send_msg_api"))) {
			return ph.getValue("cmanager_send_msg_api");
		}
		return "";
	}

	public static String getProjectName() {
		if (!StringUtils.isEmpty(ph.getValue("project_name"))) {
			return ph.getValue("project_name");
		}
		return "";
	}

	public static PropertiesHelper getPh() {
		return ph;
	}

	public static void setPh(PropertiesHelper ph) {
		SysConfig.ph = ph;
	}
}
