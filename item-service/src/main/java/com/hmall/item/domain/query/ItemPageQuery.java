package com.hmall.item.domain.query;

import com.hmall.common.domain.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "page query condition")
public class ItemPageQuery extends PageQuery {
    @ApiModelProperty("key word")
    private String key;
    @ApiModelProperty("item category")
    private String category;
    @ApiModelProperty("item brand")
    private String brand;
    @ApiModelProperty("min price")
    private Integer minPrice;
    @ApiModelProperty("max price")
    private Integer maxPrice;
}
