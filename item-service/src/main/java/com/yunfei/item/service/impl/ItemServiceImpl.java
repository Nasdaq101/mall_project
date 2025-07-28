package com.yunfei.item.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunfei.common.exception.BizIllegalException;
import com.yunfei.common.utils.BeanUtils;
import com.yunfei.item.domain.dto.ItemDTO;
import com.yunfei.item.domain.dto.OrderDetailDTO;
import com.yunfei.item.domain.po.Item;
import com.yunfei.item.mapper.ItemMapper;
import com.yunfei.item.service.IItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * item list
 * </p>
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Override
    @Transactional
    public void deductStock(List<OrderDetailDTO> items) {
        String sqlStatement = "mapper.com.yunfei.item.ItemMapper.updateStock";
        boolean r = false;
        try {
            r = executeBatch(items, (sqlSession, entity) -> sqlSession.update(sqlStatement, entity));
        } catch (Exception e) {
            throw new BizIllegalException("update error, there might be no item!", e);
        }
        if (!r) {
            throw new BizIllegalException("no item！");
        }
    }

    @Override
    public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
        return BeanUtils.copyList(listByIds(ids), ItemDTO.class);
    }
}
