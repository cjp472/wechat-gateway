package com.wangxiaobao.wechatgateway.dto.wxopenplatfrommerchantinfo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

import lombok.Data;
/**
  * @ProjectName: wechatgateway 
  * @PackageName: com.wangxiaobao.wechatgateway.dto.wxopenplatfrommerchantinfo 
  * @ClassName: WxOpenPlatformMerchantGongzhonghaoInfo
  * @Description: TODO公众号授权列表查询结果
  * @Copyright: Copyright (c) 2018  ALL RIGHTS RESERVED.
  * @Company:成都国胜天丰技术有限责任公司
  * @author liping_max
  * @date 2018年3月19日 上午11:25:59
 */
@JsonSerialize(include=Inclusion.ALWAYS)
@Data
public class WxOpenPlatformMerchantGongzhonghaoInfo {
	private String wxOpenPlatformId;
	private String wxAppid;
	private java.util.Date createDate;
	private String authType;
	private String organizationAccount;
	private String nickName;
	private String brandName;
	private String openPlatformName;
	private String status;
}
