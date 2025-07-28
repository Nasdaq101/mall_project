package com.yunfei.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunfei.trade.domain.po.OrderDetail;
import com.yunfei.trade.mapper.OrderDetailMapper;
import com.yunfei.trade.service.IOrderDetailService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * order detail impl
 * </p>
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
