package com.duym.usertestall.domain.common;
  
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
  
public class PageQuery<T> implements Serializable {  
  
    /**  
     * serialVersionUID     
	 */    
    private static final long serialVersionUID = 5443997546397888159L;  
  
    /**  
     * 当前页  
     */
    @Min(value = 1,message="页号必须为正整数！")
    private Integer pageNo = 1;  
  
    /**  
     * 每页条数  
     */
    @Max(value = 100,message = "每页条数不能超过100条！")
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