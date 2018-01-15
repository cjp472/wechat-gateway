package com.wangxiaobao.wechatgateway.service.openplatform;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.repository.openplatform.OpenPlatformXiaochengxuRepository;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OpenPlatformXiaochengxuService {
	@Value("${wechat.miniprogram.requestdomain}")
	private String requestdomain;
	@Value("${wechat.miniprogram.wsrequestdomain}")
	private String wsrequestdomain;
	@Value("${wechat.miniprogram.uploaddomain}")
	private String uploaddomain;
	@Value("${wechat.miniprogram.downloaddomain}")
	private String downloaddomain;
	@Value("${wechat.miniprogram.webviewdomain1}")
	private String webviewdomain1;
	@Value("${wechat.miniprogram.webviewdomain2}")
	private String webviewdomain2;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private OpenPlatformXiaochengxuRepository openPlatformXiaochengxuRepository;
	/**
	 * 查询可以绑定的小程序
	 * @return
	 */
	public OpenPlatformXiaochengxu findCanBindXiaochengxu(){
		List<OpenPlatformXiaochengxu> openPlatformXiaochengxus = openPlatformXiaochengxuRepository.findByTopLimitAndIsValidateAndType("0","1","1");
		if(null==openPlatformXiaochengxus||openPlatformXiaochengxus.size()<=0){
			log.info("当前没有可以被商户公众号关联的小程序");
			return null;
		}
		return openPlatformXiaochengxus.get(0);
	}
	
	/**
	 * 根据code查询平台小程序
	 * @param code
	 * @return
	 */
	public OpenPlatformXiaochengxu findXiaochengxuByCode(String code){
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuRepository.findByCode(code);
		return openPlatformXiaochengxu;
	}
	
	public String modifyDomain(String url,String wxAppid,String action){
		JSONObject param = new JSONObject();
		param.put("action", action);
		param.put("requestdomain", new String[]{requestdomain});
		param.put("wsrequestdomain", new String[]{wsrequestdomain});
		param.put("uploaddomain", new String[]{uploaddomain});
		param.put("downloaddomain", new String[]{downloaddomain});
		String result = HttpClientUtils.executeByJSONPOST(url, param.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		return result;
	}
	
	public String setwebviewdomain(String url,String wxAppid,String action){
		JSONObject param = new JSONObject();
		param.put("action", action);
		param.put("webviewdomain", new String[]{webviewdomain1,webviewdomain2});
//		JSONObject jsonResult = restTemplate.postForObject(url, param, JSONObject.class);
		String result = HttpClientUtils.executeByJSONPOST(url, param.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		return result;
	}
}
