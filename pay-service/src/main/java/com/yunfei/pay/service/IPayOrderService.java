package com.yunfei.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunfei.pay.domain.dto.PayApplyDTO;
import com.yunfei.pay.domain.dto.PayOrderFormDTO;
import com.yunfei.pay.domain.po.PayOrder;


/**
 * <p>
 * PAY ORDER SERVICE
 * </p>
 */
public interface IPayOrderService extends IService<PayOrder> {

    String applyPayOrder(PayApplyDTO applyDTO);

    void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO);
}
