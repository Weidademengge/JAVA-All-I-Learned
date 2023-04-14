package com.duym.mapper;
  
import com.duym.domain.entity.UserDO.UserDO;

import java.util.List;
  

  
public interface UserMapper {  
  
    List<UserDO> findAll() throws Exception;
  
    UserDO findByCondition(UserDO userDO) throws Exception;  
}