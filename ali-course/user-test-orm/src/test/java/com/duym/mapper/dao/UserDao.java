package com.duym.mapper.dao;
  
import com.duym.domain.entity.UserDO.UserDO;

import java.util.List;

public interface UserDao {  
  
    List<UserDO> findAll() throws Exception;
  
    UserDO findByCondition(UserDO userDO) throws Exception;  
}