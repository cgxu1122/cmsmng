package com.ifhz.core.service.mail.enums;

/**
 * 类描述
 *
 * @author chenggang.xu@qunar.com created at 13-10-28 下午6:11
 * @version 1.0.0
 */
public enum MailType {
    Alert("alert"),
    none("");

    public String VALUE;

    private MailType(String VALUE) {
        this.VALUE = VALUE;
    }
}
