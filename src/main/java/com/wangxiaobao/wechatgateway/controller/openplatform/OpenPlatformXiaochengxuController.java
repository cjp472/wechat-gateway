package com.wangxiaobao.wechatgateway.controller.openplatform;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.controller.base.BaseController;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.form.xiaochengxu.OpenPlatformXiaochengxuResponse;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.utils.JsonResult;

@RestController
public class OpenPlatformXiaochengxuController extends BaseController{
	@Autowired
	private OpenPlatformXiaochengxuService openPlatformXiaochengxuService;
	@Autowired
	private WXopenPlatformMerchantInfoService wxPlatformMerchantInfoService;
	
	@RequestMapping("/xiaochengxu/findXiaochengxuByCode")
	public JsonResult findXiaochengxuByCode(String code){
		if(StringUtils.isEmpty(code)){
			return JsonResult.newInstanceMesFail("code未传");
		}
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuService.findXiaochengxuByCode(code);
		OpenPlatformXiaochengxuResponse response = new OpenPlatformXiaochengxuResponse();
		BeanUtils.copyProperties(openPlatformXiaochengxu, response);
		response.setComponentAppId(appId);
		return JsonResult.newInstanceDataSuccess(openPlatformXiaochengxu);
	}
	
	/**
	  * @methodName: modifyDomain
	  * @Description: 设置小程序服务器域名
	  * @param wxAppid
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月15日 下午7:17:53
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月15日 下午7:17:53
	  * @throws
	 */
	@RequestMapping("/miniprogram/modifyDomain")
	public JsonResult modifyDomain(String wxAppid,String action){
		String url = wxProperties.getWx_modify_domain_url()+wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = openPlatformXiaochengxuService.modifyDomain(url, wxAppid, action);
		return JsonResult.newInstanceDataSuccess(result);
	}
	
	
	/**
	  * @methodName: modifyDomain
	  * @Description: 设置小程序业务域名
	  * @param wxAppid
	  * @return JsonResult
	  * @createUser: liping_max
	  * @createDate: 2018年1月15日 下午7:17:53
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月15日 下午7:17:53
	  * @throws
	 */
	@RequestMapping("/miniprogram/setwebviewdomain")
	public JsonResult setwebviewdomain(String wxAppid,String action){
		String url = wxProperties.getWx_setwebviewdomain_url()+wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = openPlatformXiaochengxuService.setwebviewdomain(url, wxAppid, action);
		return JsonResult.newInstanceDataSuccess(result);
	}
}
