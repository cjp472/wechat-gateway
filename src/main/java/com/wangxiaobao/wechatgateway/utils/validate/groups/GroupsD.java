package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 *
  * @ClassName: GroupsD
  * @Description: 验证顺序包含所有验证
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:21
  *
 */
@GroupSequence({ GroupsD.FirstValidate.class, GroupsD.SecondValidate.class,GroupsD.ThirdValidate.class,
        GroupsD.FourthValidate.class,GroupsD.FifthValidate.class,GroupsD.SixthValidate.class,GroupsD.SevenValidate.class,
        GroupsD.EighthValidate.class, GroupsD.NinthValidate.class, GroupsD.TenthValidate.class, GroupsD.EleventhValidate.class, GroupsD.TwelfthValidate.class
})
public interface GroupsD {
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
