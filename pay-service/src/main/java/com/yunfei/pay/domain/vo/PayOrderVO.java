package com.yunfei.pay.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * pay order vo
 * </p>
 */
@Data
@ApiModel(description = "pay order vo")
public class PayOrderVO {
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("business order number")
    private Long bizOrderNo;
    @ApiModelProperty("pay order number")
    private Long payOrderNo;
    @ApiModelProperty("business user id")
    private Long bizUserId;
    @ApiModelProperty("channel code")
    private String payChannelCode;
    @ApiModelProperty("amount/cents")
    private Integer amount;
    @ApiModelProperty("payment type，1：h5,2:mini-app，3：official account，4：qr-code，5：balance")
    private Integer payType;
    @ApiModelProperty("status，0：not committed，1:not paid，2：timeout/cancelled，3：completed")
    private Integer status;
    @ApiModelProperty("expand json")
    private String expandJson;
    @ApiModelProperty("result code")
    private String resultCode;
    @ApiModelProperty("result msg")
    private String resultMsg;
    @ApiModelProperty("payment success time")
    private LocalDateTime paySuccessTime;
    @ApiModelProperty("payment over time")
    private LocalDateTime payOverTime;
    @ApiModelProperty("qr-code url")
    private String qrCodeUrl;
    @ApiModelProperty("create time")
    private LocalDateTime createTime;
    @ApiModelProperty("update time")
    private LocalDateTime updateTime;
}
