package com.yunfei.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunfei.domain.dto.OrderDetailDTO;
import com.yunfei.domain.po.Item;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * items Mapper
 * </p>
 */
public interface ItemMapper extends BaseMapper<Item> {

    @Update("UPDATE item SET stock = stock - #{num} WHERE id = #{itemId}")
    void updateStock(OrderDetailDTO orderDetail);
}
