package com.wangxiaobao.wechatgateway.controller.store;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.BrandInfoForm;
import com.wangxiaobao.wechatgateway.service.store.BrandInfoService;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
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
 * Created by halleyzhang on 2018/1/16.
 */
@RestController
@RequestMapping("/brandinfo")
@Slf4j
public class BrandInfoController {

  @Autowired
  private BrandInfoService brandInfoService;

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
    BrandInfo result = brandInfoService.findByOrgAccount(orgAccount);
    return ResultVOUtil.success(result);
  }
}
