package com.duym.dubbo2.rpc.utils;

public class Class2String {

    public static String[] class2String(Class<?>[] classes) {
        String[] parameterTypeString = new String[classes.length];
        for (int i = 0; i < classes.length; i++) {
            parameterTypeString[i] = classes[i].getName();
        }
        return parameterTypeString;
    }
}