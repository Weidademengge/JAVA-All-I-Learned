package com.duym.usertestall;

import com.duym.starter.pojo.SimpleBean;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserTestAllApplicationTests {

    @Autowired
    private SimpleBean simpleBean;

    @Test
    void contextLoads() {
        System.out.println(simpleBean);
    }

}
