package com.wddmg.config;

import com.wddmg.pojo.Configuration;
import com.wddmg.pojo.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.InputStream;
import java.util.List;

/**
 * 专门解析映射配置文件的类
 * parse方法：解析映射配置文件--->mappedStatement ----->configuration里面的map集合中
 * @author duym
 * @version $ Id: XMLMapperBuilder, v 0.1 2023/03/08 13:23 banma-0163 Exp $
 */
public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parse(InputStream resourceAsStream) throws DocumentException {

        Document document = new SAXReader().read(resourceAsStream);
        Element rootElement = document.getRootElement();

        //这里只查找select标签，其他先不写了
        List<Element> selectList = rootElement.selectNodes("//select");
        String namespace = rootElement.attributeValue("namespace");
        for(Element element:selectList){

            String id = element.attributeValue("id");
            String resultType = element.attributeValue("resultType");
            String parameterType = element.attributeValue("parameterType");
            String sql = element.getTextTrim();

            //封装mappedStatement对象
            MappedStatement mappedStatement = new MappedStatement();

            //StatementId: namespace.id
            String statementId = namespace + "." + id;
            mappedStatement.setStatementId(statementId);
            mappedStatement.setResultType(resultType);
            mappedStatement.setParameterType(parameterType);
            mappedStatement.setSql(sql);
            mappedStatement.setSqlCommandType("select");

            //将封装好的mappedStatement封装到configuration中
            configuration.getMappedStatementMap().put(statementId,mappedStatement);
        }
    }
}
