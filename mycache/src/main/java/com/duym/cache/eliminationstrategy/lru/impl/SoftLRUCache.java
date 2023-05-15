package com.duym.cache.eliminationstrategy.lru.impl;

import com.duym.cache.eliminationstrategy.lru.LRUCache;
import com.google.common.base.Preconditions;

import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author duym
 * @version $ Id: SoftLRUCache, v 0.1 2023/04/27 14:44 duym Exp $
 */
public class SoftLRUCache<K,V> implements LRUCache<K,V> {

    private static class InternalLRUCache<K,V> extends LinkedHashMap<K, SoftReference<V>> {
        private final int capacity;

        public InternalLRUCache(int capacity) {
            super(16,0.75f,true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, SoftReference<V>> eldest) {
            return this.size() >capacity;
        }
    }

    private final int capacity;

    private final InternalLRUCache<K, V> cache;

    public SoftLRUCache(int capacity) {
        Preconditions.checkArgument(capacity > 0, "容量必须大于0");
        this.capacity = capacity;
        this.cache = new InternalLRUCache(capacity);
    }

    @Override
    public void put(K key, V value) {
        this.cache.put(key,new SoftReference<>(value));
    }

    @Override
    public V get(K key) throws Exception {
        SoftReference<V> reference = this.cache.get(key);
        if(reference == null){
            return null;
        }
        return reference.get();
    }

    @Override
    public void remove(K key) throws Exception {

        this.cache.remove(key);
    }

    @Override
    public int size() {
        return this.cache.size();
    }

    @Override
    public int capacity() {
        return this.cache.capacity;
    }

    @Override
    public void clear() {
        this.cache.clear();
    }
}
