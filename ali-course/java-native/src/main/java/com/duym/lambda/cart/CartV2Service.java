package com.duym.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 */
public class CartV2Service {

    /**
     * Version 2.0
     * 根据传入商品类型参数，找出购物车中同种商品类型的商品列表
     * @param cartSkuList
     * @param category
     * @return
     */
    public static List<Sku> filterSkusByCategory(
            List<Sku> cartSkuList, SkuCategoryEnum category) {

        List<Sku> result = new ArrayList<Sku>();
        for (Sku sku: cartSkuList) {
            // 如果商品类型 等于 传入商品类型参数
            if (category.equals(sku.getSkuCategory())) {
                result.add(sku);
            }
        }
        return result;
    }

}