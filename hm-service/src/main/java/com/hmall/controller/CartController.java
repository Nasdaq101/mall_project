package com.hmall.controller;


import com.hmall.domain.dto.CartFormDTO;
import com.hmall.domain.po.Cart;
import com.hmall.domain.vo.CartVO;
import com.hmall.service.ICartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "CART CONTROLLER")
@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {
    private final ICartService cartService;

    @ApiOperation("add to cart")
    @PostMapping
    public void addItem2Cart(@Valid @RequestBody CartFormDTO cartFormDTO){
        cartService.addItem2Cart(cartFormDTO);
    }

    @ApiOperation("update cart info")
    @PutMapping
    public void updateCart(@RequestBody Cart cart){
        cartService.updateById(cart);
    }

    @ApiOperation("delete item from cart")
    @DeleteMapping("{id}")
    public void deleteCartItem(@Param ("cartItem_id")@PathVariable("id") Long id){
        cartService.removeById(id);
    }

    @ApiOperation("search item in cart")
    @GetMapping
    public List<CartVO> queryMyCarts(){
        return cartService.queryMyCarts();
    }
    @ApiOperation("batch delete items from cart")
    @ApiImplicitParam(name = "ids", value = "cartItem_id batch")
    @DeleteMapping
    public void deleteCartItemByIds(@RequestParam("ids") List<Long> ids){
        cartService.removeByItemIds(ids);
    }
}
