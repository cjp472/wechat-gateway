package com.wangxiaobao.wechatgateway.utils.validate.groups;



import javax.validation.GroupSequence;

/**
 * 
  * @ClassName: GroupsB
  * @Description: 验证顺序不包含FirstValidate
  * @author Comsys-zt
  * @date 2016年1月20日 下午4:45:43
  *
 */
@GroupSequence({ GroupsB.FirstValidate.class, GroupsB.SecondValidate.class,GroupsB.ThirdValidate.class,
        GroupsB.FourthValidate.class,GroupsB.FifthValidate.class,GroupsB.SixthValidate.class,GroupsB.SevenValidate.class,
        GroupsB.EighthValidate.class, GroupsB.NinthValidate.class, GroupsB.TenthValidate.class, GroupsB.EleventhValidate.class, GroupsB.TwelfthValidate.class
})
public interface GroupsB {
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
