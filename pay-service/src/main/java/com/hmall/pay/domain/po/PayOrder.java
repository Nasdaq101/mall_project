package com.hmall.pay.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * pay order
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pay_order")
public class PayOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * business order no.
     */
    private Long bizOrderNo;

    /**
     * pay order no.
     */
    private Long payOrderNo;

    /**
     * business user id
     */
    private Long bizUserId;

    /**
     * channel code
     */
    private String payChannelCode;

    /**
     * amount / cents
     */
    private Integer amount;

    /**
     * type，1：h5,2:mini-app，3official account：，4：qr-code，5：balance
     */
    private Integer payType;

    /**
     * payment status，0：not committed，1:not paid，2：cancelled/timeout，3：completed
     */
    private Integer status;

    /**
     * expand json
     */
    private String expandJson;

    /**
     * result code
     */
    private String resultCode;

    /**
     * result msg
     */
    private String resultMsg;

    /**
     * successful time
     */
    private LocalDateTime paySuccessTime;

    /**
     * overtime
     */
    private LocalDateTime payOverTime;

    /**
     * qr-code url
     */
    private String qrCodeUrl;

    /**
     * create time
     */
    private LocalDateTime createTime;

    /**
     * update time
     */
    private LocalDateTime updateTime;

    /**
     * creater
     */
    private Long creater;

    /**
     * updater
     */
    private Long updater;

    /**
     * is delete?
     */
    private Boolean isDelete;


}
