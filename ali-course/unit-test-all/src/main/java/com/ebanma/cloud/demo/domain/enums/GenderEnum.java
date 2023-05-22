package com.ebanma.cloud.demo.domain.enums;

public enum GenderEnum {
    /** 保密(0) */
    SECRET(0, "保密"),
    /** 男性(1) */
    MALE(1, "男性"),
    /** 女性(2) */
    FEMALE(2, "女性");
    /** 性别取值 */
    private final int value;
    /** 性别描述 */
    private final String desc;

    GenderEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static GenderEnum fromValue(int value) {
        for (GenderEnum gender : values()) {
            if (gender.value == value) {
                return gender;
            }
        }
        return null;
    }
}