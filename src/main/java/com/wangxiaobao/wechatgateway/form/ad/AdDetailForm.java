package com.wangxiaobao.wechatgateway.form.ad;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@Data
public class AdDetailForm {

  private String detailId;

  /** 图片所属广告. */
  @NotEmpty(message = "图片所属广告必填")
  private String adId;

  private String detailName;

  /** 图片链接. */
  @NotEmpty(message = "图片链接必填")
  private String detailUrl;

  @NotEmpty(message = "七牛key必填")
  private String detailKey;

  /** 单位KB .*/
  @Range(min=1,max=1048576)
  private int detailSize;

  @Range(min=0,max=1)
  private int isneedwifi = 0;

}
