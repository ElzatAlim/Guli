package com.aili.educenter.controller;


import com.aili.commonutils.JwtUtils;
import com.aili.commonutils.R;
import com.aili.commonutils.ordervo.UcenterMemberOrder;
import com.aili.educenter.entity.UcenterMember;
import com.aili.educenter.entity.vo.RegisterVo;
import com.aili.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-01-27
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService memberService;


    //登录
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember member){
        String token = memberService.login(member);
        return R.ok().data("token",token);
    }


    //注册
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo register){
        memberService.register(register);
        return R.ok();
    }

    //根据Token获取用户信息
    @GetMapping("getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用JWT工具 获取用户信息
        String id = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember member = memberService.getById(id);
        if(member==null){
            member=new UcenterMember();
            member.setId("");
            member.setAvatar("");
        }
        return R.ok().data("userInfo",member);
    }

    //根据用户Id获取用户信息
    @PostMapping("getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){

        UcenterMember member = memberService.getById(id);
        UcenterMemberOrder user = new UcenterMemberOrder();
        BeanUtils.copyProperties(member,user);
        return user;
    }

    //查询某一天的注册人数
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable String day){
        int count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister",count);
    }


}

