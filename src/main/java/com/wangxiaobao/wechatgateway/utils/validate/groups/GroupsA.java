package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 *
  * @ClassName: GroupsA
  * @Description: 验证顺序包含所有验证
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:21
  *
 */
@GroupSequence({ GroupsA.FirstValidate.class, GroupsA.SecondValidate.class,GroupsA.ThirdValidate.class,
        GroupsA.FourthValidate.class,GroupsA.FifthValidate.class,GroupsA.SixthValidate.class,GroupsA.SevenValidate.class,
        GroupsA.EighthValidate.class, GroupsA.NinthValidate.class, GroupsA.TenthValidate.class, GroupsA.EleventhValidate.class, GroupsA.TwelfthValidate.class
})
public interface GroupsA {
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
