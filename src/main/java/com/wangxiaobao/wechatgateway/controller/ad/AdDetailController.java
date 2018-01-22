package com.wangxiaobao.wechatgateway.controller.ad;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.ad.AdDetailForm;
import com.wangxiaobao.wechatgateway.service.ad.AdDetailService;
import com.wangxiaobao.wechatgateway.service.ad.AdService;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@RestController
@RequestMapping("/adDetail")
@Slf4j
public class AdDetailController {

  @Autowired
  private AdDetailService adDetailService;

  @RequestMapping("/getByAdId")
  public ResultVO<List<AdDetail>> getByAdId(@RequestParam("adId") String adId){
    List<AdDetail> adDetails = adDetailService.findByAdId(adId);
    return ResultVOUtil.success(adDetails);
  }


  @RequestMapping("/save")
  public ResultVO<AdDetail> save(@Valid AdDetailForm adDetailForm,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【保存广告图片】参数不正确, adDetailForm={}", adDetailForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    AdDetail adDetail = new AdDetail();
    if(!StringUtils.isEmpty(adDetailForm.getDetailId())){
      adDetail = adDetailService.findById(adDetailForm.getDetailId());
    }else{
      adDetailForm.setDetailId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(adDetailForm,adDetail);
    AdDetail result = adDetailService.save(adDetail);
    log.info("保存商家广告 成功 result={}",result);
    return ResultVOUtil.success(result);
  }

  @RequestMapping("/delete")
  public void delete(@RequestParam("detailId") String detailId){
    adDetailService.delete(detailId);
  }

}
