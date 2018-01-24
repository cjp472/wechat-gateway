package com.wangxiaobao.wechatgateway.controller.ad;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.VO.ad.AdVO;
import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import com.wangxiaobao.wechatgateway.entity.ad.AdInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.ad.AdForm;
import com.wangxiaobao.wechatgateway.service.ad.AdDetailService;
import com.wangxiaobao.wechatgateway.service.ad.AdService;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@RestController
@RequestMapping("/adinfo")
@Slf4j
public class AdController {

  @Autowired
  private AdService adService;

  @Autowired
  private AdDetailService adDetailService;

  @PostMapping("/save")
  public ResultVO<AdInfo> save(@Valid AdForm adForm,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【创建商家广告】参数不正确, adForm={}", adForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    AdInfo adInfo = new AdInfo();
    if(!StringUtils.isEmpty(adForm.getAdId())){
      adInfo = adService.findOne(adForm.getAdId());
    }else{
      adForm.setAdId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(adForm,adInfo);
    AdInfo result = adService.save(adInfo);
    log.info("保存商家广告 成功 result={}",result);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/getById")
  public ResultVO<AdInfo> getById(@RequestParam("adId") String adId){
    AdInfo result = this.adService.findOne(adId);
    return ResultVOUtil.success(result);
  }

  @PostMapping("/delete")
  public ResultVO delete(@RequestParam("adId") String adId){
    adService.delete(adId);
    return ResultVOUtil.success();
  }

  @GetMapping("/getByMerchantAccount")
  public ResultVO<List<AdVO>> getByMerchantAccount(@RequestParam("merchantAccount") String merchantAccount){
    List<AdVO> result = new ArrayList<>();

    //获取账号下的广告列表
    List<AdInfo> ads = adService.findByMerchantAccount(merchantAccount);
    for(AdInfo adInfo:ads){
      AdVO adVO = new AdVO();
      adVO.setAdInfo(adInfo);
      List<AdDetail> details = adDetailService.findByAdId(adInfo.getAdId());
      adVO.setAdDetails(details);
      result.add(adVO);
    }
    return ResultVOUtil.success(result);
  }


}
