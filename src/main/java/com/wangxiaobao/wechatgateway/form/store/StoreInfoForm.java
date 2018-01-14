package com.wangxiaobao.wechatgateway.form.store;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Data
public class StoreInfoForm {

  private String storeId;

  /** 权限系统商家账号. */
  @NotEmpty(message = "商家账号必填")
  private String merchantAccount;

  /** 门店名称. */
  @NotEmpty(message = "门店名称必填")
  private String storeName;

  /** 门店省. */
  @NotEmpty(message = "门店省份必填")
  private String storeProvince;

  /** 门店市. */
  @NotEmpty(message = "门店城市必填")
  private String storeCity;

  /** 门店区. */
  @NotEmpty(message = "门店区域必填")
  private String storeDistrict;

  /** 门店地址. */
  @NotEmpty(message = "门店地址必填")
  private String storeAddress;

  /** 门店描述. */
  private String storeDescription;

  /** 门店电话. */
  @NotEmpty(message = "门店电话必填")
  private String storePhone;

  /** 门店营业时间. */
  private String storeOfficehours;

  /** 是有有车位，0没有 1有. */
  private int haveParking = 0;

  /** 是否有wifi，0没有 1有. */
  private int haveWifi = 0;

  /** 是否有包间， 0没有 1有. */
  private int havePrivateroom = 0;

  /** 是否能预订， 0不能 1能. */
  private int haveBooking = 0;

}
