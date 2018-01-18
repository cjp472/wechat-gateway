package com.wangxiaobao.wechatgateway.entity.geo;

import lombok.Data;

/**
 * Created by halleyzhang on 2018/1/17.
 * 通过坐标查询高德地图返回的信息中获取地址
 */
@Data
public class GeoAddress {

  private String address;

  private String city;

}
