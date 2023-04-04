package com.wddmg.usertest.domain.common;

/**
 * @author duym
 * @version $ Id: ErrorCOde, v 0.1 2023/03/16 17:06 duym Exp $
 */
public enum ErrorCode {

    //错误码
    SUCCESS("00000", "成功"),
    PARAM_ERROR("A0400", "请求参数错误"),
    SYSTEM_ERROR("B0001", "系统执行出错"),
    ;

    /**
     * 编码
     */
    private final String code;

    /**
     * 描述信息
     */
    private final String desc;

    ErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
