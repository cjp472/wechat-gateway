package com.wangxiaobao.wechatgateway.controller.store;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.VO.store.SnBrandAppVO;
import com.wangxiaobao.wechatgateway.dto.store.TableCard;
import com.wangxiaobao.wechatgateway.entity.openplatform.WXopenPlatformMerchantInfo;
import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.BrandInfoForm;
import com.wangxiaobao.wechatgateway.service.openplatform.WXopenPlatformMerchantInfoService;
import com.wangxiaobao.wechatgateway.service.store.BrandInfoService;
import com.wangxiaobao.wechatgateway.service.store.StoreInfoService;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@RestController
@RequestMapping("/brandinfo")
@Slf4j
public class BrandInfoController {

  @Autowired
  private BrandInfoService brandInfoService;
  @Autowired
  private StoreInfoService storeInfoService;
  @Autowired
  private WXopenPlatformMerchantInfoService wXopenPlatformMerchantInfoService;
  @Autowired
  private RestTemplate restTemplate;
  @Value("${fleetingtime.merchantInfoBySn}")
  private String merchantInfoBySnUrl;


  @PostMapping("/save")
  public ResultVO<BrandInfo> save(@Valid BrandInfoForm brandInfoForm,BindingResult bindingResult){
    if(bindingResult.hasErrors()){
      log.error("【创建品牌】参数不正确, brandInfoForm={}", brandInfoForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    BrandInfo brandInfo = new BrandInfo();
    if(!StringUtils.isEmpty(brandInfoForm.getBrandId())){
      brandInfo = brandInfoService.findOne(brandInfoForm.getBrandId());
    }else{
      brandInfoForm.setBrandId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(brandInfoForm,brandInfo);
    BrandInfo result = brandInfoService.save(brandInfo);
    return ResultVOUtil.success(result);
  }

  @GetMapping("findByOrgId")
  public ResultVO<BrandInfo> findByOrgId(@RequestParam("orgId") String orgId){
    BrandInfo result = brandInfoService.findByOrgId(orgId);
    return ResultVOUtil.success(result);
  }

  @GetMapping("findByAccount")
  public ResultVO<BrandInfo> findByOrgAccount(@RequestParam("orgAccount") String orgAccount){
    BrandInfo result = brandInfoService.findByBrandAccount(orgAccount);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/findBySn")
  public ResultVO<SnBrandAppVO> findBySn(@RequestParam("sn") String sn){
    SnBrandAppVO result = new SnBrandAppVO();
    //sn to merchant
    ResultVO<TableCard> resultVO = restTemplate.exchange(merchantInfoBySnUrl + "?sn=" + sn, HttpMethod.GET,null, new ParameterizedTypeReference<ResultVO<TableCard>>() {}).getBody();
    checkMerchant(resultVO);
    TableCard tableCard = resultVO.getData();

    //merchant to brand
    StoreInfo storeInfo = storeInfoService.findByMerchantAccount(tableCard.getMerchantAccount());
    checkBrand(storeInfo);

    //brand to app
    //authType=2  小程序
    WXopenPlatformMerchantInfo appInfo = wXopenPlatformMerchantInfoService.getByBrandAccount(storeInfo.getBrandAccount(),"2");
    checkAPP(appInfo);

    setValue(result, tableCard, storeInfo, appInfo);
    return ResultVOUtil.success(result);
  }

  private void setValue(SnBrandAppVO result, TableCard tableCard, StoreInfo storeInfo,
      WXopenPlatformMerchantInfo appInfo) {
    result.setMerchantAccount(tableCard.getMerchantAccount());
    result.setSn(tableCard.getSn());
    result.setTableName(tableCard.getTableName());
    result.setBrandAccount(storeInfo.getBrandAccount());
    result.setAppId(appInfo.getWxAppid());
  }

  private void checkAPP(WXopenPlatformMerchantInfo appInfo) {
    if(null == appInfo){
      log.error("【通过SN获取品牌和应用】error={}", ResultEnum.APPID_NOT_FOUND);
      throw new CommonException(ResultEnum.APPID_NOT_FOUND);
    }
  }

  private void checkBrand(StoreInfo storeInfo) {
    if(null == storeInfo){
      log.error("【通过SN获取品牌和应用】error={}", ResultEnum.BRAND_NOT_FOUND);
      throw new CommonException(ResultEnum.BRAND_NOT_FOUND);
    }
  }

  private void checkMerchant(ResultVO resultVO) {
    if(!resultVO.getCode().equals(0)){
      log.error("【通过SN获取品牌和应用】error={}",resultVO.getMsg());
      throw new CommonException(resultVO.getCode(),resultVO.getMsg());
    }
    if(null == resultVO.getData()){
      log.error("【通过SN获取品牌和应用】error={}",ResultEnum.MERCHANT_NOT_FOUND);
      throw new CommonException(ResultEnum.MERCHANT_NOT_FOUND);
    }
  }
}
