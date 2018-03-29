package com.wangxiaobao.wechatgateway.service.store;

import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.form.gongzhonghao.gongzhonghaoListResquest;
import com.wangxiaobao.wechatgateway.repository.store.BrandInfoRepository;
import com.wangxiaobao.wechatgateway.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@Service
@Slf4j
public class BrandInfoService {

	@Autowired
	private BrandInfoRepository brandInfoRepository;

	public BrandInfo save(BrandInfo brandInfo) {
		return brandInfoRepository.save(brandInfo);
	}

	public BrandInfo findOne(String brandId) {
		return brandInfoRepository.findOne(brandId);
	}

	public BrandInfo findByOrgId(String orgId) {
		return brandInfoRepository.findByOrgId(orgId);
	}

	public BrandInfo findByBrandAccount(String orgAccount) {
		return brandInfoRepository.findByOrgAccount(orgAccount);
	}

	/**
	  * @methodName: findLikeOrgName
	  * @Description: TODO调用权限系统查询品牌分页列表接口返回结果
	  * @param request
	  * @param url
	  * @return
	  * @throws Exception String
	  * @createUser: liping_max
	  * @createDate: 2018年3月29日 下午5:00:11
	  * @updateUser: liping_max
	  * @updateDate: 2018年3月29日 下午5:00:11
	  * @throws
	 */
	public String findLikeOrgName(gongzhonghaoListResquest request,String url) throws Exception {
		List<NameValuePair> params = new ArrayList<>();
		NameValuePair orgNameNVP = new BasicNameValuePair("orgName", request.getBrandName());
		NameValuePair pageNumNVP = new BasicNameValuePair("pageNum", request.getPage()+"");
		NameValuePair pageSizeNVP = new BasicNameValuePair("pageSize", request.getSize()+"");
		
		params.add(orgNameNVP);
		params.add(pageNumNVP);
		params.add(pageSizeNVP);
		String result = HttpClientUtils.executeByPOST(url, params);
		log.info("调用权限系统查询品牌分页列表接口返回结果：{}",result);
		return result;
	}
}
