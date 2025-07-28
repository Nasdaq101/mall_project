package com.yunfei.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunfei.item.domain.dto.ItemDTO;
import com.yunfei.item.domain.dto.OrderDetailDTO;
import com.yunfei.item.domain.po.Item;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * item list
 * </p>
 */
public interface IItemService extends IService<Item> {

    void deductStock(List<OrderDetailDTO> items);

    List<ItemDTO> queryItemByIds(Collection<Long> ids);
}
