
# 1. 分页原理

PageHelper方法使用了静态的ThreadLocal（[[ThreadLocal及相关]]）参数，分页参数和线程是绑定的。内部流程是ThreadLocal中设置了分页参数（pageNum，pageSize），之后在查询执行的时候，获取当前线程中的分页参数，执行查询的时候通过拦截器在sql语句中添加分页参数，之后实现分页查询，查询结束后在 finally 语句中清除ThreadLocal中的查询参数。

(这里抛出个问题，就是为什么pagehelper用的是ThreadLocal存储，而mybatis要把全局配置Configuration向下传递)

通俗地讲，就是

PageHelper.startPage(int pageNum, int pageSize)相当于开启分页，通过拦截 MySQL 的方式，把查询语句拦截下来加上 limit 语句

所以，该语句应该放在查询语句之前。

因此，整个过程应该是，前端会传过来pageNum和pageSize这两个参数，当点击前端的下一页按钮的时候，这个pageNum会加1，然后重新传到后端，调用后端的接口。后端拿到pageNum和pageSize之后，利用PageHelper.startPage设置分页，这样，在后面的sql语句中不用加 limit了。

注意：只要保证在PageHelper方法调用后紧跟 MyBatis 查询方法，这就是安全的。因为PageHelper在finally代码段中自动清除了ThreadLocal存储的对象。

```java
PageHelper.startPage(dto.getPageNum(), dto.getPageSize());
List<PayProject> list = payProjectMapper.queryPayProjectByPage(dto);
```

这样，查出来的list则不是满足查询条件的全量数据，而只是一页的数据，这也是分页查询的其中一种方式：利用limit去查，每次只返回一页数据。

还有另外一种方式：直接全部查出来所有满足查询条件的数据放到list中，然后再进行分页返回给前端。该方式的弊端是，如果数据量过多，全部查出来放到内存中，很有可能导致内存溢出。

# 2. 源码

在这里，作者将一个页面分为两部分

- 页的信息描述部分
- 页的数据部分


信息描述部分包括PageSerializable、PageInfo

数据部分是用Page

PageInfo定义了一个页面的基本信息，包括页大小、页码等；PageSerializable提供了一个List，让PageInfo去继承PageSerializable，这样整个页面的框架部分就搭好了，总结就是：PageInfo描述页的基本信息，PageSerializable提供一个List放数据，至于放的数据类型是啥，那就是这个了。

这里的就是ArrayList，也就是xxRequestDTO类型

- Page继承了ArrayList，所以Page本质上就是一个ArrayList，可以用来存放一个个数据实体

- PageSerializable 的成员变量很简单，总共个就两个成员变量，一个是Long total；一个是List list，可以指向一个Page实例

- PageInfo继承了PageSerializable，提供了更加丰富的成员变量来描述页的信息：比如当前页、每页条数、当前页的数量等

## `PageInfo\<T>`

```java
package com.github.pagehelper;

import java.util.Collection;
import java.util.List;
import lombok.Data;

/**
 * 现在再来看PageInfo就好理解了
 * PageInfo继承了PageSerializable，而PageSerializable的成员变量可以为Page实例，而Page实际上就是个ArrayList
 * 所以PageInfo继承了PageSerializable的成员变量，所以PageInfo的成员变量可以为Page实例
 */
@Data // 赋予 getter setter 
public class PageInfo<T> extends PageSerializable<T> {
    
    /**
     * 通过字段可以看出，PageInfo是一个 页信息 类，用来描述一页的各种信息
     */
    private int pageNum;  // 当前页
    private int pageSize;  // 每页条数
    private int size;  // 当前页的数量
    private int startRow;  // 当前页面第一个元素在数据库中的行号
    private int endRow;  // 当前页面最后一个元素在数据库中的行号
    private int pages; // 总页数
    private int prePage;  // 当前页
    private int nextPage;  // 下一页
    private boolean isFirstPage;  // 是否是首页
    private boolean isLastPage;  // 是否是最后一页
    private boolean hasPreviousPage;  // 是否有前一页
    private boolean hasNextPage;  // 是否有后一页
    private int navigatePages;  // 导航页码数
    private int[] navigatepageNums;  // 所有导航页号
    private int navigateFirstPage;  // 导航条上的第一页
    private int navigateLastPage;  // 导航条上的最后一页

    /**
     * 无参构造器
     */
    public PageInfo() {
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
    }

    /** 
     * 有参构造器，参数类型为 List<T>
     * 默认将 导航页码数 navigatePages 设置为8
     */
    public PageInfo(List<T> list) {
        this(list, 8);
    }

    /**
     * 有参构造器 
     */
    public PageInfo(List<T> list, int navigatePages) {
        super(list);  // 调用父类 PageSerializable<T> 的构造器
        this.isFirstPage = false;
        this.isLastPage = false;
        this.hasPreviousPage = false;
        this.hasNextPage = false;
        if (list instanceof Page) {
            Page page = (Page)list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
            this.size = page.size();
            if (this.size == 0) {
                this.startRow = 0;
                this.endRow = 0;
            } else {
                this.startRow = page.getStartRow() + 1;
                this.endRow = this.startRow - 1 + this.size;
            }
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
            this.size = list.size();
            this.startRow = 0;
            this.endRow = list.size() > 0 ? list.size() - 1 : 0;
        }

        if (list instanceof Collection) {
            this.navigatePages = navigatePages;
            this.calcNavigatepageNums();
            this.calcPage();
            this.judgePageBoudary();
        }

    }

    public static <T> PageInfo<T> of(List<T> list) {
        return new PageInfo(list);
    }

    public static <T> PageInfo<T> of(List<T> list, int navigatePages) {
        return new PageInfo(list, navigatePages);
    }

    private void calcNavigatepageNums() {
        int i;
        if (this.pages <= this.navigatePages) {
            this.navigatepageNums = new int[this.pages];

            for(i = 0; i < this.pages; ++i) {
                this.navigatepageNums[i] = i + 1;
            }
        } else {
            this.navigatepageNums = new int[this.navigatePages];
            i = this.pageNum - this.navigatePages / 2;
            int endNum = this.pageNum + this.navigatePages / 2;
            int i;
            if (i < 1) {
                i = 1;

                for(i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = i++;
                }
            } else if (endNum > this.pages) {
                endNum = this.pages;

                for(i = this.navigatePages - 1; i >= 0; --i) {
                    this.navigatepageNums[i] = endNum--;
                }
            } else {
                for(i = 0; i < this.navigatePages; ++i) {
                    this.navigatepageNums[i] = i++;
                }
            }
        }

    }

    private void calcPage() {
        if (this.navigatepageNums != null && this.navigatepageNums.length > 0) {
            this.navigateFirstPage = this.navigatepageNums[0];
            this.navigateLastPage = this.navigatepageNums[this.navigatepageNums.length - 1];
            if (this.pageNum > 1) {
                this.prePage = this.pageNum - 1;
            }

            if (this.pageNum < this.pages) {
                this.nextPage = this.pageNum + 1;
            }
        }

    }

    private void judgePageBoudary() {
        this.isFirstPage = this.pageNum == 1;
        this.isLastPage = this.pageNum == this.pages || this.pages == 0;
        this.hasPreviousPage = this.pageNum > 1;
        this.hasNextPage = this.pageNum < this.pages;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("PageInfo{");
        sb.append("pageNum=").append(this.pageNum);
        sb.append(", pageSize=").append(this.pageSize);
        sb.append(", size=").append(this.size);
        sb.append(", startRow=").append(this.startRow);
        sb.append(", endRow=").append(this.endRow);
        sb.append(", total=").append(this.total);
        sb.append(", pages=").append(this.pages);
        sb.append(", list=").append(this.list);
        sb.append(", prePage=").append(this.prePage);
        sb.append(", nextPage=").append(this.nextPage);
        sb.append(", isFirstPage=").append(this.isFirstPage);
        sb.append(", isLastPage=").append(this.isLastPage);
        sb.append(", hasPreviousPage=").append(this.hasPreviousPage);
        sb.append(", hasNextPage=").append(this.hasNextPage);
        sb.append(", navigatePages=").append(this.navigatePages);
        sb.append(", navigateFirstPage=").append(this.navigateFirstPage);
        sb.append(", navigateLastPage=").append(this.navigateLastPage);
        sb.append(", navigatepageNums=");
        if (this.navigatepageNums == null) {
            sb.append("null");
        } else {
            sb.append('[');

            for(int i = 0; i < this.navigatepageNums.length; ++i) {
                sb.append(i == 0 ? "" : ", ").append(this.navigatepageNums[i]);
            }

            sb.append(']');
        }

        sb.append('}');
        return sb.toString();
    }
}

```

## `PageSerializable\<T>`

```java
package com.github.pagehelper;

import java.io.Serializable;
import java.util.List;

/** 
 * 实现了Serializable，意为可序列化的Page
 */
public class PageSerializable<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    protected long total;
    protected List<T> list;
    
    public PageSerializable() {}

    /**
     * 有参构造器 传入List<T>
     * 这里道出了PageSerializable和Page的关系：Page实例可作为PageSerializable的成员变量
     * 
     */
    public PageSerializable(List<T> list) {
        this.list = list;
        if (list instanceof Page) {  // 而Page继承了ArrayList
            this.total = ((Page)list).getTotal();
        } else {
            this.total = (long)list.size();
        }
    }

    public static <T> PageSerializable<T> of(List<T> list) {
        return new PageSerializable(list);
    }

    /** 后面为属性的getter setter方法 **/
    public long getTotal() {return this.total;}
    public void setTotal(long total) {this.total = total;}
    public List<T> getList() {return this.list;}
    public void setList(List<T> list) {this.list = list;}
    public String toString() {
        return "PageSerializable{total=" + this.total + ", list=" + this.list + '}';
    }
}

```

## `Page<T>`

-   Page 类没什么特殊的地方
-   本质上是一个ArrayList，只不过要比ArrayList属性和功能更丰富一些

```java
package com.github.pagehelper;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * 可以看出Page继承了ArrayList
 */
public class Page<E> extends ArrayList<E> implements Closeable {
    private static final long serialVersionUID = 1L;
    private int pageNum;  // 当前页
    private int pageSize;  // 每页条数
    private int startRow;  // 当前页面第一个元素在数据库中的行号
    private int endRow;  // 当前页面最后一个元素在数据库中的行号
    private long total; 
    private int pages;
    private boolean count;
    private Boolean reasonable;
    private Boolean pageSizeZero;  // 每页条数是否为0
    private String countColumn;
    private String orderBy;
    private boolean orderByOnly;

    public Page() {
        this.count = true;
    }

    public Page(int pageNum, int pageSize) {
        this(pageNum, pageSize, true, (Boolean)null);
    }

    public Page(int pageNum, int pageSize, boolean count) {
        this(pageNum, pageSize, count, (Boolean)null);
    }

    private Page(int pageNum, int pageSize, boolean count, Boolean reasonable) {
        super(0);  // 调用父类ArrayList构造器初始化化大小为0
        this.count = true;
        if (pageNum == 1 && pageSize == 2147483647) {
            this.pageSizeZero = true;
            pageSize = 0;
        }
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.count = count;
        this.calculateStartAndEndRow();
        this.setReasonable(reasonable);
    }

    // 有参构造器
    public Page(int[] rowBounds, boolean count) {
        super(0);
        this.count = true;
        if (rowBounds[0] == 0 && rowBounds[1] == 2147483647) {
            this.pageSizeZero = true;
            this.pageSize = 0;
        } else {
            this.pageSize = rowBounds[1];
            this.pageNum = rowBounds[1] != 0 ? (int)Math.ceil(((double)rowBounds[0] + (double)rowBounds[1]) / (double)rowBounds[1]) : 0;
        }

        this.startRow = rowBounds[0];
        this.count = count;
        this.endRow = this.startRow + rowBounds[1];
    }

    /** 后面主要是一些getter setter 方法以及少量其他方法 **/
    
    public List<E> getResult() {
        return this;
    }

    public int getPages() {return this.pages;}
    public Page<E> setPages(int pages) {this.pages = pages;return this;}
    public int getEndRow() {return this.endRow;}
    public Page<E> setEndRow(int endRow) {this.endRow = endRow;return this;}
    public int getPageNum() {return this.pageNum;}
    public Page<E> setPageNum(int pageNum) {
        this.pageNum = this.reasonable != null && this.reasonable && pageNum <= 0 ? 1 : pageNum;
        return this;
    }
    public int getPageSize() {return this.pageSize;}
    public Page<E> setPageSize(int pageSize) {this.pageSize = pageSize;return this;}
    public int getStartRow() {return this.startRow;}
    public Page<E> setStartRow(int startRow) {this.startRow = startRow;return this;}
    public long getTotal() {return this.total;}
    
    public void setTotal(long total) {
        this.total = total;
        if (total == -1L) {
            this.pages = 1;
        } else {
            if (this.pageSize > 0) {
                this.pages = (int)(total / (long)this.pageSize + (long)(total % (long)this.pageSize == 0L ? 0 : 1));
            } else {
                this.pages = 0;
            }

            if (this.reasonable != null && this.reasonable && this.pageNum > this.pages) {
                if (this.pages != 0) {
                    this.pageNum = this.pages;
                }

                this.calculateStartAndEndRow();
            }

        }
    }

    public Boolean getReasonable() {return this.reasonable;}
    public Page<E> setReasonable(Boolean reasonable) {
        if (reasonable == null) {
            return this;
        } else {
            this.reasonable = reasonable;
            if (this.reasonable && this.pageNum <= 0) {
                this.pageNum = 1;
                this.calculateStartAndEndRow();
            }

            return this;
        }
    }

    public Boolean getPageSizeZero() {return this.pageSizeZero;}

    public Page<E> setPageSizeZero(Boolean pageSizeZero) {
        if (pageSizeZero != null) {this.pageSizeZero = pageSizeZero;}
        return this;
    }

    public String getOrderBy() {return this.orderBy;}
    public <E> Page<E> setOrderBy(String orderBy) {this.orderBy = orderBy;return this;}
    public boolean isOrderByOnly() {return this.orderByOnly;}
    public void setOrderByOnly(boolean orderByOnly) {this.orderByOnly = orderByOnly;}
    private void calculateStartAndEndRow() {
        this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize : 0;
        this.endRow = this.startRow + this.pageSize * (this.pageNum > 0 ? 1 : 0);
    }
    public boolean isCount() {return this.count;}
    public Page<E> setCount(boolean count) {
        this.count = count;
        return this;
    }
    public Page<E> pageNum(int pageNum) {
        this.pageNum = this.reasonable != null && this.reasonable && pageNum <= 0 ? 1 : pageNum;
        return this;
    }
    public Page<E> pageSize(int pageSize) {
        this.pageSize = pageSize;
        this.calculateStartAndEndRow();
        return this;
    }
    public Page<E> count(Boolean count) {this.count = count;return this;}
    public Page<E> reasonable(Boolean reasonable) {this.setReasonable(reasonable);return this;}
    public Page<E> pageSizeZero(Boolean pageSizeZero) {this.setPageSizeZero(pageSizeZero);return this;}
    public Page<E> countColumn(String columnName) {this.countColumn = columnName;return this;}
    public PageInfo<E> toPageInfo() {PageInfo<E> pageInfo = new PageInfo(this);return pageInfo;}
    public PageSerializable<E> toPageSerializable() {
        PageSerializable<E> serializable = new PageSerializable(this);
        return serializable;
    }
    public <E> Page<E> doSelectPage(ISelect select) {select.doSelect();return this;}
    public <E> PageInfo<E> doSelectPageInfo(ISelect select) {select.doSelect();return this.toPageInfo();}
    public <E> PageSerializable<E> doSelectPageSerializable(ISelect select) {select.doSelect();return this.toPageSerializable();}
    public long doCount(ISelect select) {
        this.pageSizeZero = true;
        this.pageSize = 0;
        select.doSelect();
        return this.total;
    }
    public String getCountColumn() {return this.countColumn;}
    public void setCountColumn(String countColumn) {this.countColumn = countColumn;}
    public String toString() {
        return "Page{count=" + this.count + ", pageNum=" + this.pageNum + ", pageSize=" + this.pageSize + ", startRow=" + this.startRow + ", endRow=" + this.endRow + ", total=" + this.total + ", pages=" + this.pages + ", reasonable=" + this.reasonable + ", pageSizeZero=" + this.pageSizeZero + '}' + super.toString();
    }
    public void close() {PageHelper.clearPage();}
}

```