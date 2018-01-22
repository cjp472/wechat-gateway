package com.wangxiaobao.wechatgateway.entity.header;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ModulePermissions implements Serializable {
    private static final long serialVersionUID = 1974578396136883039L;
    private String moduleId;
    private String moduleCode;
    private String moduleName;
    private List<String> serviceMethods;
}