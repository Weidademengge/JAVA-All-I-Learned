package com.duym.cache.eliminationstrategy.lru;

import com.duym.cache.eliminationstrategy.lru.impl.LRUCacheImpl;
import com.duym.cache.eliminationstrategy.lru.impl.LRUCacheImpl2;
import com.duym.cache.eliminationstrategy.lru.impl.SoftLRUCache;

import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: LRUTEst, v 0.1 2023/04/26 22:18 duym Exp $
 */
public class LRUTEst {

    public static void main(String[] args) throws Exception {
//        LRUCacheImpl<Integer,String> cache1 = new LRUCacheImpl<>(3);
//        cache1.put(1,"a");
//        cache1.put(2,"b");
//        cache1.put(3,"c");
//        System.out.println(cache1);
//        cache1.put(4,"d");
//        System.out.println(cache1);
//        cache1.get(2);
//        System.out.println(cache1);

//        LRUCacheImpl2<Integer,String> cache = new LRUCacheImpl2<Integer, String>(3);
//        cache.put(1,"a");
//        cache.put(2,"b");
//        cache.put(3,"c");
//        System.out.println(cache);
//        cache.put(4,"d");
//        System.out.println(cache);
//        cache.get(2);
//        System.out.println(cache);

        final SoftLRUCache<String,byte[]> cache = new SoftLRUCache<>(100);
        for (int i = 0; i < 100; i++) {
            cache.put(String.valueOf(i),new byte[1024 * 1024 * 2]);
            TimeUnit.MILLISECONDS.sleep(500);
            System.out.println("第"+i+"个放入了");
        }
    }
}
