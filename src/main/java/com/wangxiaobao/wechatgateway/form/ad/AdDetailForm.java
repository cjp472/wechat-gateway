package com.wangxiaobao.wechatgateway.form.ad;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

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

}
