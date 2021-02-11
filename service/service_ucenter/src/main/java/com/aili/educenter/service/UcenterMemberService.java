package com.aili.educenter.service;

import com.aili.educenter.entity.UcenterMember;
import com.aili.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-27
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo register);


    //根据Openid查询用户
    UcenterMember getOpenIdMember(String openid);

    //查询某一天的注册人数
    Integer countRegisterDay(String day);
}
