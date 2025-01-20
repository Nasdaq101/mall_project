package com.hmall.pay.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(description = "pay order dto")
public class PayApplyDTO {
    @ApiModelProperty("business order id must be filled")
    @NotNull(message = "business order id must be filled")
    private Long bizOrderNo;
    @ApiModelProperty("amount must be positive!")
    @Min(value = 1, message = "amount must be positive!")
    private Integer amount;
    @ApiModelProperty("channel code must be filled")
    @NotNull(message = "channel code must be filled")
    private String payChannelCode;
    @ApiModelProperty("payment type must be filled")
    @NotNull(message = "payment type must be filled")
    private Integer payType;
    @ApiModelProperty("order information must be filled")
    @NotNull(message = "order information must be filled")
    private String orderInfo;
}
