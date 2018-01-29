package com.wangxiaobao.wechatgateway.service.openplatform;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.constantcode.ConstantCode;
import com.wangxiaobao.wechatgateway.entity.miniprogramtemplate.WxMiniprogramTemplate;
import com.wangxiaobao.wechatgateway.entity.openplatform.OpenPlatformXiaochengxu;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.organizetemplate.OrganizeTemplate;
import com.wangxiaobao.wechatgateway.enums.MiniprogramTemplateTypeEnum;
import com.wangxiaobao.wechatgateway.enums.OrganizeTemplateStatusEnum;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.repository.openplatform.OpenPlatformXiaochengxuRepository;
import com.wangxiaobao.wechatgateway.service.base.BaseService;
import com.wangxiaobao.wechatgateway.service.constantcode.ConstantCodeService;
import com.wangxiaobao.wechatgateway.service.miniprogramtemplate.WxMiniprogramTemplateService;
import com.wangxiaobao.wechatgateway.service.organizetemplate.OrganizeTemplateService;
import com.wangxiaobao.wechatgateway.service.test.TestService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class OpenPlatformXiaochengxuService extends BaseService {
	@Value("${wechat.miniprogram.requestdomain1}")
	private String requestdomain1;
	@Value("${wechat.miniprogram.wsrequestdomain1}")
	private String wsrequestdomain1;
	@Value("${wechat.miniprogram.uploaddomain1}")
	private String uploaddomain1;
	@Value("${wechat.miniprogram.downloaddomain1}")
	private String downloaddomain1;
	@Value("${wechat.miniprogram.requestdomain2}")
	private String requestdomain2;
	@Value("${wechat.miniprogram.wsrequestdomain2}")
	private String wsrequestdomain2;
	@Value("${wechat.miniprogram.uploaddomain2}")
	private String uploaddomain2;
	@Value("${wechat.miniprogram.downloaddomain2}")
	private String downloaddomain2;
	@Value("${wechat.miniprogram.webviewdomain1}")
	private String webviewdomain1;
	@Value("${wechat.miniprogram.webviewdomain2}")
	private String webviewdomain2;
	@Value("${wechat.miniprogram.userversion}")
	private String userVersion;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private OpenPlatformXiaochengxuRepository openPlatformXiaochengxuRepository;
	@Autowired
	private WXopenPlatformMerchantInfoService wxPlatformMerchantInfoService;
	@Autowired
	private WxMiniprogramTemplateService wxMiniprogramTemplateService;
	@Autowired
	private OrganizeTemplateService organizeTemplateService;
	@Autowired
	private ConstantCodeService constantCodeService;

	/**
	 * 查询可以绑定的小程序
	 * 
	 * @return
	 */
	public OpenPlatformXiaochengxu findCanBindXiaochengxu() {
		List<OpenPlatformXiaochengxu> openPlatformXiaochengxus = openPlatformXiaochengxuRepository
				.findByTopLimitAndIsValidateAndType("0", "1", "1");
		if (null == openPlatformXiaochengxus || openPlatformXiaochengxus.size() <= 0) {
			log.info("当前没有可以被商户公众号关联的小程序");
			return null;
		}
		return openPlatformXiaochengxus.get(0);
	}

	/**
	 * 根据code查询平台小程序
	 * 
	 * @param code
	 * @return
	 */
	public OpenPlatformXiaochengxu findXiaochengxuByCode(String code) {
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuRepository.findByCode(code);
		return openPlatformXiaochengxu;
	}
	
	public OpenPlatformXiaochengxu findXiaochengxuByAppId(String appId) {
		OpenPlatformXiaochengxu openPlatformXiaochengxu = openPlatformXiaochengxuRepository.findByAppId(appId);
		return openPlatformXiaochengxu;
	}

	public String modifyDomain(String wxAppid, String action) {
		String url = wxProperties.getWx_modify_domain_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		param.put("action", action);
		param.put("requestdomain", new String[] { requestdomain1, requestdomain2 });
		param.put("wsrequestdomain", new String[] { wsrequestdomain1, wsrequestdomain2 });
		param.put("uploaddomain", new String[] { uploaddomain1, uploaddomain2 });
		param.put("downloaddomain", new String[] { downloaddomain1, downloaddomain2 });
		String result = HttpClientUtils.executeByJSONPOST(url, param.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		JSONObject modifyDomainJson = JSONObject.parseObject(result);
		if (!JsonResult.APP_RETURN_SUCCESS.equals(modifyDomainJson.getString("errcode"))
				&& !"85017".equals(modifyDomainJson.getString("errcode"))) {
			log.error("设置商家小程序{}服务器域名异常{}", wxAppid, modifyDomainJson.getString("errmsg"));
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), modifyDomainJson.getString("errmsg"));
		}
		return result;
	}

	public String setwebviewdomain(String wxAppid, String action) {
		String url = wxProperties.getWx_setwebviewdomain_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		param.put("action", action);
		param.put("webviewdomain", new String[] { webviewdomain1, webviewdomain2 });
		String result = HttpClientUtils.executeByJSONPOST(url, param.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		JSONObject setwebviewdomainJson = JSONObject.parseObject(result);
		if (!JsonResult.APP_RETURN_SUCCESS.equals(setwebviewdomainJson.getString("errcode"))
				&& !"89019".equals(setwebviewdomainJson.getString("errcode"))) {
			log.error("设置商家小程序{}业务域名异常{}", wxAppid, setwebviewdomainJson.getString("errmsg"));
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), setwebviewdomainJson.getString("errmsg"));
		}
		return result;
	}

	public JSONObject commit(String wxAppid, String templateId, String organizationAccount) {
		String url = wxProperties.getWx_miniprogram_commit_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		param.put("template_id", templateId);
		param.put("user_version", userVersion);
		param.put("user_desc", "test");
		JSONObject params = new JSONObject();
		JSONObject ext = new JSONObject();
		ext.put("organizationAccount", organizationAccount);
		params.put("ext", ext);
		param.put("ext_json", params.toString());
		JSONObject result = restTemplate.postForObject(url, param, JSONObject.class);
//		String result = HttpClientUtils.executeByJSONPOST(url, param.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		if (!JsonResult.APP_RETURN_SUCCESS.equals(result.getString("errcode"))) {
			log.error("为商家小程序{}上传模板异常{}", wxAppid, result.getString("errmsg"));
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), result.getString("errmsg"));
		}
		return result;
	}
public static void main(String[] args) {
	JSONObject jsono = new JSONObject();
	JSONArray jsonA= new JSONArray();
	JSONObject json = new JSONObject();
	json.put("firstClass", "餐饮");
	json.put("secondClass", "菜谱");
	json.put("firstId", 220);
	json.put("secondId", 225);
	json.put("address", "pages/home");
	json.put("title", "首页");
	jsonA.add(json);
	jsono.put("item_list", jsonA);
	System.out.println(jsono.toJSONString());
}
	
	/**
	 * @methodName: getCategory @Description: TODO获取授权小程序帐号的可选类目 @param
	 *              url @return JSONObject @createUser: liping_max @createDate:
	 *              2018年1月16日 下午5:06:11 @updateUser: liping_max @updateDate:
	 *              2018年1月16日 下午5:06:11 @throws
	 */
	public JSONObject getCategory(String wxAppid) {
		String url = wxProperties.getWx_miniprogram_get_category_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject result = restTemplate.getForObject(url, JSONObject.class);
		return result;
	}

	/**
	 * @methodName: getPage @Description: TODO获取小程序的第三方提交代码的页面配置 @param
	 *              url @return JSONObject @createUser: liping_max @createDate:
	 *              2018年1月16日 下午7:09:16 @updateUser: liping_max @updateDate:
	 *              2018年1月16日 下午7:09:16 @throws
	 */
	public JSONObject getPage(String url) {
		JSONObject result = restTemplate.getForObject(url, JSONObject.class);
		return result;
	}

	/**
	 * @methodName: submitAudit @Description: TODO将第三方提交的代码包提交审核 @param
	 *              url @param json @return JSONObject @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午5:10:11 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午5:10:11 @throws
	 */
	public JSONObject submitAudit(String wxAppid, JSONObject json) {
		String url = wxProperties.getWx_miniprogram_submit_audit_url()
				+ wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		String result = HttpClientUtils.executeByJSONPOST(url, json.toJSONString(), Constants.HTTP_CLIENT_TIMEOUT);
		JSONObject resultJson = JSONObject.parseObject(result);
		if (!JsonResult.APP_RETURN_SUCCESS.equals(resultJson.getString("errcode"))
				&& !"89019".equals(resultJson.getString("errcode"))&&!"85009".equals(resultJson.getString("errcode"))) {
			log.error("设置商家小程序{}业务域名异常{}", wxAppid, resultJson.getString("errmsg"));
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), resultJson.getString("errmsg"));
		}
		return resultJson;
	}

	/**
	 * @methodName: getAuditstatus @Description: TODO查询某个指定版本的审核状态 @param
	 *              url @param auditid @return JSONObject @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午7:57:12 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午7:57:12 @throws
	 */
	public JSONObject getAuditstatus(String url, String auditid) {
		JSONObject param = new JSONObject();
		param.put("auditid", auditid);
		JSONObject result = restTemplate.postForObject(url, param.toJSONString(), JSONObject.class);
		return result;
	}

	/**
	 * @methodName: getLatestAuditstatus @Description: TODO查询最新一次提交的审核状态 @param
	 *              url @return JSONObject @createUser: liping_max @createDate:
	 *              2018年1月16日 下午8:00:00 @updateUser: liping_max @updateDate:
	 *              2018年1月16日 下午8:00:00 @throws
	 */
	public JSONObject getLatestAuditstatus(String url) {
		JSONObject result = restTemplate.getForObject(url, JSONObject.class);
		return result;
	}

	/**
	 * @methodName: release @Description: TODO发布已通过审核的小程序 @param url @return
	 *              JSONObject @createUser: liping_max @createDate: 2018年1月16日
	 *              下午8:01:36 @updateUser: liping_max @updateDate: 2018年1月16日
	 *              下午8:01:36 @throws
	 */
	public JSONObject release(String wxAppid) {
		String url = "https://api.weixin.qq.com/wxa/release?access_token="+
				wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		JSONObject result = restTemplate.postForObject(url, param.toJSONString(), JSONObject.class);
		return result;
	}
	
	/**
	  * @methodName: release
	  * @Description: TODO
	  * @param wxAppid
	  * @return JSONObject
	  * @createUser: liping_max
	  * @createDate: 2018年1月24日 下午4:23:36
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月24日 下午4:23:36
	  * @throws
	 */
	public JSONObject bindTester(String wechatid,String wxAppid) {
		String url = "https://api.weixin.qq.com/wxa/bind_tester?access_token="+
				wxPlatformMerchantInfoService.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		param.put("wechatid", wechatid);
		JSONObject result = restTemplate.postForObject(url, param.toJSONString(), JSONObject.class);
		return result;
	}

	/**
	 * @methodName: setweappsupportversion @Description: TODO设置最低基础库版本 @param
	 *              url @param version @return JSONObject @createUser:
	 *              liping_max @createDate: 2018年1月16日 下午8:28:55 @updateUser:
	 *              liping_max @updateDate: 2018年1月16日 下午8:28:55 @throws
	 */
	public JSONObject setweappsupportversion(String wxAppid, String version) {
		String url = wxProperties.getWx_miniprogram_setweappsupportversion_url() + wxPlatformMerchantInfoService
				.getWXopenPlatformMerchantInfo(wxAppid).getAuthoriceAccessToken();
		JSONObject param = new JSONObject();
		param.put("version", version);
		JSONObject result = restTemplate.postForObject(url, param.toJSONString(), JSONObject.class);
		return result;
	}

	/**
	  * @methodName: initXiaochengxu
	  * @Description: TODO给小程序发模板
	  * @param wxAppid
	  * @param action
	  * @param organizationAccount void
	  * @createUser: liping_max
	  * @createDate: 2018年1月17日 下午7:43:33
	  * @updateUser: liping_max
	  * @updateDate: 2018年1月17日 下午7:43:33
	  * @throws
	 */
	public void initXiaochengxu(String wxAppid, String action, String organizationAccount,String userName) {
		// 1，设置小程序服务器域名---code为0或者85017都算成功
		modifyDomain(wxAppid, action);
		// 2，设置小程序业务域名
		setwebviewdomain(wxAppid, action);
		// 获取当前默认发布的模板信息
		WxMiniprogramTemplate wxMiniprogramTemplate = wxMiniprogramTemplateService
				.findWxMiniprogramTemplateDefaultByType(MiniprogramTemplateTypeEnum.PLATFORM_PAGE_TEMPLATE.getType());
		// 为商家小程序上传模板
		JSONObject commitJson = commit(wxAppid, wxMiniprogramTemplate.getTemplateId(), organizationAccount);
		// 将商家上传的版本记录到数据库
		OrganizeTemplate organizeTemplate = new OrganizeTemplate();
		organizeTemplate.setCreateDate(new Date());
		organizeTemplate.setTemplateId(wxMiniprogramTemplate.getTemplateId());
		organizeTemplate.setExtJson(commitJson.getString("ext_json"));
		organizeTemplate.setMiniprogramTemplateId(KeyUtil.genUniqueKey());
		organizeTemplate.setOrganizationAccount(organizationAccount);
		organizeTemplate.setWxAppId(wxAppid);
		organizeTemplate.setStatus(OrganizeTemplateStatusEnum.UPLOAD.getStatus());
		organizeTemplate.setIsOnline("0");
		organizeTemplate.setIsNew("1");
		//设置当前为new为1，其它为0
		organizeTemplateService.updateOrganizeTemplateIsNew(wxAppid,"0");
		OrganizeTemplate organizeTemplateResult = organizeTemplateService.save(organizeTemplate);
		if (null == organizeTemplateResult) {
			log.error("将商家小程序{}版本上传的版本记录到数据库失败", wxAppid);
			throw new CommonException(ResultEnum.RETURN_ERROR.getCode(), "保存商家小程序" + wxAppid + "模板版本号数据库异常");
		}
		// 获取商家小程序账号需要配置的address和类目
		ConstantCode constantCode = new ConstantCode("templatePage", "yellowpages");
		constantCode = constantCodeService.findConstantCode(constantCode);
		JSONObject submitauditparamJson = JSONObject.parseObject(constantCode.getValue());
		//设置tag，将小程序名字设置到tag里面
		WXopenPlatformMerchantInfo wxInfo = wxPlatformMerchantInfoService.getByWXAppId(wxAppid);
		String tag = wxInfo.getNickName();
		JSONArray itemListJsonA = submitauditparamJson.getJSONArray("item_list");
		for(int i=0;i<itemListJsonA.size();i++){
			JSONObject itemJson = itemListJsonA.getJSONObject(i);
			if(StringUtils.isEmpty(itemJson.getString("tag"))){
				itemJson.put("tag", tag);
			}else{
				itemJson.put("tag", itemJson.getString("tag")+" "+tag);
			}
		}
		// 将第三方提交的代码包提交审核
		submitAudit(wxAppid, submitauditparamJson);
		organizeTemplate.setStatus(OrganizeTemplateStatusEnum.AUDITING.getStatus());
		organizeTemplateService.save(organizeTemplate);
	}
}
