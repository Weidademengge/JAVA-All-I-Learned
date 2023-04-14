package com.duym.mapper;
  
import java.util.List;


import com.duym.domain.entity.UserDO.UserDO;
import com.duym.mapper.dao.UserDao;
import com.duym.mapper.dao.UserDaoImpl;
import org.junit.Test;
  
public class UserDaoTest {  
  
    private UserDao userDao = new UserDaoImpl();
  
    @Test  
    public void selectOne() throws Exception {  
  
        UserDO userDO = new UserDO();
        userDO.setId(1L);  
        userDO.setUsername("username1");  
  
        UserDO one = userDao.findByCondition(userDO);  
        System.out.println(one);  
    }  
  
    @Test  
    public void selectList() throws Exception {  
  
        List<UserDO> users = userDao.findAll();  
        users.forEach(user -> System.out.println(user));  
    }  
}