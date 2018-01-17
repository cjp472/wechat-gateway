package com.wangxiaobao.wechatgateway.VO.store;

import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import java.util.List;
import lombok.Data;

/**
 * Created by halleyzhang on 2018/1/17.
 */
@Data
public class BrandVO {

  private BrandInfo brandInfo;

  private List<StoreDistanceVO> stores;

}
