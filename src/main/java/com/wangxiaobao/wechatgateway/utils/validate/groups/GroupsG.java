package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 *
  * @ClassName: GroupsG
  * @Description: 验证顺序包含所有验证
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:21
  *
 */
@GroupSequence({ GroupsG.FirstValidate.class, GroupsG.SecondValidate.class,GroupsG.ThirdValidate.class,
        GroupsG.FourthValidate.class,GroupsG.FifthValidate.class,GroupsG.SixthValidate.class,GroupsG.SevenValidate.class,
        GroupsG.EighthValidate.class, GroupsG.NinthValidate.class, GroupsG.TenthValidate.class, GroupsG.EleventhValidate.class, GroupsG.TwelfthValidate.class
})
public interface GroupsG {
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