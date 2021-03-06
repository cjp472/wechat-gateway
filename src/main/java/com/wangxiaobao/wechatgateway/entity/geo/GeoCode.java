package com.wangxiaobao.wechatgateway.entity.geo;

import lombok.Data;

/**
 * Created by halleyzhang on 2018/1/16.
 * 从高德地图查询地址返回的结果中获取坐标
 */
@Data
public class GeoCode {

  private String formatted_address;

  private String location;

}
