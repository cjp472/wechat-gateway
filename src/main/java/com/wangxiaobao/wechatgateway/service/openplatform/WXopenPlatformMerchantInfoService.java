package com.wangxiaobao.wechatgateway.service.openplatform;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoForm;
import com.wangxiaobao.wechatgateway.form.openplatform.WXopenPlatformMerchantInfoSearchCondition;
import com.wangxiaobao.wechatgateway.repository.openplatform.WXopenPlatformMerchantInfoMapper;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.service.redis.RedisService;
import com.wangxiaobao.wechatgateway.service.wxauth.WXAuthService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Service
@Transactional
public class WXopenPlatformMerchantInfoService {
	@Value("${wechat.openplatform.appid}")
	String appId;
	@Value("${wechat.openplatform.appsecret}")
	String appsecret;
	@Autowired
	RedisService redisService;
	@Autowired
	private WXopenPlatformMerchantInfoMapper wXopenPlatformMerchantInfoMapper;
	@Autowired
	private OrganizeTemplateService organizeTemplateService;
	@Autowired
	private WXAuthService wXAuthService;
	
	public void save(WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo){
		wXopenPlatformMerchantInfoMapper.save(wXopenPlatformMerchantInfo);
	}

	public WXopenPlatformMerchantInfo getByBrandAccount(String brandAccount,String authType){
		WXopenPlatformMerchantInfo result =  wXopenPlatformMerchantInfoMapper.findByOrganizationAccountAndAuthType(brandAccount,authType);
		//只有商家小程序发布上线，审核通过之后才算真正有商家小程序
		if(!ObjectUtils.isEmpty(result)){
			OrganizeTemplate organizeTemplate = new OrganizeTemplate();
			organizeTemplate.setIsOnline("1");
			organizeTemplate.setOrganizationAccount(brandAccount);
			List<OrganizeTemplate> organizeTemplates = organizeTemplateService.findOrganizeTemplateListBy(organizeTemplate);
			if(!ObjectUtils.isEmpty(organizeTemplates)&&organizeTemplates.size()>0){
				return result;
			}else{
				return null;
			}
		}
		return null;
	}

	
	public WXopenPlatformMerchantInfo getByWXAppId(String wxAppId){
		return wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppId);
	}
	
	/**
	  * @methodName: findByCondition
	  * @Description: 根据条件查询
	  * @param wxCondition
	  * @return List<WXopenPlatformMerchantInfo>
	  * @createUser: liping_max
	  * @createDate: 2018年1月13日 下午3:13:58
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月13日 下午3:13:58
	  * @throws
	 */
	public List<WXopenPlatformMerchantInfo> findByCondition(WXopenPlatformMerchantInfoSearchCondition wxCondition){
		return wXopenPlatformMerchantInfoMapper.findByAuthTypeAndOrganizationAccount(wxCondition.getAuthType(),wxCondition.getOrganizationAccount());
	}
	
	public List<WXopenPlatformMerchantInfo> findListBy(WXopenPlatformMerchantInfoSearchCondition wxCondition){
		WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
		BeanUtils.copyProperties(wxCondition, wxInfo);
		Example<WXopenPlatformMerchantInfo> example = Example.of(wxInfo);
		return wXopenPlatformMerchantInfoMapper.findAll(example);
	}
	/**
	  * @methodName: deleteByWXAppId
	  * @Description: 根据wxAppId删除记录
	  * @param wxAppId void
	  * @createUser: liping_max
	  * @createDate: 2018年1月12日 下午4:46:48
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月12日 下午4:46:48
	  * @throws
	 */
	public void deleteByWXAppId(String wxAppId){
		wXopenPlatformMerchantInfoMapper.delete(wXopenPlatformMerchantInfoMapper.findByWxAppid(wxAppId));
	}
	
	/**
	  * @methodName: deleteByorganizationAccountAndAuthType
	  * @Description: 根据wxAppId删除记录
	  * @createUser: liping_max
	  * @createDate: 2018年1月12日 下午4:46:48
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月12日 下午4:46:48
	  * @throws
	 */
	public void deleteByorganizationAccountAndAuthType(WXopenPlatformMerchantInfoForm wxForm){
		wXopenPlatformMerchantInfoMapper.deleteInBatch(wXopenPlatformMerchantInfoMapper.findByAuthTypeAndOrganizationAccount(wxForm.getAuthType(),wxForm.getOrganizationAccount()));
	}
	
	public List<WXopenPlatformMerchantInfo> findByAuthType(String authType){
		return wXopenPlatformMerchantInfoMapper.findByAuthType(authType);
	}
	/**
	  * @methodName: update
	  * @Description: 更新
	  * @param wXopenPlatformMerchantInfo void
	  * @createUser: liping_max
	  * @createDate: 2018年1月12日 下午10:46:43
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月12日 下午10:46:43
	  * @throws
	 */
	public void update(WXopenPlatformMerchantInfo wXopenPlatformMerchantInfo){
		
	}
	
	/**
	 * @methodName: getWXopenPlatformMerchantInfo @Description:
	 *              获取三方平台调用公众号的凭证及刷新凭证 @param wxAppId @return
	 *              WXopenPlatformMerchantInfo @createUser:
	 *              liping_max @createDate: 2018年1月12日 下午6:12:01 @updateUser:
	 *              liping_max @updateDate: 2018年1月12日 下午6:12:01 @throws
	 */
	public WXopenPlatformMerchantInfo getWXopenPlatformMerchantInfo(String wxAppId) {
		WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
		String wxInfoStr = redisService.get(Constants.MERCHANT_WX_OPENPLATFORM_KEY + wxAppId);
		if (!StringUtils.isEmpty(wxInfoStr)) {
			wxInfo = JSONObject.parseObject(wxInfoStr, WXopenPlatformMerchantInfo.class);
		} else {
			wxInfo = getByWXAppId(wxAppId);
			String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="
					+ wXAuthService.getApiComponentToken(appId,appsecret);
			JSONObject jsonO = new JSONObject();
			jsonO.put("component_appid", appId);
			jsonO.put("authorizer_appid", wxAppId);
			jsonO.put("authorizer_refresh_token", wxInfo.getAuthoriceRefreshToken());
			String StrResult = HttpClientUtils.executeByJSONPOST(url, jsonO.toJSONString(), 5000);
			JSONObject jsonResult = JSONObject.parseObject(StrResult);
			//如果刷新调用凭证接口出现异常，将不更新数据库和redis记录，保持原样，通过日志查询来排查问题
			if(ObjectUtils.isEmpty(jsonResult.getString("authorizer_access_token"))||ObjectUtils.isEmpty(jsonResult.getString("authorizer_refresh_token"))){
				log.error("刷新公众号或小程序{}调用凭证异常{}",wxAppId,StrResult);
			}else{
				wxInfo.setAuthoriceAccessToken(jsonResult.getString("authorizer_access_token"));
				wxInfo.setAuthoriceRefreshToken(jsonResult.getString("authorizer_refresh_token"));
				wXopenPlatformMerchantInfoMapper.save(wxInfo);
				redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY + wxAppId, JSONObject.toJSONString(wxInfo),7000);
			}
		}
		return wxInfo;
	}
	
	/**
	  * @methodName: manualRefreshApiAuthorizerToken
	  * @Description: TODO手动刷新公众号小程序的调用凭证
	  * @param wxAppId
	  * @return WXopenPlatformMerchantInfo
	  * @createUser: liping_max
	  * @createDate: 2018年3月19日 下午3:04:40
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月19日 下午3:04:40
	  * @throws
	 */
	public WXopenPlatformMerchantInfo manualRefreshApiAuthorizerToken(String wxAppId){
		WXopenPlatformMerchantInfo wxInfo = new WXopenPlatformMerchantInfo();
		wxInfo = getByWXAppId(wxAppId);
		String url = "https://api.weixin.qq.com/cgi-bin/component/api_authorizer_token?component_access_token="
				+ wXAuthService.getApiComponentToken(appId,appsecret);
		JSONObject jsonO = new JSONObject();
		jsonO.put("component_appid", appId);
		jsonO.put("authorizer_appid", wxAppId);
		jsonO.put("authorizer_refresh_token", wxInfo.getAuthoriceRefreshToken());
		String StrResult = HttpClientUtils.executeByJSONPOST(url, jsonO.toJSONString(), 5000);
		JSONObject jsonResult = JSONObject.parseObject(StrResult);
		//如果刷新调用凭证接口出现异常，将不更新数据库和redis记录，保持原样，通过日志查询来排查问题
		if(ObjectUtils.isEmpty(jsonResult.getString("authorizer_access_token"))||ObjectUtils.isEmpty(jsonResult.getString("authorizer_refresh_token"))){
			log.error("刷新公众号或小程序{}调用凭证异常{}",wxAppId,StrResult);
		}else{
			wxInfo.setAuthoriceAccessToken(jsonResult.getString("authorizer_access_token"));
			wxInfo.setAuthoriceRefreshToken(jsonResult.getString("authorizer_refresh_token"));
			wXopenPlatformMerchantInfoMapper.save(wxInfo);
			redisService.set(Constants.MERCHANT_WX_OPENPLATFORM_KEY + wxAppId, JSONObject.toJSONString(wxInfo),7000);
		}
		return wxInfo;
	}
	/**
	  * @methodName: selectWXopenPlatformMerchantInfoListByOrganizationAccounts
	  * @Description: TODO根据品牌列表查询
	  * @param organizationAccounts
	  * @return List<WXopenPlatformMerchantInfo>
	  * @createUser: liping_max
	  * @createDate: 2018年3月19日 上午11:06:32
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月19日 上午11:06:32
	  * @throws
	 */
	public List<WXopenPlatformMerchantInfo> selectWXopenPlatformMerchantInfoListByOrganizationAccountsAndAuthType(List<String> organizationAccounts,String authType){
		return wXopenPlatformMerchantInfoMapper.selectListByOrganizationAccounts(organizationAccounts,authType);
	}
}
