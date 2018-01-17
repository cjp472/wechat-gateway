package com.wangxiaobao.wechatgateway.entity.store;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@Entity
@Data
@DynamicUpdate
public class StoreInfo {

  @Id
  private String storeId;

  /** 权限系统商家账号. */
  private String merchantAccount;

  /** 权限系统商家ID. */
  private String merchantId;

  /** 门店名称. */
  private String storeName;

  /** 门店省. */
  private String storeProvince;

  /** 门店市. */
  private String storeCity;

  /** 门店区. */
  private String storeDistrict;

  /** 门店地址. */
  private String storeAddress;

  /** 门店坐标. */
  private String storeLocation;

  /** 门店logo. */
  private String storeLogo;

  /** 门店描述. */
  private String storeDescription;

  /** 门店电话. */
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

  /** 门店菜品图片地址. */
  private String storeMenu;

  /** 门店图片地址. */
  private String storePhoto;

  private Date createTime;
  private Date updateTime;
}
