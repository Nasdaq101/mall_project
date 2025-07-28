package com.yunfei.trade.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunfei.api.client.CartClient;
import com.yunfei.api.client.ItemClient;
import com.yunfei.api.dto.ItemDTO;
import com.yunfei.api.dto.OrderDetailDTO;
import com.yunfei.common.exception.BadRequestException;
import com.yunfei.common.utils.UserContext;

import com.yunfei.trade.constants.MQConstants;
import com.yunfei.trade.domain.dto.OrderFormDTO;
import com.yunfei.trade.domain.po.Order;
import com.yunfei.trade.domain.po.OrderDetail;
import com.yunfei.trade.mapper.OrderMapper;
import com.yunfei.trade.service.IOrderDetailService;
import com.yunfei.trade.service.IOrderService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * order service impl
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ItemClient itemClient;
    private final IOrderDetailService detailService;
    private final CartClient cartClient;
    private final RabbitTemplate rabbitTemplate;

    @Override
    @GlobalTransactional
    public Long createOrder(OrderFormDTO orderFormDTO) {
        Order order = new Order();
        // 1.1.get items
        List<OrderDetailDTO> detailDTOS = orderFormDTO.getDetails();
        // 1.2.get item id and number (map)
        Map<Long, Integer> itemNumMap = detailDTOS.stream()
                .collect(Collectors.toMap(OrderDetailDTO::getItemId, OrderDetailDTO::getNum));
        Set<Long> itemIds = itemNumMap.keySet();
        // 1.3.query item
        List<ItemDTO> items = itemClient.queryItemByIds(itemIds);
        if (items == null || items.size() < itemIds.size()) {
            throw new BadRequestException("item not found");
        }
        // 1.4.calculate totalfee
        int total = 0;
        for (ItemDTO item : items) {
            total += item.getPrice() * itemNumMap.get(item.getId());
        }
        order.setTotalFee(total);
        // 1.5.other properties
        order.setPaymentType(orderFormDTO.getPaymentType());
        order.setUserId(UserContext.getUser());
        order.setStatus(1);
        // 1.6.write order into order database
        save(order);

        // 2.save order details
        List<OrderDetail> details = buildDetails(order.getId(), items, itemNumMap);
        detailService.saveBatch(details);

        // 3.clear cart items
        cartClient.deleteCartItemByIds(itemIds);

        // 4.deduct stock
        try {
            itemClient.deductStock(detailDTOS);
        } catch (Exception e) {
            throw new RuntimeException("not enough stock！");
        }

        //delay_msg
        rabbitTemplate.convertAndSend(
                MQConstants.DELAY_EXCHANGE_NAME,
                MQConstants.DELAY_ORDER_KEY,
                order.getId(),
                message -> {
                    message.getMessageProperties().setDelay(10000);
                    return message;
                });

        return order.getId();
    }

    @Override
    public void markOrderPaySuccess(Long orderId) {
        Order old = getById(orderId);
        // 2.check order status
        if (old == null || old.getStatus() != 1) {
            return;
        }
        // 3.update order
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(2);
        order.setPayTime(LocalDateTime.now());
        updateById(order);
    }

    private List<OrderDetail> buildDetails(Long orderId, List<ItemDTO> items, Map<Long, Integer> numMap) {
        List<OrderDetail> details = new ArrayList<>(items.size());
        for (ItemDTO item : items) {
            OrderDetail detail = new OrderDetail();
            detail.setName(item.getName());
            detail.setSpec(item.getSpec());
            detail.setPrice(item.getPrice());
            detail.setNum(numMap.get(item.getId()));
            detail.setItemId(item.getId());
            detail.setImage(item.getImage());
            detail.setOrderId(orderId);
            details.add(detail);
        }
        return details;
    }

    @Override
    public void cancelOrder(Long orderId) {
        // todo
    }
}
