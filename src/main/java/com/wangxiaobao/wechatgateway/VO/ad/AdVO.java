package com.wangxiaobao.wechatgateway.VO.ad;

import com.wangxiaobao.wechatgateway.entity.ad.AdDetail;
import com.wangxiaobao.wechatgateway.entity.ad.AdInfo;
import java.util.List;
import lombok.Data;

/**
 * Created by halleyzhang on 2018/1/21.
 */
@Data
public class AdVO {
  private AdInfo adInfo;
  private List<AdDetail> adDetails;
}
