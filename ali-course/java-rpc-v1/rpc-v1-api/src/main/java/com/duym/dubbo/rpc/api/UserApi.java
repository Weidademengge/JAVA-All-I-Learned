package com.duym.dubbo.rpc.api;

import com.duym.dubbo.rpc.api.dto.UserInfoDTO;

/**
 * @author duym
 * @version $ Id: UserApi, v 0.1 2023/05/15 9:15 duym Exp $
 */
public interface UserApi {

    UserInfoDTO getById(Integer id);
}
