package com.wangxiaobao.wechatgateway.form.store;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @author Zhoutong
 * @version V1.0
 * @Title: StoreTimesForm
 * @Project wechat-gateway
 * @Package com.wangxiaobao.wechatgateway.form.store
 * @Description: 商家倒计时
 * @Copyright: Copyright (c) 2017  ALL RIGHTS RESERVED.
 * @Company:成都国盛天丰技术有限责任公司
 * @date 2018/1/19 10:37
 */
@Data
public class StoreTimesForm implements Serializable{

    private static final long serialVersionUID = 1495287735118363923L;

    //商家Account
    @NotBlank(message = "商家Account不能为空")
    private String merchantAccount;
    //品牌信息
    private String brandAccount;
    //是否开启倒计时 0否 1是
    private int isOpenTime = 0;
    //倒计时时间 默认:1分钟
    private int times = 1;
    //商家承诺
    private String promise;
}
