package com.wddmg.sqlSession;

import java.util.List;

/**
 * @author duym
 * @version $ Id: SqlSession, v 0.1 2023/03/08 13:57 banma-0163 Exp $
 */
public interface SqlSession {

    /**
     * 查询多个结果
     * @return
     */
    <E> List<E> selectList(String statementId,Object param) throws Exception;

    /**
     * 查询单个结果
     * @param statementId
     * @param param
     * @return
     * @param <T>
     */
    <T> T selectOne(String statementId,Object param) throws Exception;

    /**
     * 清除资源
     */
    void close();

    /**
     * 生成代理对象
     * @return
     * @param <T>
     */
    <T> T getMapper(Class<?> mapperClass);

}
