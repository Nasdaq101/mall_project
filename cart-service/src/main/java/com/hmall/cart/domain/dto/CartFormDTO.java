package com.hmall.cart.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "new item entity in shopping cart")
public class CartFormDTO {
    @ApiModelProperty("item id")
    private Long itemId;
    @ApiModelProperty("item name")
    private String name;
    @ApiModelProperty("item spec")
    private String spec;
    @ApiModelProperty("price, cent")
    private Integer price;
    @ApiModelProperty("item image")
    private String image;
}
