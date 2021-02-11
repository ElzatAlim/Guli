package com.aili.educenter.service.impl;

import com.aili.baseservice.exceptionHandler.GuliException;
import com.aili.commonutils.JwtUtils;
import com.aili.commonutils.MD5;
import com.aili.educenter.entity.UcenterMember;
import com.aili.educenter.entity.vo.RegisterVo;
import com.aili.educenter.mapper.UcenterMemberMapper;
import com.aili.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-27
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

//    @Autowired
//    private RedisTemplate<String,String> redisTemplate;

    //登录方法
    @Override
    public String login(UcenterMember member) {

        String password = member.getPassword();
        String mobile = member.getMobile();

        if(StringUtils.isEmpty(password)||StringUtils.isEmpty(mobile)){
            throw new GuliException(20001,"登录失败");
        }

        //判断手机号是否正确
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        UcenterMember one = baseMapper.selectOne(wrapper);
        //判断
        if(one==null){
            throw new GuliException(20001,"用户不存在");
        }

        if(!MD5.encrypt(password).equals(one.getPassword())){
            throw new GuliException(20001,"密码有误");
        }

        if(one.getIsDisabled()){
            throw new GuliException(20001,"用户被限制登录");
        }

        //登陆成功
        String token = JwtUtils.getJwtToken(one.getId(), one.getNickname());
        return token;
    }

    //注册
    @Override
    public void register(RegisterVo register) {
      //获取注册时的数据
        String mobile = register.getMobile();
        String code = register.getCode();
        String nickname = register.getNickname();
        String password = register.getPassword();

        if(StringUtils.isEmpty(mobile)||StringUtils.isEmpty(code)||StringUtils.isEmpty(nickname)||StringUtils.isEmpty(password)){
            throw new GuliException(20001, "注册信息不能为空");
        }

        //判断验证码
        //获取Redis种的验证码
//        String rediscode = redisTemplate.opsForValue().get(mobile);
//        if(!code.equals(rediscode)){
//            throw new GuliException(20001, "验证码有误！");
//        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        Integer count = baseMapper.selectCount(wrapper);
        if(count>0){
            throw new GuliException(20001, "手机号已被注册！");
        }

        //添加到数据库
        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setIsDisabled(false);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("https://edu-aili.oss-cn-shanghai.aliyuncs.com/2021/01/16/0f953282e69a4eb19809a5d87896ceecfile.png");
        baseMapper.insert(member);



    }

    //根据Openid查询
    @Override
    public UcenterMember getOpenIdMember(String openid) {
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid", openid);
        UcenterMember member = baseMapper.selectOne(queryWrapper);

        return member;
    }

    //查询某一天的注册人数
    @Override
    public Integer countRegisterDay(String day) {
        int count = baseMapper.countRegisterDay(day);
        return count;
    }
}
