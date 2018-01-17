package com.wangxiaobao.wechatgateway.form.store;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Data
public class StoreInfoForm {

  private String storeId;

  /** 权限系统商家账号. */
  @NotEmpty(message = "商家权限系统账号必填")
  private String merchantAccount;

  /** 权限系统商家ID. */
  @NotEmpty(message = "商家权限系统ID必填")
  private String merchantId;

  /** 门店名称. */
  @NotEmpty(message = "门店名称必填")
  private String storeName;

  /** 门店省. */
  private String storeProvince;

  /** 门店市. */
  private String storeCity;

  /** 门店区. */
  private String storeDistrict;

  /** 门店地址. */
  private String storeAddress;

  /** 门店描述. */
  private String storeDescription;

  /** 门店电话. */
  private String storePhone;

  /** 门店logo. */
  private String storeLogo;

  /** 门店营业时间. */
  private String storeOfficehours;

  /** 是有有车位，0没有 1有. */
  @Range(min=0,max=1)
  private int haveParking = 0;

  /** 是否有wifi，0没有 1有. */
  @Range(min=0,max=1)
  private int haveWifi = 0;

  /** 是否有包间， 0没有 1有. */
  @Range(min=0,max=1)
  private int havePrivateroom = 0;

  /** 是否能预订， 0不能 1能. */
  @Range(min=0,max=1)
  private int haveBooking = 0;

}
