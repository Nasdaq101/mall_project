package com.yunfei.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunfei.common.exception.BizIllegalException;
import com.yunfei.common.utils.BeanUtils;
import com.yunfei.common.utils.UserContext;
import com.yunfei.domain.dto.PayApplyDTO;
import com.yunfei.domain.dto.PayOrderFormDTO;
import com.yunfei.domain.po.Order;
import com.yunfei.domain.po.PayOrder;
import com.yunfei.enums.PayStatus;
import com.yunfei.mapper.PayOrderMapper;
import com.yunfei.service.IOrderService;
import com.yunfei.service.IPayOrderService;
import com.yunfei.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class PayOrderServiceImpl extends ServiceImpl<PayOrderMapper, PayOrder> implements IPayOrderService {

    private final IUserService userService;

    private final IOrderService orderService;

    @Override
    public String applyPayOrder(PayApplyDTO applyDTO) {
        PayOrder payOrder = checkIdempotent(applyDTO);

        return payOrder.getId().toString();
    }

    @Override
    @Transactional
    public void tryPayOrderByBalance(PayOrderFormDTO payOrderFormDTO) {

        PayOrder po = getById(payOrderFormDTO.getId());

        if(!PayStatus.WAIT_BUYER_PAY.equalsValue(po.getStatus())){

            throw new BizIllegalException("交易已支付或关闭！");
        }

        userService.deductMoney(payOrderFormDTO.getPw(), po.getAmount());

        boolean success = markPayOrderSuccess(payOrderFormDTO.getId(), LocalDateTime.now());
        if (!success) {
            throw new BizIllegalException("交易已支付或关闭！");
        }

        Order order = new Order();
        order.setId(po.getBizOrderNo());
        order.setStatus(2);
        order.setPayTime(LocalDateTime.now());
        orderService.updateById(order);
    }

    public boolean markPayOrderSuccess(Long id, LocalDateTime successTime) {
        return lambdaUpdate()
                .set(PayOrder::getStatus, PayStatus.TRADE_SUCCESS.getValue())
                .set(PayOrder::getPaySuccessTime, successTime)
                .eq(PayOrder::getId, id)

                .in(PayOrder::getStatus, PayStatus.NOT_COMMIT.getValue(), PayStatus.WAIT_BUYER_PAY.getValue())
                .update();
    }


    private PayOrder checkIdempotent(PayApplyDTO applyDTO) {

        PayOrder oldOrder = queryByBizOrderNo(applyDTO.getBizOrderNo());

        if (oldOrder == null) {

            PayOrder payOrder = buildPayOrder(applyDTO);
            payOrder.setPayOrderNo(IdWorker.getId());
            save(payOrder);
            return payOrder;
        }
        // 3.old order exists, check if payment successful
        if (PayStatus.TRADE_SUCCESS.equalsValue(oldOrder.getStatus())) {
            // success, throw exception
            throw new BizIllegalException("order paid！");
        }
        // 4.old order exists, check if payment closed
        if (PayStatus.TRADE_CLOSED.equalsValue(oldOrder.getStatus())) {
            // closed, throw exception
            throw new BizIllegalException("order closed");
        }
        // check if channel consistent
        if (!StringUtils.equals(oldOrder.getPayChannelCode(), applyDTO.getPayChannelCode())) {
            // if not consistent, renew data, renew order
            PayOrder payOrder = buildPayOrder(applyDTO);
            payOrder.setId(oldOrder.getId());
            payOrder.setQrCodeUrl("");
            updateById(payOrder);
            payOrder.setPayOrderNo(oldOrder.getPayOrderNo());
            return payOrder;
        }
        // 6.old order exists, and not paid/not submitted, and consistent, return data
        return oldOrder;
    }

    private PayOrder buildPayOrder(PayApplyDTO payApplyDTO) {
        PayOrder payOrder = BeanUtils.toBean(payApplyDTO, PayOrder.class);
        // initialize data
        payOrder.setPayOverTime(LocalDateTime.now().plusMinutes(120L));
        payOrder.setStatus(PayStatus.WAIT_BUYER_PAY.getValue());
        payOrder.setBizUserId(UserContext.getUser());
        return payOrder;
    }
    public PayOrder queryByBizOrderNo(Long bizOrderNo) {
        return lambdaQuery()
                .eq(PayOrder::getBizOrderNo, bizOrderNo)
                .one();
    }
}
