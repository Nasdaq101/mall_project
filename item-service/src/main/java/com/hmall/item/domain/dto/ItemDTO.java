package com.hmall.item.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "item entity")
public class ItemDTO {
    @ApiModelProperty("item id")
    private Long id;
    @ApiModelProperty("SKU name")
    private String name;
    @ApiModelProperty("price, cent")
    private Integer price;
    @ApiModelProperty("stock amount")
    private Integer stock;
    @ApiModelProperty("item image")
    private String image;
    @ApiModelProperty("item category")
    private String category;
    @ApiModelProperty("brand name")
    private String brand;
    @ApiModelProperty("spec")
    private String spec;
    @ApiModelProperty("sold amount")
    private Integer sold;
    @ApiModelProperty("comment amount")
    private Integer commentCount;
    @ApiModelProperty("enable ad? true/false")
    private Boolean isAD;
    @ApiModelProperty("status 1-normal，2-offline，3-deleted")
    private Integer status;
}
