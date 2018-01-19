package com.wangxiaobao.wechatgateway.entity.push;

import lombok.Data;

@Data
public class PushForm {

    //通道 ALIYUN
    private String channel;
    //推送APP
    private String appKey;
    //推送类型 MESSAGE NOTICE
    private String pushType;
    // 设备类型 ANDROID iOS ALL
    private String deviceType;
    //推送设备类型 ANDROID IOS ALL
    private String pushDeviceType;
    //推送目标类型 TAG ALIAS DEVICES ALL
    private String target;
    //推送目标类型内容
    private String targetValue;
    //标题
    private String title;
    //内容
    private String body;
    //延后推送。可选，如果不设置表示立即推送
    private String pushTime;
    //失效时间
    private String expireTime;
    //离线消息是否保存,若保存, 在推送时候，用户即使不在线，下一次上线则会收到
    private Boolean storeOffline;
}