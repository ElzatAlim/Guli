package com.aili.eduorder.controller;


import com.aili.commonutils.R;
import com.aili.eduorder.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-03
 */
@RestController
@RequestMapping("/eduorder/paylog")
@CrossOrigin
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    //生成微信支付二维码接口
    //参数是订单号
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable String orderNo){
        //返回 二维码url 和  其他信息
        Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    //查询支付状态
    //参数 ： 订单号
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map  =  payLogService.queryPayStatus(orderNo);
        if(map==null){
            return R.error().message("支付有错误！");
        }
        //不是null
        if(map.get("trade_state").equals("SUCCESS")){
            //支付表中加记录
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功！");
        }
        return R.error().message("正在支付中！");
    }


}

