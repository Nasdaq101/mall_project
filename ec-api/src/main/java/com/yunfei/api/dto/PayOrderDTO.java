package com.yunfei.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * payment order
 * </p>
 */
@Data
@ApiModel(description = "payment order entity")
public class PayOrderDTO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("business order number")
    private Long bizOrderNo;
    @ApiModelProperty("order number")
    private Long payOrderNo;
    @ApiModelProperty("user id")
    private Long bizUserId;
    @ApiModelProperty("channel code")
    private String payChannelCode;
    @ApiModelProperty("payment amount(cent)")
    private Integer amount;
    @ApiModelProperty("type，1：h5,2:x app，3：news channel，4：scan code，5：rest amount")
    private Integer payType;
    @ApiModelProperty("status，0：not submit，1:incomplete，2：cancel/exceed time，3：payment successful")
    private Integer status;
    @ApiModelProperty("expand json")
    private String expandJson;
    @ApiModelProperty("result code")
    private String resultCode;
    @ApiModelProperty("result msg")
    private String resultMsg;
    @ApiModelProperty("payment success time")
    private LocalDateTime paySuccessTime;
    @ApiModelProperty("over time")
    private LocalDateTime payOverTime;
    @ApiModelProperty("qr code")
    private String qrCodeUrl;
    @ApiModelProperty("create time")
    private LocalDateTime createTime;
    @ApiModelProperty("update time")
    private LocalDateTime updateTime;
}
