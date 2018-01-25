package com.wangxiaobao.wechatgateway.service.miniprogramqrcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeAddForm;
import com.wangxiaobao.wechatgateway.form.miniprogramqrcode.MiniprogramQrCodeRequest;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
@Service
public class MiniProgramQrCodeService extends BaseService {
	@Autowired
	private WXopenPlatformMerchantInfoService wxPlatformMerchantInfoService;

	public String qrcodejumpget(MiniprogramQrCodeRequest request){
		String url = wxProperties.getWx_qrcode_qrcodejumpget_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(request.getWxAppid()).getAuthoriceAccessToken();
		
		String result = HttpClientUtils.executeByJSONPOST(url, new JSONObject().toJSONString(), 50000);
		return result;
	}
	
	@SuppressWarnings("static-access")
	public String qrcodejumpadd(MiniprogramQrCodeAddForm form,String wxAppid){
		String url = wxProperties.getWx_qrcode_qrcodejumpadd_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONArray debugUrlJSONA = new JSONArray();
		if(!StringUtils.isEmpty(form.getDebug_url())){
			String[] debugUrls = form.getDebug_url().split(",");
			for (String string : debugUrls) {
				debugUrlJSONA.add(string);
			}
		}
		String paramsStr = new JSONObject().toJSONString(form);
		JSONObject paramsJSON = JSONObject.parseObject(paramsStr);
		paramsJSON.put("debug_url", debugUrlJSONA);
		String result = HttpClientUtils.executeByJSONPOST(url, paramsJSON.toJSONString(), 50000);
		return result;
	}
	
	public String qrcodejumpdownload(String wxAppid){
		String url = wxProperties.getWx_qrcode_qrcodejumpdownload_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = HttpClientUtils.executeByJSONPOST(url, new JSONObject().toJSONString(), 50000);
		return result;
	}
}
