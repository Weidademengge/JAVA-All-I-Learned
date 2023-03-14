package com.wddmg.config;

import com.wddmg.utils.ParameterMapping;
import java.util.List;

/**
 * @author duym
 * @version $ Id: BoundSql, v 0.1 2023/03/08 14:46 banma-0163 Exp $
 */
public class BoundSql {

    private String finalSql;

    private List<ParameterMapping> parameterMappingList;

    public BoundSql(String finalSql, List<ParameterMapping> parameterMappingList) {
        this.finalSql = finalSql;
        this.parameterMappingList = parameterMappingList;
    }

    public String getFinalSql() {
        return finalSql;
    }

    public void setFinalSql(String finalSql) {
        this.finalSql = finalSql;
    }

    public List<ParameterMapping> getParameterMappingList() {
        return parameterMappingList;
    }

    public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
        this.parameterMappingList = parameterMappingList;
    }
}
