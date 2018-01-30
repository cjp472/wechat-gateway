package com.wangxiaobao.wechatgateway.utils;

import lombok.Data;

import java.io.Serializable;

/**
  * @PackageName: com.source3g.member.common.response
  * @ClassName: JsonResult
  * @Description: JSON返回对象
  * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
  * @Company:成都国盛天丰技术有限责任公司
  * @author ZhouTong
  * @date  2018/1/9 9:36
 */
@Data
public class JsonResult implements Serializable {

	public final static String APP_RETURN_SUCCESS = "0";
	public final static String APP_MSG_RETURN_SUCCESS = "成功";
	public final static String APP_RETURN_FAIL = "1";
	public final static String APP_MSG_RETURN_FAIL = "失败";
	public final static String APP_SIGN_FAIL = "2";
	public final static String APP_MSG_SIGN_FAIL = "签名错误";
	public final static String APP_RETURN_NULL = "3";
	public final static String APP_MSG_RETURN_NULL = "返回数据为空";
	public final static String APP_RETURN_ABNORMAL = "4";
	public final static String APP_MSG_RETURN_ABNORMAL = "返回成功,存在异常数据";
	public final static String APP_INPUT_PARAM_ORROR = "5";
	public final static String APP_MSG_INPUT_PARAM_ORROR = "传入参数错误";
	public final static String APP_NOT_DATA = "6";
	public final static String APP_MSG_NOT_DATA = "未查询到相关数据";
	public final static String APP_MSG_AUTH_FAIL = "授权失败";
	public final static String APP_MSG_AUTH_SUCCESS = "授权成功";
	/**
	  * @Fields serialVersionUID : 序列化
	  */
	private static final long serialVersionUID = 3548096925260083341L;

	/**
	 * 状态
	 */
	private String code;
	/**
	 * 信息
	 */
	private String message;
	/**
	 * 数据
	 */
	private Object data;

	public static JsonResult newInstanceFail() {
		return JsonResult.newInstance(APP_RETURN_FAIL, APP_MSG_RETURN_FAIL, null);
	}

	public static JsonResult newInstanceMesFail(String message) {
		return JsonResult.newInstance(APP_RETURN_FAIL, message, null);
	}

	public static JsonResult newInstanceDataFail(Object data) {
		return JsonResult.newInstance(APP_RETURN_FAIL, APP_MSG_RETURN_FAIL, data);
	}

	public static JsonResult newInstanceFail(String message, Object data) {
		return JsonResult.newInstance(APP_RETURN_FAIL, message, data);
	}
	
	public static JsonResult newInstanceAuthFail(Object data) {
		return JsonResult.newInstance(APP_RETURN_FAIL, APP_MSG_AUTH_FAIL, data);
	}
	
	public static JsonResult newInstanceAuthSuccess(Object data) {
		return JsonResult.newInstance(APP_RETURN_SUCCESS, APP_MSG_AUTH_SUCCESS, data);
	}

	public static JsonResult newInstanceSuccess() {
		return JsonResult.newInstance(APP_RETURN_SUCCESS, APP_MSG_RETURN_SUCCESS, null);
	}

	public static JsonResult newInstanceMesSuccess(String message) {
		return JsonResult.newInstance(APP_RETURN_SUCCESS, message, null);
	}

	public static JsonResult newInstanceDataSuccess(Object data) {
		return JsonResult.newInstance(APP_RETURN_SUCCESS, APP_MSG_RETURN_SUCCESS, data);
	}

	public static JsonResult newInstanceSuccess(String message, Object data) {
		return JsonResult.newInstance(APP_RETURN_SUCCESS, message, data);
	}

	public static JsonResult newInstance(String code, String message, Object data) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(code);
		jsonResult.setMessage(message);
		if (data != null) {
			jsonResult.setData(data);
		}
		return jsonResult;
	}

	public static JsonResult newInstance(String code, String message) {
		JsonResult jsonResult = new JsonResult();
		jsonResult.setCode(code);
		jsonResult.setMessage(message);
		return jsonResult;
	}


}
