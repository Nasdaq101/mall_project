package com.yunfei.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunfei.common.exception.BizIllegalException;
import com.yunfei.common.utils.BeanUtils;
import com.yunfei.domain.dto.ItemDTO;
import com.yunfei.domain.dto.OrderDetailDTO;
import com.yunfei.domain.po.Item;
import com.yunfei.mapper.ItemMapper;
import com.yunfei.service.IItemService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Override
    public void deductStock(List<OrderDetailDTO> items) {
        String sqlStatement = "com.mapper.yunfei.ItemMapper.updateStock";
        boolean r = false;
        try {
            r = executeBatch(items, (sqlSession, entity) -> sqlSession.update(sqlStatement, entity));
        } catch (Exception e) {
            throw new BizIllegalException("Error! stock might be empty!", e);
        }
        if (!r) {
            throw new BizIllegalException("stock empty！");
        }
    }

    @Override
    public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
        return BeanUtils.copyList(listByIds(ids), ItemDTO.class);
    }
}
