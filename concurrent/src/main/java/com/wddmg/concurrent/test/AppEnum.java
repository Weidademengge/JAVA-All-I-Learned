package com.wddmg.concurrent.test;

/**
 * @author duym
 * @version $ Id: Test111, v 0.1 2023/04/12 14:47 duym Exp $
 */
public enum AppEnum {

    systemId("0","所属系统编号","setSystemId"),
    appCode("1","app编号","setAppCode"),
    appName("2","app名称","setAppName"),
    feEngineeringName("3","前端工程地址","setFeEngineeringName"),
    appType("4","app类型","setAppType"),
    appLeader("5","负责人","setAppLeader"),
    appLeaderAccount("6","负责人账号","setAppLeaderAccount"),
    implementLeader("7","实施负责人","setImplementLeader"),
    implementLeaderAccount("8","实施负责人账号","setImplementLeaderAccount"),
    appSort("9","排序","setAppSort"),
    appLeaderdingding("10","负责人钉钉号","setAppLeaderdingding"),
    isUse("11","是否启用(请填写是或否)","setIsUse"),
    picName("12","图标名称","setPicName"),
    remark("13","备注","setRemark");

    private final String num;
    private final String name;
    private final String method;

    AppEnum(String num, String name, String method) {
        this.num = num;
        this.name = name;
        this.method = method;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getMethod() {
        return method;
    }

    public static String getIndexName(int index){
        AppEnum[] appEnumArr = AppEnum.class.getEnumConstants();
        return appEnumArr[index].getName();
    }

    public static String getIndexNum(int index){
        AppEnum[] appEnumArr = AppEnum.class.getEnumConstants();
        return appEnumArr[index].getNum();
    }
    public static String getIndexMethod(int index){
        AppEnum[] appEnumArr = AppEnum.class.getEnumConstants();
        return appEnumArr[index].getMethod();
    }
}
