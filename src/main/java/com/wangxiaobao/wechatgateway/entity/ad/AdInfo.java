package com.wangxiaobao.wechatgateway.entity.ad;

import com.wangxiaobao.wechatgateway.enums.ad.AdTypeEnum;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

/**
 * Created by halleyzhang on 2018/1/18.
 */
@Data
@Entity
@DynamicUpdate
public class AdInfo {

  @Id
  private String adId;

  private String adName;

  private Integer adType = AdTypeEnum.PICTURE.getCode();

  private String storeId;

  private Date createTime;
  private Date updateTime;

}
