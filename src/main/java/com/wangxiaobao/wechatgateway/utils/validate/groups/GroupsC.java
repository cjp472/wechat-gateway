package com.wangxiaobao.wechatgateway.utils.validate.groups;


import javax.validation.GroupSequence;

/**
 * 
  * @ClassName: GroupsC
  * @Description: 验证顺序不包含FirstValidate,SecondValidate
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:46:09
  *
 */
@GroupSequence({ GroupsC.FirstValidate.class, GroupsC.SecondValidate.class,GroupsC.ThirdValidate.class,
        GroupsC.FourthValidate.class,GroupsC.FifthValidate.class,GroupsC.SixthValidate.class,GroupsC.SevenValidate.class,
        GroupsC.EighthValidate.class, GroupsC.NinthValidate.class, GroupsC.TenthValidate.class, GroupsC.EleventhValidate.class, GroupsC.TwelfthValidate.class
})
public interface GroupsC {
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
