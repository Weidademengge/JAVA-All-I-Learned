package com.wddmg.sqlSession;

/**
 * @author duym
 * @version $ Id: SqlSessionFactory, v 0.1 2023/03/08 9:11 banma-0163 Exp $
 */
public interface SqlSessionFactory {

    /**
     * 1、生产sqlSession对象
     * 2、创建执行器对象
     * @return
     */
    SqlSession openSession();
}
