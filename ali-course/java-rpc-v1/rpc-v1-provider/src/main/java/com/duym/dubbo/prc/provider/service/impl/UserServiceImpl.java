package com.duym.dubbo.prc.provider.service.impl;

import com.duym.dubbo.prc.provider.annotation.RpcService;
import com.duym.dubbo.rpc.api.UserApi;
import com.duym.dubbo.rpc.api.dto.UserInfoDTO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author duym
 * @version $ Id: UserServiceImpl, v 0.1 2023/05/15 9:32 duym Exp $
 */
@RpcService
@Service
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
