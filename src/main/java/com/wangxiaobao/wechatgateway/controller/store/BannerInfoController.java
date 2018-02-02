package com.wangxiaobao.wechatgateway.controller.store;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wangxiaobao.wechatgateway.entity.header.LoginUserInfo;
import com.wangxiaobao.wechatgateway.entity.store.BannerInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.BannerInfoRequest;
import com.wangxiaobao.wechatgateway.form.store.BannerInfoTypeRequest;
import com.wangxiaobao.wechatgateway.service.store.BannerInfoService;
import com.wangxiaobao.wechatgateway.utils.Constants;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bannerInfo")
public class BannerInfoController {
	@Autowired
	private BannerInfoService bannerInfoService;

	/**
	 * @methodName: findBannerInfo @Description: TODO查询集团或者门店的banner配置 @param
	 *              orgAccount @param merchantAccount @param
	 *              bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年2月2日 下午4:32:18 @updateUser:
	 *              liping_max @updateDate: 2018年2月2日 下午4:32:18 @throws
	 */
	@RequestMapping("/findBannerInfo")
	public JsonResult findBannerInfo(@Valid BannerInfoRequest bannerInfoRequest, BindingResult bindingResult,
			String merchantAccount) {
		if (bindingResult.hasErrors()) {
			log.info("查询品牌门店bannerInfo参数异常{}", bannerInfoRequest);
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		if (StringUtils.hasText(merchantAccount)) {
			return JsonResult.newInstanceDataSuccess(
					bannerInfoService.findMerchantBannerInfoList(bannerInfoRequest.getOrgAccount(), Constants.IS_VALIDATE, merchantAccount));
		} else {
			return JsonResult
					.newInstanceDataSuccess(bannerInfoService.findOrgBannerInfoList(bannerInfoRequest.getOrgAccount(), Constants.IS_VALIDATE));
		}
	}

	/**
	 * @methodName: findBannerInfo @Description: TODO查询集团或者门店的banner配置 @param
	 *              orgAccount @param merchantAccount @param
	 *              bindingResult @return JsonResult @createUser:
	 *              liping_max @createDate: 2018年2月2日 下午4:32:18 @updateUser:
	 *              liping_max @updateDate: 2018年2月2日 下午4:32:18 @throws
	 */
	@RequestMapping("/findBannerInfoWithType")
	public JsonResult findBannerInfo(@Valid BannerInfoTypeRequest bannerInfoTypeRequest, BindingResult bindingResult,LoginUserInfo loginUserInfo) {
		if (bindingResult.hasErrors()) {
			log.info("查询品牌门店bannerInfo参数异常{}", bannerInfoTypeRequest);
			throw new CommonException(ResultEnum.PARAM_ERROR);
		}
		if (StringUtils.hasText(loginUserInfo.getMerchantAccount())) {
			return JsonResult.newInstanceDataSuccess(
					bannerInfoService.findMerchantBannerInfoTypeList(loginUserInfo.getOrganizationAccount(), Constants.IS_VALIDATE, loginUserInfo.getMerchantAccount(),bannerInfoTypeRequest.getType()));
		} else {
			return JsonResult
					.newInstanceDataSuccess(bannerInfoService.findOrgBannerInfoTypeList(loginUserInfo.getOrganizationAccount(), Constants.IS_VALIDATE,bannerInfoTypeRequest.getType()));
		}
	}

	/**
	 * @methodName: save @Description: TODO保存banner @param bannerInfo @param
	 * bindingResult @return JsonResult @createUser: liping_max @createDate:
	 * 2018年2月2日 下午4:43:39 @updateUser: liping_max @updateDate: 2018年2月2日
	 * 下午4:43:39 @throws
	 */
	@RequestMapping("/save")
	public JsonResult save(@Valid BannerInfo bannerInfo, BindingResult bindingResult,LoginUserInfo loginUserInfo) {
		if (bindingResult.hasErrors()) {
			log.info("保存品牌门店bannerInfo参数异常{}", bannerInfo);
			throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
					bindingResult.getFieldError().getDefaultMessage());
		}
		if (!StringUtils.hasText(bannerInfo.getConfigId())) {
			bannerInfo.setConfigId(KeyUtil.genUniqueKey());
		}
		return JsonResult.newInstanceDataSuccess(bannerInfoService.save(bannerInfo,loginUserInfo));
	}
}
