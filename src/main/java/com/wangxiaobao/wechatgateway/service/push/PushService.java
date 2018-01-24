package com.wangxiaobao.wechatgateway.service.push;

import com.wangxiaobao.wechatgateway.entity.push.PushForm;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * @author Zhoutong
 * @version V1.0
 * @Title: PushService
 * @Project wechat-gateway
 * @Package com.wangxiaobao.wechatgateway.service.push
 * @Description: 推送服务
 * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
 * @Company:成都国盛天丰技术有限责任公司
 * @date 2018/1/19 10:55
 */
@Slf4j
@Service
public class PushService {

    @Value("${push.url}")
    private String pushUrl;
    @Value("${push.prefix}")
    private String pushPrefix;

    @Autowired
    RestTemplate restTemplate;


    public JsonResult pushMessage(String merchantAccount,String title,String body){
        if(StringUtils.isEmpty(merchantAccount) ||StringUtils.isEmpty(title)){
            log.info("推送商家账号和推送title不能为空");
            return JsonResult.newInstanceMesFail("推送商家账号和推送title不能为空");
        }
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PushForm pushForm=new PushForm();
        pushForm.setChannel("ALIYUN");
        pushForm.setPushType("MESSAGE");
        pushForm.setDeviceType("ALL");
        pushForm.setPushDeviceType("ALL");
        pushForm.setTarget("ALIAS");
        pushForm.setTargetValue(pushPrefix+"_"+merchantAccount);
        pushForm.setTitle(title);
        pushForm.setBody(body);
        HttpEntity request=new HttpEntity(pushForm, headers);
        JsonResult result = restTemplate.postForObject(pushUrl, request, JsonResult.class);
        log.info("通用推送：account={},title={},body={},result={}",merchantAccount,title,body,result);
        return result;
    }
    /**
     * @methodName: pushStoreUpdateTimesMessage
     * @Description: 推送商家更新倒计时消息
     * @Params: [merchantId]
     * @Return: JsonResult
     * @createUser: ZhouTong
     * @createDate: 2018/1/13 11:13
     * @updateUser: ZhouTong
     * @updateDate: 2018/1/13 11:13
     */
    public JsonResult pushStoreUpdateTimesMessage(String merchantId,String body){
        if(!StringUtils.hasText(merchantId) || !StringUtils.hasText(body)){
            log.info("商户ID或内容不能为空,无法推送消息");
            return null;
        }
        //商家广告发布成功推送消息给设备
        HttpHeaders headers =new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        PushForm pushForm=new PushForm();
        pushForm.setChannel("ALIYUN");
        pushForm.setPushType("MESSAGE");
        pushForm.setDeviceType("ALL");
        pushForm.setPushDeviceType("ALL");
        pushForm.setTarget("ALIAS");
        pushForm.setTargetValue(pushPrefix+"_"+merchantId);
        pushForm.setTitle("UPDATETIMES");
        pushForm.setBody(body);
        HttpEntity request=new HttpEntity(pushForm, headers);
        JsonResult result = restTemplate.postForObject(pushUrl, request, JsonResult.class);
        log.info("推送商家更新倒计时消息：{}",result);
        return result;
    }
}
