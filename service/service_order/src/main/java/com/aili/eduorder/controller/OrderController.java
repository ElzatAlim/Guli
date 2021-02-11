package com.aili.eduorder.controller;


import com.aili.commonutils.JwtUtils;
import com.aili.commonutils.R;
import com.aili.eduorder.entity.Order;
import com.aili.eduorder.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Autowired
    private OrderService orderService;

    //生成订单方法
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        //用户ID
        String id = JwtUtils.getMemberIdByJwtToken(request);
        //创建订单 返回订单号
        String orderId  = orderService.createOrders(courseId,id);
        return R.ok().data("orderId",orderId);
    }

    //2 根据订单号查询订单信息
    @GetMapping("getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    //根据用户ID 和 课程信息  获取 订单信息
    @PostMapping("getUserOrders/{courseId}")
    public R getUserOrders(@PathVariable String courseId, HttpServletRequest request){
        //用户ID
        String userId = JwtUtils.getMemberIdByJwtToken(request);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id", userId);
        wrapper.eq("course_id", courseId);
        wrapper.eq("status", 1);
        Order order = orderService.getOne(wrapper);
        if(order!=null){
            return R.ok().data("buyer",true);
        }
        return R.ok().data("buyer",false);
    }


}

