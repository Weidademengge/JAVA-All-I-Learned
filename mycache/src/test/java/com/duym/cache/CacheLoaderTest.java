package com.duym.cache;

import com.duym.cache.eliminationstrategy.po.EmployeePO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sun.istack.internal.NotNull;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author duym
 * @version $ Id: CacheLoaderTest, v 0.1 2023/04/27 18:43 duym Exp $
 */
public class CacheLoaderTest {

    private boolean isTrue = false;

    @Test
    public void testBasic() throws ExecutionException, InterruptedException {
        LoadingCache<String,EmployeePO> cache = CacheBuilder.newBuilder().maximumSize(10)
                .expireAfterAccess(30, TimeUnit.MILLISECONDS)
                .build(new CacheLoader<String, EmployeePO>() {
                    @Override
                    public EmployeePO load(String key) throws Exception {
                        return findEmployeeByName(key);
                    }
                });
        EmployeePO emp = cache.get("Alex");
        assertThat(emp, notNullValue());
        assertLoadFromDBThenReset();
        emp = cache.get("Alex");
        assertThat(emp,notNullValue());
        assertLoadFromCache();

        TimeUnit.MILLISECONDS.sleep(31);
        emp = cache.get("Alex");
        assertThat(emp,notNullValue());
        assertLoadFromCache();
    }

    public void testEvictionBySize(){
        CacheLoader<String, EmployeePO> cacheLoader = creatCacheLoader();
        LoadingCache<String,EmployeePO> cache = CacheBuilder.newBuilder()
                .maximumSize(3).build(cacheLoader);
        cache.getUnchecked("Alex");
        assertLoadFromDBThenReset();
        cache.getUnchecked("Jack");
        assertLoadFromDBThenReset();

        cache.getUnchecked("Gavin");
        assertLoadFromDBThenReset();

        assertThat(cache.size(),equalTo(3L));
        cache.getUnchecked("Susan");
        assertThat(cache.getIfPresent("Alex"),nullValue());

        assertThat(cache.getIfPresent("Susan"),nullValue());
    }

    private CacheLoader<String, EmployeePO> creatCacheLoader() {
        return new CacheLoader<String, EmployeePO>() {
            @Override
            public EmployeePO load(String key) throws Exception {
                return findEmployeeByName(key);
            }
        };
    }

    private void assertLoadFromDBThenReset(){
        assertThat(true,equalTo(isTrue));
        this.isTrue = false;
    }

    private void assertLoadFromCache(){
        assertThat(true,equalTo(isTrue));
        this.isTrue = false;
    }

    private EmployeePO findEmployeeByName(final String name){

//        System.out.println("emplyee"+ name +"从数据库加载了");
        isTrue = true;
        return new EmployeePO(name,name,name);
    }
}
