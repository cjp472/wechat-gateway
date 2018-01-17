package com.wangxiaobao.wechatgateway.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.entity.geo.GeoAddress;
import com.wangxiaobao.wechatgateway.entity.geo.GeoCode;
import com.wangxiaobao.wechatgateway.entity.geo.GeoDistance;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by halleyzhang on 2018/1/16.
 * 高德地图工具类
 */
@Service
@Slf4j
public class AmapUtil {

  @Autowired
  private RestTemplate restTemplate;

  /**
   * 通过调用高德地图获取地址的坐标
   * @param address 地址 如果多个地址请用"|"分割
   * @return
   */
  public List<GeoCode> getGeoCode(String address){
    String url = "http://restapi.amap.com/v3/geocode/geo?address="+address+"&output=JSON&key=92d093befe769ff153a0c34f7477ed73&batch=true";
    String result = restTemplate.getForObject(url,String.class);
    return this.parse(result);
  }

  public List<GeoCode> parse(String str){
    JSONObject dataJson = JSON.parseObject(str);
    JSONArray jsonArray = dataJson.getJSONArray("geocodes");
    List<GeoCode> codes = JSON.parseArray(jsonArray.toJSONString(),GeoCode.class);
    return codes;
  }

  /**
   * 通调用高德地图获取源坐标与目标坐标间的距离
   * @param origin  源地址坐标  如果有多个用"|"分割
   * @param destination 目标地址坐标
   * @return
   */
  public List<GeoDistance> getDistance(String origin,String destination){
    String url = "http://restapi.amap.com/v3/distance?origins="+origin+"&destination="+destination+"&output=json&key=92d093befe769ff153a0c34f7477ed73";
    String result = restTemplate.getForObject(url,String.class);
    return this.parseDistance(result);
  }

  public List<GeoDistance> parseDistance(String str){
    JSONObject dataJson = JSON.parseObject(str);
    JSONArray jsonArray = dataJson.getJSONArray("results");
    List<GeoDistance> distances = JSON.parseArray(jsonArray.toJSONString(),GeoDistance.class);
    return distances;
  }

  /**
   * 输入坐标从高德地图查询出地址信息
   * @param location  用户坐标
   * @return
   */
  public GeoAddress getAddress(String location){
    String url = "http://restapi.amap.com/v3/geocode/regeo?output=json&location="+location+"&key=92d093befe769ff153a0c34f7477ed73&radius=1000&extensions=base";
    String result = restTemplate.getForObject(url,String.class);
    return this.parseAddress(result);
  }

  public GeoAddress parseAddress(String str){
    GeoAddress result  = new GeoAddress();
    JSONObject dataJson  = JSON.parseObject(str);
    JSONObject jsonObject = dataJson.getJSONObject("regeocode");
    result.setAddress(jsonObject.getString("formatted_address"));
    result.setCity(jsonObject.getJSONObject("addressComponent").getString("city"));
    return result;
  }

}
