package com.duym.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 */
public class CartV3Service {

    /**
     * Version 3.0
     * 支持通过商品类型或总价来过滤商品
     *
     * @param cartSkuList
     * @param category
     * @param totalPrice
     * @param categoryOrPrice - true: 根据商品类型，false: 根据商品总价
     * @return
     */
    public static List<Sku> filterSkus(
            List<Sku> cartSkuList, SkuCategoryEnum category,
            Integer totalPrice, Boolean categoryOrPrice) {

        List<Sku> result = new ArrayList<Sku>();
        for (Sku sku : cartSkuList) {

            // 如果根据商品类型判断，sku类型与输入类型比较
            // 如果根据商品总价判断，sku总价与输入总价比较
            if (
                    (categoryOrPrice
                            && category.equals(sku.getSkuCategory())
                            ||
                            (!categoryOrPrice
                                    && sku.getTotalPrice() > totalPrice))) {
                result.add(sku);
            }
        }
        return result;
    }

}