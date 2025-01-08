package com.hmall.cart.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.cart.config.CartProPerties;
import com.hmall.cart.domain.dto.CartFormDTO;
import com.hmall.cart.domain.po.Cart;
import com.hmall.cart.domain.vo.CartVO;
import com.hmall.cart.mapper.CartMapper;
import com.hmall.cart.service.ICartService;
import com.hmall.common.exception.BizIllegalException;
import com.hmall.common.utils.BeanUtils;
import com.hmall.common.utils.CollUtils;
import com.hmall.common.utils.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * order details
 * </p>
 */
@Service
@RequiredArgsConstructor
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

    //private final RestTemplate restTemplate;
    //private final DiscoveryClient discoveryClient;
    private final ItemClient itemClient;
    private final CartProPerties cartProPerties;

    @Override
    public void addItem2Cart(CartFormDTO cartFormDTO) {
        // 1.get current user
        Long userId = UserContext.getUser();

        // 2.check if exists?
        if(checkItemExists(cartFormDTO.getItemId(), userId)){
            // 2.1.yes?update number
            baseMapper.updateNum(cartFormDTO.getItemId(), userId);
            return;
        }
        // 2.2.no? check if already full
        checkCartsFull(userId);

        // 3.new cart
        // 3.1.copy bean
        Cart cart = BeanUtils.copyBean(cartFormDTO, Cart.class);
        // 3.2.save current user
        cart.setUserId(userId);
        // 3.3.save to sql
        save(cart);
    }

    @Override
    public List<CartVO> queryMyCarts() {
        // 1.query shopping cart
        List<Cart> carts = lambdaQuery().eq(Cart::getUserId, UserContext.getUser()).list();
        if (CollUtils.isEmpty(carts)) {
            return CollUtils.emptyList();
        }

        // 2.bean - vo
        List<CartVO> vos = BeanUtils.copyList(carts, CartVO.class);

        // 3.vo item info
        handleCartItems(vos);

        return vos;
    }

    private void handleCartItems(List<CartVO> vos) {
        // 1.get item ids
        Set<Long> itemIds = vos.stream().map(CartVO::getItemId).collect(Collectors.toSet());
        // 2.query item
        //restTemplate--httpclient + nacos -- registration center get functional api url, multiple nginx
        /*List<ServiceInstance> instances = discoveryClient.getInstances("item-service");
        if (CollUtil.isEmpty(instances)){
            return;
        }
        ServiceInstance instance = instances.get(RandomUtil.randomInt(instances.size()));

        //use resttemplate, httpclient - item service - item info
        ResponseEntity<List<ItemDTO>> response = restTemplate.exchange(
                instance.getUri()+"/items?ids={ids}",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ItemDTO>>() {
                },
                Map.of("ids", CollUtil.join(itemIds, ","))
        );
        // serialize
        if (!response.getStatusCode().is2xxSuccessful()){
            return;
        }
        List<ItemDTO> items = response.getBody();*/
        //openfeign - restTemplate + nacos
        List<ItemDTO> items = itemClient.queryItemByIds(itemIds);
        if (CollUtils.isEmpty(items)) {
            return;
        }
        Map<Long, ItemDTO> itemMap = items.stream().collect(Collectors.toMap(ItemDTO::getId, Function.identity()));
        for (CartVO v : vos) {
            ItemDTO item = itemMap.get(v.getItemId());
            if (item == null) {
                continue;
            }
            v.setNewPrice(item.getPrice());
            v.setStatus(item.getStatus());
            v.setStock(item.getStock());
        }
    }

    @Override
    @Transactional
    public void removeByItemIds(Collection<Long> itemIds) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<Cart>();
        queryWrapper.lambda()
                .eq(Cart::getUserId, UserContext.getUser())
                .in(Cart::getItemId, itemIds);
        remove(queryWrapper);
    }

    private void checkCartsFull(Long userId) {
        int count = lambdaQuery().eq(Cart::getUserId, userId).count();
        if (count >= cartProPerties.getMaxItems()) {
            throw new BizIllegalException(StrUtil.format("max item number is {}", cartProPerties.getMaxItems()));
        }
    }

    private boolean checkItemExists(Long itemId, Long userId) {
        int count = lambdaQuery()
                .eq(Cart::getUserId, userId)
                .eq(Cart::getItemId, itemId)
                .count();
        return count > 0;
    }
}
