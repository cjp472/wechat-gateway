package com.wangxiaobao.wechatgateway.form.ad;

import com.wangxiaobao.wechatgateway.enums.ad.AdTypeEnum;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

/**
 * Created by halleyzhang on 2018/1/19.
 */
@Data
public class AdForm {

  private String adId;

  /** 广告名. */
  @NotEmpty(message = "广告名必填")
  private String adName;

  /** 广告类型.  0图片  1视频*/
  @Range(min=0,max=1)
  private int adType = AdTypeEnum.PICTURE.getCode();

  @NotEmpty(message = "商家账号不为空")
  private String merchantAccount;

}
