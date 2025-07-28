package com.yunfei.pay.controller;

import com.yunfei.api.dto.PayOrderDTO;
import com.yunfei.common.exception.BizIllegalException;
import com.yunfei.common.utils.BeanUtils;
import com.yunfei.pay.domain.dto.PayApplyDTO;
import com.yunfei.pay.domain.dto.PayOrderFormDTO;
import com.yunfei.pay.domain.po.PayOrder;
import com.yunfei.pay.domain.vo.PayOrderVO;
import com.yunfei.pay.enums.PayType;
import com.yunfei.pay.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Pay APIs")
@RestController
@RequestMapping("pay-orders")
@RequiredArgsConstructor
public class PayController {

    private final IPayOrderService payOrderService;

    @ApiOperation("create payment order")
    @PostMapping
    public String applyPayOrder(@RequestBody PayApplyDTO applyDTO){
        if(!PayType.BALANCE.equalsValue(applyDTO.getPayType())){
            throw new BizIllegalException("Sorry, only accept balance payment now!");
        }
        return payOrderService.applyPayOrder(applyDTO);
    }

    @ApiOperation("try pay using balance type")
    @ApiImplicitParam(value = "pay order id", name = "id")
    @PostMapping("{id}")
    public void tryPayOrderByBalance(@PathVariable("id") Long id, @RequestBody PayOrderFormDTO payOrderFormDTO){
        payOrderFormDTO.setId(id);
        payOrderService.tryPayOrderByBalance(payOrderFormDTO);
    }


    @ApiOperation("query payment order")
    @GetMapping
    public List<PayOrderVO> queryPayOrders(){
        return BeanUtils.copyList(payOrderService.list(), PayOrderVO.class);
    }


    @ApiOperation("query based on id")
    @GetMapping("/biz/{id}")
    public PayOrderDTO queryPayOrderByBizOrderNo(@PathVariable("id") Long id){
        PayOrder payOrder = payOrderService.lambdaQuery().eq(PayOrder::getBizOrderNo, id).one();
        return BeanUtils.copyBean(payOrder, PayOrderDTO.class);
    }
}
