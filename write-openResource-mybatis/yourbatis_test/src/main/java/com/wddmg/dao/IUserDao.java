package com.wddmg.dao;

import com.wddmg.pojo.User;
import org.dom4j.DocumentException;
import java.util.List;

/**
 * @author duym
 * @version $ Id: IUserDao, v 0.1 2023/03/10 9:07 banma-0163 Exp $
 */
public interface IUserDao {

    /**
     * 查询所有
     * @return
     */
    List<User> findAll() throws Exception;

    /**
     * 根据多条件查询
     * @param user
     * @return
     */
    User findByCondition(User user) throws Exception;
}
