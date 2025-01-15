package com.hmall.trade.controller;

import com.hmall.common.utils.BeanUtils;

import com.hmall.trade.domain.dto.OrderFormDTO;
import com.hmall.trade.domain.vo.OrderVO;
import com.hmall.trade.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

@Api(tags = "order mng api")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final IOrderService orderService;

    @ApiOperation("query order by id")
    @GetMapping("{id}")
    public OrderVO queryOrderById(@Param ("order id")@PathVariable("id") Long orderId) {
        return BeanUtils.copyBean(orderService.getById(orderId), OrderVO.class);
    }

    @ApiOperation("create order")
    @PostMapping
    public Long createOrder(@RequestBody OrderFormDTO orderFormDTO){
        return orderService.createOrder(orderFormDTO);
    }

    @ApiOperation("mark order status as paid successfully")
    @ApiImplicitParam(name = "orderId", value = "order id", paramType = "path")
    @PutMapping("/{orderId}")
    public void markOrderPaySuccess(@PathVariable("orderId") Long orderId) {
        orderService.markOrderPaySuccess(orderId);
    }
}
