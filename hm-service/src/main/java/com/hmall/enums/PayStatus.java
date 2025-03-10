package com.hmall.enums;

import lombok.Getter;

@Getter
public enum PayStatus {
    NOT_COMMIT(0, "NOT COMMIT"),
    WAIT_BUYER_PAY(1, "WAIT PAY"),
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
