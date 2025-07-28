package com.yunfei.item.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunfei.common.domain.PageDTO;
import com.yunfei.common.domain.PageQuery;
import com.yunfei.common.utils.BeanUtils;
import com.yunfei.item.domain.dto.ItemDTO;
import com.yunfei.item.domain.dto.OrderDetailDTO;
import com.yunfei.item.domain.po.Item;
import com.yunfei.item.service.IItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "item controller api")
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {

    private final IItemService itemService;

    @ApiOperation("page query")
    @GetMapping("/page")
    public PageDTO<ItemDTO> queryItemByPage(PageQuery query) {
        Page<Item> result = itemService.page(query.toMpPage("update_time", false));
        return PageDTO.of(result, ItemDTO.class);
    }

    @ApiOperation("batch query by ids")
    @GetMapping
    public List<ItemDTO> queryItemByIds(@RequestParam("ids") List<Long> ids){
        return itemService.queryItemByIds(ids);
    }

    @ApiOperation("query by id")
    @GetMapping("{id}")
    public ItemDTO queryItemById(@PathVariable("id") Long id) {
        return BeanUtils.copyBean(itemService.getById(id), ItemDTO.class);
    }

    @ApiOperation("new item")
    @PostMapping
    public void saveItem(@RequestBody ItemDTO item) {
        itemService.save(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("update item status")
    @PutMapping("/status/{id}/{status}")
    public void updateItemStatus(@PathVariable("id") Long id, @PathVariable("status") Integer status){
        Item item = new Item();
        item.setId(id);
        item.setStatus(status);
        itemService.updateById(item);
    }

    @ApiOperation("update item")
    @PutMapping
    public void updateItem(@RequestBody ItemDTO item) {
        item.setStatus(null);
        itemService.updateById(BeanUtils.copyBean(item, Item.class));
    }

    @ApiOperation("delete item by id")
    @DeleteMapping("{id}")
    public void deleteItemById(@PathVariable("id") Long id) {
        itemService.removeById(id);
    }

    @ApiOperation("batch deduct")
    @PutMapping("/stock/deduct")
    public void deductStock(@RequestBody List<OrderDetailDTO> items){
        itemService.deductStock(items);
    }
}
