package com.wangxiaobao.wechatgateway.controller.store;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.StoreInfoForm;
import com.wangxiaobao.wechatgateway.service.store.StoreInfoService;
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
 * Created by halleyzhang on 2018/1/13.
 */
@RestController
@RequestMapping("/storeinfo")
@Slf4j
public class StoreInfoController {

  @Autowired
  private StoreInfoService storeInfoService;

  @PostMapping("/save")
  public ResultVO<StoreInfo> save(@Valid StoreInfoForm storeInfoForm,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【创建商家】参数不正确, storeInfoForm={}", storeInfoForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    StoreInfo storeInfo = new StoreInfo();
    if(!StringUtils.isEmpty(storeInfoForm.getStoreId())){
      storeInfo = storeInfoService.findOne(storeInfoForm.getStoreId());
    }else{
      storeInfoForm.setStoreId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(storeInfoForm,storeInfo);
    StoreInfo result = storeInfoService.save(storeInfo);
    return ResultVOUtil.success(result);
  }

  @PostMapping("/saveStoreMenu")
  public ResultVO<StoreInfo> storeMenuSave(@RequestParam("merchantAccount") String merchantAccount,
      @RequestParam("storeMenu") String storeMenu){

    StoreInfo result = storeInfoService.storeMenuSave(merchantAccount,storeMenu);
    return ResultVOUtil.success(result);
  }

  @PostMapping("/saveStorePhoto")
  public ResultVO<StoreInfo> storePhotoSave(@RequestParam("merchantAccount") String merchantAccount,
      @RequestParam("storePhoto") String storePhoto){

    StoreInfo result = storeInfoService.storePhotoSave(merchantAccount,storePhoto);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/findByMerchantAccount")
  public ResultVO<StoreInfo> findByMerchantAccount(@RequestParam("merchantAccount") String merchantAccount){
    StoreInfo result = storeInfoService.findByMerchantAccount(merchantAccount);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/findByMerchantId")
  public ResultVO<StoreInfo> findByMerchantId(@RequestParam("merchantId") String merchantId){
    StoreInfo result = storeInfoService.findByMerchantId(merchantId);
    return ResultVOUtil.success(result);
  }
}
