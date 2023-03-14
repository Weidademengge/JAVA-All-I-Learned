package com.wddmg.dao;

import com.wddmg.io.Resources;
import com.wddmg.pojo.User;
import com.wddmg.sqlSession.SqlSession;
import com.wddmg.sqlSession.SqlSessionFactory;
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;
import org.dom4j.DocumentException;
import java.io.InputStream;
import java.util.List;

/**
 * @author duym
 * @version $ Id: UserDaoImpl, v 0.1 2023/03/10 9:09 banma-0163 Exp $
 */
public class UserDaoImpl implements IUserDao{

    @Override
    public List<User> findAll() throws Exception {

        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");

        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3、生产sqlSession;创建执行器
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4、调用sqlSession方法
        List<User> list = sqlSession.selectList("user.selectList", null);
        for (User user1 : list) {
            System.out.println(user1);
        }

        //5、释放资源
        sqlSession.close();
        return null;
    }

    @Override
    public User findByCondition(User user) throws Exception {
        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");

        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3、生产sqlSession;创建执行器
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4、调用sqlSession方法
        User user3 = new User();
        user3.setId(1);
        user3.setUsername("Tom");
        User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println(user2);

        //5、释放资源
        sqlSession.close();
        return user3;
    }
}
