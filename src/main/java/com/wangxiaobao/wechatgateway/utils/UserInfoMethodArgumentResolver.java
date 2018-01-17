package com.wangxiaobao.wechatgateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.header.PlateformOrgUserInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import java.net.URLDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author Zhoutong
 * @version V1.0
 * @Title: UserInfoMethodArgumentResolver
 * @Project wechat-user
 * @Package com.wangxiaobao.wechatuser.base.mvc.argumentresolver
 * @Description: 参数解析器
 * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
 * @Company:成都国盛天丰技术有限责任公司
 * @date 2018/1/16 11:41
 */
@Slf4j
public class UserInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {
    /**
      * @methodName: supportsParameter
      * @Description: 支持的参数类型
      * @Params: [methodParameter]
      * @Return: boolean
      * @createUser: ZhouTong
      * @createDate: 2018/1/16 11:42
      * @updateUser: ZhouTong
      * @updateDate: 2018/1/16 11:42
      */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().equals(PlateformOrgUserInfo.class) && methodParameter.getParameterName().equals("plateformOrgUserInfo");
    }


    /**
      * @methodName: resolveArgument
      * @Description: 解析参数
      * @Params: [methodParameter, modelAndViewContainer, nativeWebRequest, webDataBinderFactory]
      * @Return: java.lang.Object
      * @createUser: ZhouTong
      * @createDate: 2018/1/16 11:49
      * @updateUser: ZhouTong
      * @updateDate: 2018/1/16 11:49
      */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        try {
            String wxUserInfo = nativeWebRequest.getHeader("wxUserInfo");
            if (StringUtils.isBlank(wxUserInfo))
                return null;
            log.info("wxUserInfo :",wxUserInfo);
            return JSONObject.parseObject(URLDecoder.decode(wxUserInfo, "UTF-8"), PlateformOrgUserInfo.class);
        }catch (Exception e){
            throw new CommonException(ResultEnum.HEADER_GAIN_ERROR.getCode(),e.getMessage());
        }
    }
}
