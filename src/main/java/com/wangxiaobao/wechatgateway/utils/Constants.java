package com.wangxiaobao.wechatgateway.utils;

/**
 * 
 * @ClassName: Constants
 * @Description: 常量类
 * @author Comsys-zt
 * @date 2016年1月13日 上午10:33:48
 *
 */
public class Constants {

	/**
	 * 用于格式化日期和时间
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";

	public static final String DATE_TIME_HY_FORMAT = "yyyy年MM月dd日 HH:mm";

	/**
	 * 用于格式化日期和时间
	 */
	public static final String DATE_TIME_SEC_FORMAT = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 用于格式化日期
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 用于格式化日期
	 */
	public static final String DATE_FORMAT_DOT = "yyyy.MM.dd";

	public static final String DATE_FORMAT_DOT_MONTH = "MM.dd";

	/**
	 * 用于格式化日期
	 */
	public static final String DATETIME_FORMAT = "HH:mm";
	/**
	 * 格式化中文(小时分钟)
	 */
	public static final String DATETIME_FORMAT_ZH_HM = "HH小时mm分钟";
	/**
	 * 格式化中文(分钟)
	 */
	public static final String DATETIME_FORMAT_ZH_MM = "mm分钟";

	/**
	 * 用于格式化日期
	 */
	public static final String DATETIMESENCOD_FORMAT = "HH:mm:ss";

	/**
	 * 用于格式化日期-年月
	 */
	public static final String DATE_PERSONAL_FORMAT = "yyyy-MM";

	/**
	 * 用于格式化日期-年
	 */
	public static final String DATE_YEAR_FORMAT = "yyyy";

	/**
	 * 用于格式化日期-年
	 */
	public static final String DATE_MONTH_FORMAT = "MM";
	/**
	 * 用于格式化日期-年月日时分秒
	 */
	public static final String DATE_FORMAT_FULL = "yyyyMMddHHmmss";

	/**
	 * 用于格式化日期-年月日
	 */
	public static final String DATE_FORMAT_YMD = "yyyyMMdd";

	/**
	 * 如果成功返回该值
	 */
	public static final String RESPONSE_SUCCESS = "success";
	
	/************************** 支付方式 **************************************************/
	public static final String QC_PAY = "1006001";
	public static final String ONLINE_PAY = "1006002";
	/**********************************************************************************/
	/************************** 支付类别 **************************************************/
	public static final String WX_PAY = "1007001";
	public static final String ALI_PAY = "1007002";
	/**********************************************************************************/
	public static final String ALI_PAY_PC_SERVICE = "create.direct.pay.by.user";
	public static final String ALI_PAY_WAP_SERVICE = "alipay.wap.create.direct.pay.by.user";

	/**
	 * 如果失败时返回改值
	 */
	public static final String RESPONSE_FAIL = "fail";
	/*********************** 签名KEY ****************************************************/
	public final static String KEY = "";
	
	/**
	 * 商户授权第三方开放平台之后的信息缓存
	 */
	public static final String MERCHANT_WX_OPENPLATFORM_KEY = "wxOpenplatformKey:";
	/**
	 * 第三方平台的调用凭证
	 */
	public static final String OPENPLATFORM_COMPONENT_ACCESS_TOKEN = "componentAccessToken";
	
	/**
	 * 商户授权账号类型1：公众号；2：小程序
	 */
	public static final String AUTHORIZER_TYPE_GONGZHONGHAO = "1";
	public static final String AUTHORIZER_TYPE_XIAOCHENGXU = "2";
	//accessToken过期时间
	public static final long ACCESS_TOKEN_REDIS_TIMEOUT=7000;
	
}
