package com.yunfei.item.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunfei.item.domain.dto.OrderDetailDTO;
import com.yunfei.item.domain.po.Item;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * item mapper
 * </p>
 */
public interface ItemMapper extends BaseMapper<Item> {

    @Update("UPDATE item SET stock = stock - #{num} WHERE id = #{itemId}")
    void updateStock(OrderDetailDTO orderDetail);
}
