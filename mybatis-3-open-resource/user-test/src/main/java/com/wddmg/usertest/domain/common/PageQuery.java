package com.wddmg.usertest.domain.common;

import java.io.Serializable;

/**
 * @author duym
 * @version $ Id: PageQuery, v 0.1 2023/03/16 17:13 duym Exp $
 */
public class PageQuery<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5443997546397888159L;

    /**
     * 当前页
     */
    private Integer pageNo = 1;

    /**
     * 每页条数
     */
    private Integer pageSize = 20;

    /**
     * 动态查询条件
     */
    private T query;

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public T getQuery() {
        return query;
    }

    public void setQuery(T query) {
        this.query = query;
    }
}
