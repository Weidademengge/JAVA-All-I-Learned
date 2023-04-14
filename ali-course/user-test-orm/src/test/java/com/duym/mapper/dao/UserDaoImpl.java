package com.duym.mapper.dao;
  
import com.duym.domain.entity.UserDO.UserDO;
import com.duym.orm.session.SqlSession;
import com.duym.orm.session.SqlSessionFactory;
import com.duym.orm.session.SqlSessionFactoryBuilder;

import java.util.List;


  
public class UserDaoImpl implements UserDao {  
    @Override  
    public List<UserDO> findAll() throws Exception {
  
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build("sqlMapConfig.xml");
        SqlSession sqlSession = sqlSessionFactory.openSession();
  
        return sqlSession.selectList("user.selectList");  
    }  
  
    @Override  
    public UserDO findByCondition(UserDO userDO) throws Exception {  
  
        SqlSessionFactory sqlSessionFactory = SqlSessionFactoryBuilder.build("sqlMapConfig.xml");  
        SqlSession sqlSession = sqlSessionFactory.openSession();  
  
        return sqlSession.selectOne("user.selectOne", userDO);  
    }  
}