package com.duym.cache.eliminationstrategy.lru;

public interface LRUCache<K,V>{

    void put(K key,V value);

    V get(K key) throws Exception;

    void remove(K key) throws Exception;

    int size();

    int capacity();

    void clear();
}
