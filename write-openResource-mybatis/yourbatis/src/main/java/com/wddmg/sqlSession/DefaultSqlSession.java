package com.wddmg.sqlSession;

import com.wddmg.executor.Executor;
import com.wddmg.pojo.Configuration;
import com.wddmg.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

/**
 * @author duym
 * @version $ Id: DefaultSqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $
 */
public class DefaultSqlSession implements SqlSession{

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    /**
     * 查询多个结果
     * @param statementId 定位要执行的sql语句，从而执行
     * @param param 查询的参数,下面的？
     *              select * from user where username lke '%?%'
     * @return
     * @param <E>
     * @throws Exception
     */
    @Override
    public <E> List<E> selectList(String statementId, Object param) throws Exception {
        // 查询操作委派给底层的执行器
        // query():执行底层的JDBC 1、获取数据源对象，连接 2、sql是什么，参数是什么
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
        List<E> list = executor.query(configuration,mappedStatement,param);
        return list;
    }

    /**
     * 查询单个结果
     * @param statementId
     * @param param
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> T  selectOne(String statementId, Object param) throws Exception {
        // 去调用selectList();
        List<Object> list = this.selectList(statementId, param);
        if(list.size() == 1){
            return (T) list.get(0);
        }else if(list.size() > 1){
            throw new RuntimeException("返回结果过多");
        }else{
            return null;
        }
    }

    /**
     * 关闭连接池
     */
    @Override
    public void close() {
        executor.close();
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用JDK动态代理生成基于接口的代理对象
        Object proxy = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {

            /**
             *
             * @param o 代理对象的引用，很少用
             *
             * @param method 被调用的方法的字节码对象
             *
             * @param objects 调用的方法的参数
             *
             * @return
             * @throws Throwable
             */
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                //具体的瑞吉：执行底层JDBC
                //通过调用sqlSession里面的方法来完成方法调用
                //参数的准备：1、statementId：com.wddmg.dao.IUserDao.findALl 2、param
                //问题一：无法获取现有的statementId

                //拿到findAll
                String methodName = method.getName();
                //com.wddmg.dao.IUserDao
                String className = method.getDeclaringClass().getName();
                String statementId = className + "." + methodName;

                //方法调用，问题2：要调用sqlSession中的什么方法？
                //改造当前工程：sqlCommandType
                MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementId);
                // select update delete insert
                String sqlCommandType = mappedStatement.getSqlCommandType();
                switch(sqlCommandType){
                    case "select":
                        //执行查询方法调用
                        //问题3：该调用selectList还是selectOne
                        Type genericReturnType = method.getGenericReturnType();
                        //判断是否实现了泛型类型参数化-->其实就是返回值类型有没有泛型
                        if(genericReturnType instanceof ParameterizedType){
                            if(objects != null){
                                return selectList(statementId,objects[0]);
                            }
                                return selectList(statementId,null);
                        }
                        return selectOne(statementId,objects[0]);
                    case "update":
                        //执行更新方法调用
                        break;
                    case "delete":
                        //执行删除方法调用
                        break;
                    case "insert":
                        //执行插入方法调用
                        break;
                }


                return null;
            }
        });
        return (T) proxy;
    }
}
