package com.yunfei.pay.enums;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;

@Getter
public enum PayChannel {
    wxPay("WECHAT PAY"),
    aliPay("ALI PAY"),
    balance("BALANCE PAY"),
    ;

    private final String desc;

    PayChannel(String desc) {
        this.desc = desc;
    }

    public static String desc(String value){
        if (StrUtil.isBlank(value)) {
            return "";
        }
        return PayChannel.valueOf(value).getDesc();
    }
}
