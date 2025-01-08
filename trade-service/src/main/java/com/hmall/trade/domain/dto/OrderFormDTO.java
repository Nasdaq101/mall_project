package com.hmall.trade.domain.dto;

import com.hmall.api.dto.OrderDetailDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "order form dto")
public class OrderFormDTO {
    @ApiModelProperty("address id")
    private Long addressId;
    @ApiModelProperty("payment type")
    private Integer paymentType;
    @ApiModelProperty("order details")
    private List<OrderDetailDTO> details;
}
