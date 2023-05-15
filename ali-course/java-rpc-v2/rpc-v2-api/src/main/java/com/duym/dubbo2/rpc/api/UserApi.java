package com.duym.dubbo2.rpc.api;


import com.duym.dubbo2.rpc.api.dto.UserInfoDTO;

public interface UserApi {
  
UserInfoDTO getById(Integer id);
}