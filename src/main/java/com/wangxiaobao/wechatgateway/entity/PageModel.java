package com.wangxiaobao.wechatgateway.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2017成都国盛天丰网络科技有限公司. All rights reserved.
 *
 * @project: pay-admin
 * @package: com.source3g.pay.admin.common
 * @description: 分页模型
 * @author: gaozitian
 * @date: 2017/6/19 13:43
 * @version: V1.0
 */
@Data
public class PageModel<T> implements Serializable {


    public static final String DEF_PAGE_NUM = "1";

    public static final String DEF_PAGE_SIZE = "10";

    private Long count = 0L;

    private Integer pageNum = 1;

    private Integer pageSize = 10;

    @JsonIgnore
    private Map<String, Object> params;

    private List<T> datas;
}
