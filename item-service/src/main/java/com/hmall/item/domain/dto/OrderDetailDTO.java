package com.hmall.item.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@ApiModel(description = "order details")
@Data
@Accessors(chain = true)
public class OrderDetailDTO {
    @ApiModelProperty("item id")
    private Long itemId;
    @ApiModelProperty("item amount")
    private Integer num;
}
