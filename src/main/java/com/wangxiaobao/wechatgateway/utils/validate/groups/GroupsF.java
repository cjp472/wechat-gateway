package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 *
  * @ClassName: GroupsF
  * @Description: 验证顺序包含所有验证
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:21
  *
 */
@GroupSequence({ GroupsF.FirstValidate.class, GroupsF.SecondValidate.class,GroupsF.ThirdValidate.class,
        GroupsF.FourthValidate.class,GroupsF.FifthValidate.class,GroupsF.SixthValidate.class,GroupsF.SevenValidate.class,
        GroupsF.EighthValidate.class, GroupsF.NinthValidate.class, GroupsF.TenthValidate.class, GroupsF.EleventhValidate.class, GroupsF.TwelfthValidate.class
})
public interface GroupsF {
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
