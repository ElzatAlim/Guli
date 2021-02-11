package com.aili.eduorder.service;

import com.aili.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-03
 */
public interface PayLogService extends IService<PayLog> {

    //生成微信支付二维码接口
    Map createNative(String orderNo);

    //根据订单号  查询订单的支付状态
    Map<String, String> queryPayStatus(String orderNo);

    //支付表里面添加记录  同时 更新订单状态
    void updateOrderStatus(Map<String, String> map);
}
