package com.wangxiaobao.wechatgateway.entity.header;

import lombok.Data;

import java.util.List;

/**
 * @author Zhoutong
 * @version V1.0
 * @Title: PlateformOrgUserInfo
 * @Project wechat-user
 * @Package com.wangxiaobao.wechatuser.dto.user
 * @Description: 平台和品牌用户信息
 * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
 * @Company:成都国盛天丰技术有限责任公司
 * @date 2018/1/15 20:21
 */
@Data
public class PlatformOrgUserInfo {

    private String orgUserId;
    private String orgId;
    private String organizationAccount;
    private String orgName;
    private String orgUnionId;
    private String orgOpenId;
    private String orgAppId;
    private String orgOpenPlatformId;
    private String orgAppType;
    private String nickName;
    private int gender;
    private String avatarUrl;
    private String platformUserId;
    private String platformUnionId;
    private String platformOpenId;
    private String platformAppId;
    private String platformOpenPlateformId;
    private String platformAppType;
    private List<Merchant> merchant;
    @Data
    public class Merchant{
        private String merchantId;
        private String merchantName;
        private String merchantAccount;
        private String orgId;
        private String isOpen;
    }
}
