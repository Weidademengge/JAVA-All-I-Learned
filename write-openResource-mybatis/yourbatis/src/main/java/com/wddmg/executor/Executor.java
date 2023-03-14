package com.wddmg.executor;

import com.wddmg.pojo.Configuration;
import com.wddmg.pojo.MappedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author duym
 * @version $ Id: Executor, v 0.1 2023/03/08 14:08 banma-0163 Exp $
 */
public interface Executor {
    <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws SQLException, ClassNotFoundException, NoSuchFieldException, IllegalAccessException, Exception;

    void close();
}
