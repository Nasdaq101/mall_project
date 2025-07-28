package com.yunfei.service.impl;

import com.yunfei.domain.po.OrderDetail;
import com.yunfei.mapper.OrderDetailMapper;
import com.yunfei.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
