package com.wangxiaobao.wechatgateway.controller.store;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.StoreInfoForm;
import com.wangxiaobao.wechatgateway.repository.store.StoreInfoRepository;
import com.wangxiaobao.wechatgateway.service.store.StoreInfoService;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Controller
@RequestMapping("/storeinfo")
@Slf4j
public class StoreInfoController {

  @Autowired
  private StoreInfoService storeInfoService;

  @PostMapping("/create")
  public ResultVO<StoreInfo> create(@Valid StoreInfoForm storeInfoForm,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【创建商家】参数不正确, orderForm={}", storeInfoForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    StoreInfo storeInfo = new StoreInfo();
    BeanUtils.copyProperties(storeInfoForm,storeInfo);
    StoreInfo result = storeInfoService.create(storeInfo);
    return ResultVOUtil.success(result);
  }
}
