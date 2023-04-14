package com.duym.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 */
public class CartV7Service {

    public static <T> List<T> filter(List<T> list, Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T e: list){
            if(p.test(e)){
                result.add(e);
            }
        }
        return result;
    }

    public interface Predicate<T>{
        boolean test(T t);
    }

}