package com.duym.cache.eliminationstrategy;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @author duym
 * @version $ Id: CacheLoaderTest, v 0.1 2023/04/27 18:35 duym Exp $
 */
public class CacheLoaderTest {
    public static void main(String[] args) {
        LoadingCache<String,String> cache = CacheBuilder.newBuilder().maximumSize(10)
                .expireAfterAccess(30, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String s) throws Exception {
                        return null;
                    }
                });
    }
}
