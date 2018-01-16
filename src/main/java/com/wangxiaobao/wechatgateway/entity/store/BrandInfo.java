package com.wangxiaobao.wechatgateway.entity.store;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@Entity
@Data
@DynamicUpdate
public class BrandInfo {

  @Id
  private String brandId;

  /** 权限系统品牌ID. */
  private String orgId;

  /** 权限系统品牌账号. */
  private String orgAccount;

  /** 品牌logo url. */
  private String logoUrl;

  private Date createTime;
  private Date updateTime;

}
