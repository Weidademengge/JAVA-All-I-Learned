package com.wddmg.executor;

import com.wddmg.config.BoundSql;
import com.wddmg.pojo.Configuration;
import com.wddmg.pojo.MappedStatement;
import com.wddmg.utils.GenericTokenParser;
import com.wddmg.utils.ParameterMapping;
import com.wddmg.utils.ParameterMappingTokenHandler;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author duym
 * @version $ Id: SimpleExecutor, v 0.1 2023/03/08 14:09 banma-0163 Exp $
 */
public class SimpleExecutor implements Executor{

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private ResultSet resultSet = null;
    @Override
    public <E> List<E> query(Configuration configuration, MappedStatement mappedStatement, Object param) throws Exception {
        //1、加载驱动，获取数据库连接
        connection = configuration.getDataSource().getConnection();

        /**
         * 获取preparedStatement预编译对象
         * 获取要执行的sql语句
         *                                     自定义的占位符
         *      select * from user where id = #{id} and username = #{username}
         * 替换：select * from user where id = ? and username = ?
         * 解析替换的过程中，#{id}里面的值保存下来
         */
        String sql = mappedStatement.getSql();
        BoundSql boundSql = getBoundSql(sql);
        //获得最终需要的sql
        String finalSql = boundSql.getFinalSql();
        preparedStatement = connection.prepareStatement(finalSql);

        //3、设置参数
        //com.wddmg.pojo.User
        String parameterType = mappedStatement.getParameterType();
        if(parameterType != null){
            Class<?> paramterTypeClass = Class.forName(parameterType);

            List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
            for(int i = 0;i<parameterMappingList.size();i++){
                ParameterMapping parameterMapping = parameterMappingList.get(i);
                //id username
                String paramName = parameterMapping.getContent();
                //反射
                Field declaredField = paramterTypeClass.getDeclaredField(paramName);
                //属性有可能是私有的，暴力访问
                declaredField.setAccessible(true);

                Object value = declaredField.get(param);
                //赋值占位符
                preparedStatement.setObject(i+1,value);
            }
        }


        // 4、执行sql，发起查询
        resultSet = preparedStatement.executeQuery();

        // 5、处理返回结果集
        List<E> list= new ArrayList<>();
        while(resultSet.next()){
            //元数据信息 包含了字段名和值
            ResultSetMetaData metaData = resultSet.getMetaData();

            //com.wddmg.pojo.User
            String resultType = mappedStatement.getResultType();
            Class<?> resultTypeClass = Class.forName(resultType);
            Object o = resultTypeClass.newInstance();


            for(int i = 1;i<= metaData.getColumnCount();i++){

                //字段名 id username
                String columnName = metaData.getColumnName(i);
                //字段的值
                Object value = resultSet.getObject(columnName);

                // 问题：现在要封装到哪一个实体中--------->封装到resultType对应的类
                // 封装-->用到内省库，属性描述器
                // 属性描述其：通过API方法获取某个属性的读写方法
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                Method writeMethod = propertyDescriptor.getWriteMethod();

                //参数1：需要个实例对象
                //参数2:要设置的值
                writeMethod.invoke(o,value);

            }
            list.add((E) o);
        }
        return list;
    }

    /**
     * 1、#{}占位符替换成？
     * 2、解析替换的过程中，将#{}中的值保存下来
     * @param sql
     * @return
     */
    private BoundSql getBoundSql(String sql) {

        //1、创建标记处理器：配合标记解析器完成标记的处理解析工作
        ParameterMappingTokenHandler parameterMappingTokenHandler = new ParameterMappingTokenHandler();
        //2、创建标记解析器
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", parameterMappingTokenHandler);

        //#{}占位符替换成？ 2、解析替换的过程中 将#{}里面的值保存下来，存在ParameterMapping中
        String finalSql = genericTokenParser.parse(sql);

        //#{}里面值的集合 id,username
        List<ParameterMapping> parameterMappings = parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(finalSql, parameterMappings);
        return boundSql;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        //释放资源
        if(resultSet != null){
            try {
                resultSet.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(preparedStatement != null){
            try{
                preparedStatement.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
