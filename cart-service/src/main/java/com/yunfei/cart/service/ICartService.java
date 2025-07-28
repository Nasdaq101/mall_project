package com.yunfei.cart.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunfei.cart.domain.dto.CartFormDTO;
import com.yunfei.cart.domain.po.Cart;
import com.yunfei.cart.domain.vo.CartVO;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 * order details
 * </p>
 */
public interface ICartService extends IService<Cart> {

    void addItem2Cart(CartFormDTO cartFormDTO);

    List<CartVO> queryMyCarts();

    void removeByItemIds(Collection<Long> itemIds);
}
