package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 *
  * @ClassName: GroupsE
  * @Description: 验证顺序包含所有验证
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:21
  *
 */
@GroupSequence({ GroupsE.FirstValidate.class, GroupsE.SecondValidate.class,GroupsE.ThirdValidate.class,
        GroupsE.FourthValidate.class,GroupsE.FifthValidate.class,GroupsE.SixthValidate.class,GroupsE.SevenValidate.class,
        GroupsE.EighthValidate.class, GroupsE.NinthValidate.class, GroupsE.TenthValidate.class, GroupsE.EleventhValidate.class, GroupsE.TwelfthValidate.class
})
public interface GroupsE {
    interface FirstValidate{}
    interface SecondValidate{}
    interface ThirdValidate{}
    interface FourthValidate{}
    interface FifthValidate{}
    interface SixthValidate{}
    interface SevenValidate{}
    interface EighthValidate{}
    interface NinthValidate{}
    interface TenthValidate{}
    interface EleventhValidate{}
    interface TwelfthValidate{}
}
