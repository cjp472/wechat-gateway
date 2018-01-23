package com.wangxiaobao.wechatgateway.utils;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.header.LoginUserInfo;
import com.wangxiaobao.wechatgateway.entity.header.PlatformOrgUserInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.net.URLDecoder;

/**
 * @author Zhoutong
 * @version V1.0
 * @Title: LoginUserInfoMethodArgumentResolver
 * @Project wechat-user
 * @Package com.wangxiaobao.market.base.mvc.argumentresolver
 * @Description: APP用户参数解析器
 * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
 * @Company:成都国盛天丰技术有限责任公司
 * @date 2018/1/16 11:41
 */
@Slf4j
public class LoginUserInfoMethodArgumentResolver implements HandlerMethodArgumentResolver {
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
        return methodParameter.getParameterType().equals(LoginUserInfo.class) && methodParameter.getParameterName().equals("loginUserInfo");
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
            String loginUserStr = URLDecoder.decode(nativeWebRequest.getHeader("loginUserInfo"), "UTF-8");
            log.info("header参数为:"+loginUserStr);
            JSONObject obj = JSONObject.parseObject(loginUserStr);
            LoginUserInfo loginUserInfo = new LoginUserInfo();
            loginUserInfo.setUserId(obj.getString("userId"));
            loginUserInfo.setUserAccount(obj.getString("userAccount"));
            loginUserInfo.setUserName(obj.getString("userName"));
            loginUserInfo.setLoginTime(obj.getString("loginTime"));
            Integer userType = obj.getInteger("currentLevel");
            if(1 == userType){
                loginUserInfo.setUserType("1");
            }else if(3 == userType){
                loginUserInfo.setUserType("0");
            }
            if(obj.getJSONObject("org").getJSONObject("0")!=null){//0表示是集团信息
                loginUserInfo.setGroupAccount(obj.getJSONObject("org").getJSONObject("0").getString("account"));
                loginUserInfo.setGroupName(obj.getJSONObject("org").getJSONObject("0").getString("name"));
            }
            if(obj.getJSONObject("org").getJSONObject("1")!=null){//1表示是品牌信息
                loginUserInfo.setOrganizationAccount(obj.getJSONObject("org").getJSONObject("1").getString("account"));
                loginUserInfo.setOrganizationName(obj.getJSONObject("org").getJSONObject("1").getString("name"));
                loginUserInfo.setMerchant(obj.getJSONArray("merchant").toJavaList(
                    LoginUserInfo.Merchant.class));
            }
            if(obj.getJSONObject("org").getJSONObject("2")!=null){//2表示区域信息
                loginUserInfo.setAreaAccount(obj.getJSONObject("org").getJSONObject("2").getString("account"));
                loginUserInfo.setAreaName(obj.getJSONObject("org").getJSONObject("2").getString("name"));
            }
            if(obj.getJSONObject("org").getJSONObject("3")!=null){//3表示门店信息
                loginUserInfo.setMerchantAccount(obj.getJSONObject("org").getJSONObject("3").getString("account"));
                loginUserInfo.setMerchantName(obj.getJSONObject("org").getJSONObject("3").getString("name"));
            }
            log.info("header解析后:" + loginUserInfo);
            return loginUserInfo;
        }catch (Exception e){
            log.error("logUser header exception",e);
            throw new CommonException(ResultEnum.HEADER_GAIN_ERROR.getCode(),e.getMessage());
        }
    }
}
