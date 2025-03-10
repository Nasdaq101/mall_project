package com.hmall.controller;

import com.hmall.common.exception.BizIllegalException;
import com.hmall.domain.dto.PayApplyDTO;
import com.hmall.domain.dto.PayOrderFormDTO;
import com.hmall.enums.PayType;
import com.hmall.service.IPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "pay controller")
@RestController
@RequestMapping("pay-orders")
@RequiredArgsConstructor
public class PayController {

    private final IPayOrderService payOrderService;

    @ApiOperation("generate payment order")
    @PostMapping
    public String applyPayOrder(@RequestBody PayApplyDTO applyDTO){
        if(!PayType.BALANCE.equalsValue(applyDTO.getPayType())){
            throw new BizIllegalException("Soory, only balance payment available");
        }
        return payOrderService.applyPayOrder(applyDTO);
    }

    @ApiOperation("try balance payment")
    @ApiImplicitParam(value = "payOrder_id", name = "id")
    @PostMapping("{id}")
    public void tryPayOrderByBalance(@PathVariable("id") Long id, @RequestBody PayOrderFormDTO payOrderFormDTO){
        payOrderFormDTO.setId(id);
        payOrderService.tryPayOrderByBalance(payOrderFormDTO);
    }
}
