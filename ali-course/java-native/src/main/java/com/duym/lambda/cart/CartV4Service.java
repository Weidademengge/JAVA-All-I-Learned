package com.duym.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 */
public class CartV4Service {

    /**
     * Version 4.0.0
     * 根据不同的Sku判断标准，对Sku列表进行过滤
     * @param cartSkuList
     * @param predicate - 不同的Sku判断标准策略
     * @return
     */
    public static List<Sku> filterSkus(
            List<Sku> cartSkuList, SkuPredicate predicate) {

        List<Sku> result = new ArrayList<Sku>();
        for (Sku sku: cartSkuList) {
            // 根据不同的Sku判断标准策略，对Sku进行判断
            if (predicate.test(sku)) {
                result.add(sku);
            }
        }
        return result;
    }

}