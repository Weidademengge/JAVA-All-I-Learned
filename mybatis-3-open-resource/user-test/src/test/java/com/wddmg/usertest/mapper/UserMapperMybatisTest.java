package com.wddmg.usertest.mapper;

import java.util.HashMap;
import java.util.List;

import com.wddmg.usertest.domain.entity.UserDO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest {

    private static final Logger logger = LoggerFactory.getLogger(UserMapperTest.class);

    @Autowired
    private UserMapperMybatis userMapper;

    @Test
    public void find() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("username", "Tom");
        List<UserDO> users = userMapper.selectByMap(hashMap);
        logger.info("{}", users);
    }

}