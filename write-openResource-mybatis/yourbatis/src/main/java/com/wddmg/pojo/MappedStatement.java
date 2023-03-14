package com.wddmg.pojo;

/**
 * 映射配置文件
 * @author duym
 * @version $ Id: MappedStatement, v 0.1 2023/03/08 8:57 banma-0163 Exp $
 */
public class MappedStatement {

    //唯一标识 statementId:namespace.id
    private String statementId;
    //返回值类型
    private String resultType;
    //参数值类型
    private String parameterType;
    //sql语句
    private String sql;

    //sqlCommandType ：解析当前是什么操作的一个属性
    private String sqlCommandType;

    public String getSqlCommandType() {
        return sqlCommandType;
    }

    public void setSqlCommandType(String sqlCommandType) {
        this.sqlCommandType = sqlCommandType;
    }

    public String getStatementId() {
        return statementId;
    }



    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
}
