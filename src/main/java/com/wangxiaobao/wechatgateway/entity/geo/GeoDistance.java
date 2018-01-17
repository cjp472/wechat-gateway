package com.wangxiaobao.wechatgateway.entity.geo;

import lombok.Data;

/**
 * Created by halleyzhang on 2018/1/17.
 * 从高的地图返回的距离对象中获取存储距离
 */
@Data
public class GeoDistance {

  private String origin_id;

  private String distance;

}
