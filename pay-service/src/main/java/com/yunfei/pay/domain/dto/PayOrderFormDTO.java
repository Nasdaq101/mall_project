package com.yunfei.pay.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(description = "pay order dto")
public class PayOrderFormDTO {
    @ApiModelProperty("order id must be filled")
    @NotNull(message = "order id must be filled")
    private Long id;
    @ApiModelProperty("msg")
    @NotNull(message = "msg")
    private String pw;
}
