package com.yunfei.pay.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    NOT_COMMIT(0, "NOT COMMITTED"),
    WAIT_BUYER_PAY(1, "NOT PAID"),
    TRADE_CLOSED(2, "CLOSED"),
    TRADE_SUCCESS(3, "SUCCESS"),
    TRADE_FINISHED(3, "FINISHED"),
    ;
    private final int value;
    private final String desc;

    PayStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public boolean equalsValue(Integer value){
        if (value == null) {
            return false;
        }
        return getValue() == value;
    }
}
