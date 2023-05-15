package com.duym.cache.eliminationstrategy.lru.impl;

import com.duym.cache.eliminationstrategy.lru.LRUCache;
import com.google.common.base.Preconditions;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author duym
 * @version $ Id: LRUCacheImpl2, v 0.1 2023/04/26 20:14 duym Exp $
 */
public class LRUCacheImpl2<K, V> implements LRUCache<K, V> {

    private static class InternalLRUCache<K, V> extends LinkedHashMap<K, V> {
        private final int capacity;

        public InternalLRUCache(int capacity) {
            super(16,0.75f,true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > capacity;
        }
    }

    private final int capacity;

    private final InternalLRUCache<K, V> internalLRUCache;

    public LRUCacheImpl2(int capacity) {
        Preconditions.checkArgument(capacity > 0, "容量必须大于0");
        this.capacity = capacity;
        this.internalLRUCache = new InternalLRUCache(capacity);
    }

    @Override
    public void put(K key, V value) {
        this.internalLRUCache.put(key, value);
    }

    @Override
    public V get(K key) throws Exception {
        return this.internalLRUCache.get(key);
    }

    @Override
    public void remove(K key) throws Exception {
        this.internalLRUCache.remove(key);
    }

    @Override
    public int size() {
        return this.internalLRUCache.size();
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public void clear() {
        this.internalLRUCache.clear();
    }

    @Override
    public String toString() {
        return this.internalLRUCache.toString();
    }
}
