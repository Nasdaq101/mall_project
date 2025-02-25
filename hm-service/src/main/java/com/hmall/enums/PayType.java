package com.hmall.enums;

import lombok.Getter;

@Getter
public enum PayType{
    JSAPI(1, "JSAPI"),
    MINI_APP(2, "MINI-APP"),
    APP(3, "APP"),
    NATIVE(4, "QRCODE"),
    BALANCE(5, "BALANCE"),
    ;
    private final int value;
    private final String desc;

    PayType(int value, String desc) {
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
