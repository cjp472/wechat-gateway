package com.wangxiaobao.wechatgateway.handler;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by halleyzhang on 2018/1/14.
 */
@ControllerAdvice
@Slf4j
public class CommonExceptionHandler {
  @ExceptionHandler(CommonException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ResultVO handlerCommonException(CommonException e) {
    log.error("【通用异常】",e.getMessage());
    return ResultVOUtil.error(e.getCode() , e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  @ResponseBody
  public ResultVO handlerException(Exception e) {
    log.error("【未捕获异常】",e.getMessage());
    return ResultVOUtil.error(ResultEnum.UNKNOW_ERROR.getCode(), e.getMessage());
  }
}
