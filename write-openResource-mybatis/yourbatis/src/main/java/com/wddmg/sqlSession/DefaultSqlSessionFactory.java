package com.wddmg.sqlSession;


import com.wddmg.executor.Executor;
import com.wddmg.executor.SimpleExecutor;
import com.wddmg.pojo.Configuration;

/**
 * @author duym
 * @version $ Id: DefaultSqlSessionFactory, v 0.1 2023/03/08 13:48 banma-0163 Exp $
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory{


    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public SqlSession openSession(){
        // 1、创建执行器对象
        Executor simpleExecutor = new SimpleExecutor();


        //  2、创建sqlSession对象
        DefaultSqlSession defaultSqlSession = new DefaultSqlSession(configuration,simpleExecutor);

        return defaultSqlSession;


    }
}
