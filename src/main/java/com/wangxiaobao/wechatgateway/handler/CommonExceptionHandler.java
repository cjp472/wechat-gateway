package com.wangxiaobao.wechatgateway.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by halleyzhang on 2018/1/14.
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
  @ExceptionHandler(CommonException.class)
  @ResponseBody
  public ResultVO handlerCommonException(CommonException e) {
    log.error("【通用异常】{}",e);
    return ResultVOUtil.error(e.getCode() , e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResultVO handlerException(Exception e) {
    log.error("【未捕获异常】{}",e);
    return ResultVOUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), e.getMessage());
  }
}
