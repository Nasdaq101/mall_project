package com.yunfei.cart.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * order details
 * </p>
 */
@Data
@ApiModel(description = "shopping cart VO entity")
public class CartVO {
    @ApiModelProperty("shopping cart id ")
    private Long id;
    @ApiModelProperty("sku item id")
    private Long itemId;
    @ApiModelProperty("amount")
    private Integer num;
    @ApiModelProperty("name")
    private String name;
    @ApiModelProperty("spec")
    private String spec;
    @ApiModelProperty("price, cent")
    private Integer price;
    @ApiModelProperty("updated price")
    private Integer newPrice;
    @ApiModelProperty("updated status")
    private Integer status = 1;
    @ApiModelProperty("updated stock")
    private Integer stock = 10;
    @ApiModelProperty("item image")
    private String image;
    @ApiModelProperty("create time")
    private LocalDateTime createTime;

}
