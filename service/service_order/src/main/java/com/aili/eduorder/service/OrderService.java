package com.aili.eduorder.service;

import com.aili.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author ElzatAlim
 * @since 2021-02-03
 */
public interface OrderService extends IService<Order> {

    //生成订单
    String createOrders(String courseId, String id);
}
