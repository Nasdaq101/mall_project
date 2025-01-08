package com.hmall.item.domain.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "doc entity")
public class ItemDoc{

    @ApiModelProperty("item id")
    private String id;

    @ApiModelProperty("item name")
    private String name;

    @ApiModelProperty("price, cent")
    private Integer price;

    @ApiModelProperty("item image")
    private String image;

    @ApiModelProperty("item category")
    private String category;

    @ApiModelProperty("item brand")
    private String brand;

    @ApiModelProperty("item sold")
    private Integer sold;

    @ApiModelProperty("comment amount")
    private Integer commentCount;

    @ApiModelProperty("enable ad? true/false")
    private Boolean isAD;

    @ApiModelProperty("uopdate time")
    private LocalDateTime updateTime;
}
