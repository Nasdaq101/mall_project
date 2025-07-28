package com.yunfei.trade.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunfei.trade.domain.dto.OrderFormDTO;
import com.yunfei.trade.domain.po.Order;


/**
 *  service
 */
public interface IOrderService extends IService<Order> {

    Long createOrder(OrderFormDTO orderFormDTO);

    void markOrderPaySuccess(Long orderId);

    void cancelOrder(Long orderId);
}
