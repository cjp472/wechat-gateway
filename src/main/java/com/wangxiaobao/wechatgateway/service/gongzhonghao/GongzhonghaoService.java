package com.wangxiaobao.wechatgateway.service.gongzhonghao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.repository.openplatform.OpenPlatformXiaochengxuRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.service.openplatform.OpenPlatformXiaochengxuService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
@Service
public class GongzhonghaoService extends BaseService {
	@Autowired
	RedisService redisService;
	@Autowired
	OpenPlatformXiaochengxuRepository openPlatformXiaochengxuRepository;
	/**
	  * @methodName: getAccessToken
	  * @Description: TODO获取调用凭证
	  * @param appid
	  * @param secret
	  * @return String
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 下午10:11:49
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 下午10:11:49
	  * @throws
	 */
	public String getAccessToken(String appid){
		String accessToken = redisService.get(Constants.WX_ACCESS_TOKEN_KEY+appid);
		if(StringUtils.isEmpty(accessToken)){
			String url = wxProperties.getWx_get_access_token_url();
			OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuRepository.findByAppId(appid);
			url = url.replace("APPID", appid).replace("APPSECRET", openPlatformXiaochengxu.getAppSecret());
			String result = HttpClientUtils.executeByGET(url);
			JSONObject json = JSONObject.parseObject(result);
			accessToken =  json.getString("access_token");
			redisService.set(Constants.WX_ACCESS_TOKEN_KEY+appid, accessToken, Constants.ACCESS_TOKEN_REDIS_TIMEOUT);
		}
		return accessToken;
	}
}
