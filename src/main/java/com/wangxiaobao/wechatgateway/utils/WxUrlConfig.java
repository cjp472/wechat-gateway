package com.wangxiaobao.wechatgateway.utils;

public class WxUrlConfig {

	// 未支付订单(预订单) --
	public final static String WEIXIN_PAY_UNIFIEDORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	// 关闭订单接口
	public final static String WEIXIN_PAY_CLOSEORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
	// 微信退款接口
	public final static String WEIXIN_PAY_REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";
	// 查询微信订单
	public final static String WEIXIN_PAY_ORDERQUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";
	// 上传图片
	public final static String WEIXIN_WIFI_UPLOADIMG = "https://api.weixin.qq.com/cgi-bin/media/uploadimg";
	// 创建门店
	public final static String WEIXIN_WIFI_POIID = "http://api.weixin.qq.com/cgi-bin/poi/addpoi";
}
