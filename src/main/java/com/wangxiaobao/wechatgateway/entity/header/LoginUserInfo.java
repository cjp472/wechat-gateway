package com.wangxiaobao.wechatgateway.entity.header;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class LoginUserInfo implements Serializable {
    private static final long serialVersionUID = 75040487623644556L;
    private String userId;
    private String userAccount;
    private String userType;
    private String merchantAccount;
    private String organizationAccount;
    private String userName;
    private String merchantName;
    private String organizationName;
    private String currentModuleId;
    private Map<String, ModulePermissions> modulePermissionsMap;
    private List<String> serviceMethods;
    private String loginTime;
    private String groupAccount;
    private String groupName;
    private String brandAccount;
    private String brandName;
    private String areaAccount;
    private String areaName;
    private List<Merchant> merchant;
    @Data
    public static class Merchant{
        private String merchantId;
        private String merchantName;
        private String merchantAccount;
        private String orgId;
        private String isOpen;
    }
}