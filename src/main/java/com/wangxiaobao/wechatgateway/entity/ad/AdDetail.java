package com.wangxiaobao.wechatgateway.entity.ad;

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
public class AdDetail {

  @Id
  private String detailId;

  private String adId;

  private String detailName;

  private String detailUrl;

  private Date createTime;
  private Date updateTime;

}
