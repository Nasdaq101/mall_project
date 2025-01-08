package com.hmall.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "item dto")
public class ItemDTO {
    @ApiModelProperty("item id")
    private Long id;
    @ApiModelProperty("SKU name")
    private String name;
    @ApiModelProperty("price(cent)")
    private Integer price;
    @ApiModelProperty("stock amount")
    private Integer stock;
    @ApiModelProperty("image")
    private String image;
    @ApiModelProperty("category")
    private String category;
    @ApiModelProperty("brand name")
    private String brand;
    @ApiModelProperty("spec")
    private String spec;
    @ApiModelProperty("sold")
    private Integer sold;
    @ApiModelProperty("comment count")
    private Integer commentCount;
    @ApiModelProperty("if ad? true/false")
    private Boolean isAD;
    @ApiModelProperty("item status 1-online，2-offline，3-deleted")
    private Integer status;
}
