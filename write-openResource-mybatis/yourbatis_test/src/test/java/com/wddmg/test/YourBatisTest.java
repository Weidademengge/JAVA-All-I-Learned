package com.wddmg.test;

import com.wddmg.dao.IUserDao;
import com.wddmg.io.Resources;
import com.wddmg.pojo.User;
import com.wddmg.sqlSession.SqlSession;
import com.wddmg.sqlSession.SqlSessionFactory;
import com.wddmg.sqlSession.SqlSessionFactoryBuilder;
import org.junit.Test;
import java.io.InputStream;
import java.util.List;

/**
 * @author duym
 * @version $ Id: IPersistentTest, v 0.1 2023/03/07 10:59 banma-0163 Exp $
 */
public class YourBatisTest {

    /**
     * 传统方式（不使用mapper代理）测试
     */
    @Test
    public void test1() throws Exception {

        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");

        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3、生产sqlSession;创建执行器
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4、调用sqlSession方法
        User user = new User();
        user.setId(1);
        user.setUsername("Tom");
        User user2 = sqlSession.selectOne("user.selectOne", user);

        System.out.println("条件查询---->"+user2);

        System.out.println("查询全部----------");
        List<User> list = sqlSession.selectList("user.selectList", null);
        for (User user1 : list) {
            System.out.println(user1);
        }

        //5、释放资源
        sqlSession.close();
    }

    /**
     * mapper代理测试
     */
    @Test
    public void test2() throws Exception {

        //1、根据配置文件的路径，加载成字节输入流，存到内存中。注意：配置文件还未解析
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapperConfig.xml");

        //2、解析配置文件，封装了Configuration对象；创建了sqlSessionFactory工厂对象
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

        //3、生产sqlSession;创建执行器
        SqlSession sqlSession = sqlSessionFactory.openSession();

        //4、调用sqlSession方法
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);

        User user1 = new User();
        user1.setId(1);
        user1.setUsername("Tom");
        User user3 = userDao.findByCondition(user1);
        System.out.println("条件查询---->" + user3);
        System.out.println("查询全部----------");
        List<User> all = userDao.findAll();
        for(User user:all){
            System.out.println(user);
        }
        //5、释放资源
        sqlSession.close();
    }
}
