package com.wangxiaobao.wechatgateway.utils;

import static org.junit.Assert.*;

import com.oracle.tools.packager.Log;
import com.wangxiaobao.wechatgateway.entity.geo.GeoCode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AmapUtilTest {

  @Autowired
  private AmapUtil amapUtil;

  @Test
  public void getGeoCode() throws Exception {
    String address = "四川省成都市华阳销魂掌华阳店|成都市都江堰市青城山区成都东软学校";
    List<GeoCode> codes = amapUtil.getGeoCode(address);
    log.info(codes.toString());
  }

  @Test
  public void distanceParse(){
    String str = "{\n"
        + "status: \"1\",\n"
        + "info: \"OK\",\n"
        + "infocode: \"10000\",\n"
        + "results: [\n"
        + "{\n"
        + "origin_id: \"1\",\n"
        + "dest_id: \"1\",\n"
        + "distance: \"1556\",\n"
        + "duration: \"180\"\n"
        + "},\n"
        + "{\n"
        + "origin_id: \"2\",\n"
        + "dest_id: \"1\",\n"
        + "distance: \"1556\",\n"
        + "duration: \"180\"\n"
        + "}\n"
        + "]\n"
        + "}";
    log.info(String.valueOf(amapUtil.parseDistance(str)));
  }

  @Test
  public void getDistance(){
    String orgin = "104.068359,30.538196|104.068359,30.538196";
    String destination = "104.066428,30.527265";
    log.info(amapUtil.getDistance(orgin,destination).toString());
  }

  @Test
  public void parse() throws Exception {
    String str = "{\n"
        + "status: \"1\",\n"
        + "info: \"OK\",\n"
        + "infocode: \"10000\",\n"
        + "count: \"2\",\n"
        + "geocodes: [\n"
        + "{\n"
        + "formatted_address: \"四川省成都市双流区销魂掌华阳店\",\n"
        + "province: \"四川省\",\n"
        + "citycode: \"028\",\n"
        + "city: \"成都市\",\n"
        + "district: \"双流区\",\n"
        + "township: [ ],\n"
        + "neighborhood: {\n"
        + "name: [ ],\n"
        + "type: [ ]\n"
        + "},\n"
        + "building: {\n"
        + "name: [ ],\n"
        + "type: [ ]\n"
        + "},\n"
        + "adcode: \"510116\",\n"
        + "street: [ ],\n"
        + "number: [ ],\n"
        + "location: \"104.066428,30.527265\",\n"
        + "level: \"兴趣点\"\n"
        + "},\n"
        + "{\n"
        + "formatted_address: \"四川省成都市都江堰市成都东软学校\",\n"
        + "province: \"四川省\",\n"
        + "citycode: \"028\",\n"
        + "city: \"成都市\",\n"
        + "district: \"都江堰市\",\n"
        + "township: [ ],\n"
        + "neighborhood: {\n"
        + "name: [ ],\n"
        + "type: [ ]\n"
        + "},\n"
        + "building: {\n"
        + "name: [ ],\n"
        + "type: [ ]\n"
        + "},\n"
        + "adcode: \"510181\",\n"
        + "street: [ ],\n"
        + "number: [ ],\n"
        + "location: \"103.596471,30.888439\",\n"
        + "level: \"兴趣点\"\n"
        + "}\n"
        + "]\n"
        + "}";
    List<GeoCode> codes = amapUtil.parse(str);
    log.info(codes.toString());
  }




}