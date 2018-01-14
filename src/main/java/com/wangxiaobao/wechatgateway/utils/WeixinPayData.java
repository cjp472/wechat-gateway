package com.wangxiaobao.wechatgateway.utils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class WeixinPayData {

	private String appid;
	private String mch_id;
	private String trade_type;
	private String out_trade_no;
	private String total_fee;
	private String body;
	private String spbill_create_ip;
	private String notify_url;
	private String nonce_str;
	private String sign;
	private String openid;

	public WeixinPayData(String appid, String mch_id, String trade_type, String out_trade_no, String total_fee, String body, String spbill_create_ip, String notify_url, String nonce_str, String sign,
			String openid) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.trade_type = trade_type;
		this.out_trade_no = out_trade_no;
		this.total_fee = total_fee;
		this.body = body;
		this.spbill_create_ip = spbill_create_ip;
		this.notify_url = notify_url;
		this.nonce_str = nonce_str;
		this.sign = sign;
		this.openid = openid;
	}

	public WeixinPayData() {

	}

	public WeixinPayData(String appid, String mch_id, String out_trade_no, String nonce_str, String sign) {
		this.appid = appid;
		this.mch_id = mch_id;
		this.out_trade_no = out_trade_no;
		this.nonce_str = nonce_str;
		this.sign = sign;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getMch_id() {
		return mch_id;
	}

	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}

	public String getTrade_type() {
		return trade_type;
	}

	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}

	public String getOut_trade_no() {
		return out_trade_no;
	}

	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}

	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}

	public String getNotify_url() {
		return notify_url;
	}

	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}

	public String getNonce_str() {
		return nonce_str;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

}
