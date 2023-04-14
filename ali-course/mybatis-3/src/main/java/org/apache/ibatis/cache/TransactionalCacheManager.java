/**
 *    Copyright 2009-2023 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.cache;

import org.apache.ibatis.cache.decorators.TransactionalCache;

import java.util.HashMap;
import java.util.Map;

/**
 * {@link TransactionalCache} 管理器
 *
 * @author Clinton Begin
 */
public class TransactionalCacheManager {

    /**
     * Cache 和 TransactionalCache 的映射
     */
    private final Map<Cache, TransactionalCache> transactionalCaches = new HashMap<>();

    /**
     * 清空缓存
     *
     * @param cache Cache 对象
     */
    public void clear(Cache cache) {
        getTransactionalCache(cache).clear();
    }

    /**
     * 获得缓存中，指定 Cache + K 的值。
     *
     * @param cache Cache 对象
     * @param key 键
     * @return 值
     */
    public Object getObject(Cache cache, CacheKey key) {
        // 首先，获得 Cache 对应的 TransactionalCache 对象
        // 然后从 TransactionalCache 对象中，获得 key 对应的值
        return getTransactionalCache(cache).getObject(key);
    }

    /**
     * 添加 Cache + KV ，到缓存中
     *
     * @param cache Cache 对象
     * @param key 键
     * @param value 值
     */
    public void putObject(Cache cache, CacheKey key, Object value) {
        // 首先，获得 Cache 对应的 TransactionalCache 对象
        // 然后，添加 KV 到 TransactionalCache 对象中
        getTransactionalCache(cache).putObject(key, value);
    }

    /**
     * 提交所有 TransactionalCache
     */
    public void commit() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.commit();
        }
    }

    /**
     * 回滚所有 TransactionalCache
     */
    public void rollback() {
        for (TransactionalCache txCache : transactionalCaches.values()) {
            txCache.rollback();
        }
    }

    /**
     * 获得 Cache 对应的 TransactionalCache 对象
     *
     * @param cache Cache 对象
     * @return TransactionalCache 对象
     */
    private TransactionalCache getTransactionalCache(Cache cache) {
        return transactionalCaches.computeIfAbsent(cache, TransactionalCache::new);
    }

}
