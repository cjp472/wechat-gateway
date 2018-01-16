package com.wangxiaobao.wechatgateway.form.store;

import java.util.Date;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by halleyzhang on 2018/1/16.
 */
@Data
public class BrandInfoForm {

  private String brandId;

  /** 权限系统品牌ID. */
  @NotEmpty(message = "品牌权限系统ID必填")
  private String orgId;

  /** 权限系统品牌账号. */
  @NotEmpty(message = "品牌权限系统账号必填")
  private String orgAccount;

  /** 品牌logo url. */
  @NotEmpty(message = "品牌logoUrl必填")
  private String logoUrl;

}
