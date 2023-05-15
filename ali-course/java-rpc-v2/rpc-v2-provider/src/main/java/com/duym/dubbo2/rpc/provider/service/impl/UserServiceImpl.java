package com.duym.dubbo2.rpc.provider.service.impl;

import com.duym.dubbo2.rpc.api.UserApi;
import com.duym.dubbo2.rpc.api.dto.UserInfoDTO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserServiceImpl implements UserApi {
    Map<Object, UserInfoDTO> userMap = new HashMap();

    @Override
    public UserInfoDTO getById(Integer id) {
        if (userMap.size() == 0) {
            UserInfoDTO user1 = new UserInfoDTO();
            user1.setId(1);
            user1.setName("张三");
            UserInfoDTO user2 = new UserInfoDTO();
            user2.setId(2);
            user2.setName("李四");
            userMap.put(user1.getId(), user1);
            userMap.put(user2.getId(), user2);
        }
        return userMap.get(id);
    }
}