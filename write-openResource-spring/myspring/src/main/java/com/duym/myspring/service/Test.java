package com.duym.myspring.service;

import com.duym.myspring.spring.ApplicatinContext;

/**
 * @author duym
 * @version $ Id: Test, v 0.1 2023/04/17 13:15 duym Exp $
 */
public class Test {

    public static void main(String[] args) {
        ApplicatinContext applicatinContext = new ApplicatinContext(AppConfig.class);

        UserService userService = (UserService) applicatinContext.getBean("userServiceImpl");
        userService.test();
    }
}
