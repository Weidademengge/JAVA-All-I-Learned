package com.duym.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version6Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 过滤商品总价大于3000的商品
        List<Sku> result = CartV4Service.filterSkus(
                cartSkuList, (Sku sku) -> sku.getTotalPrice() > 3000);

        System.out.println(JSON.toJSONString(
                result, true));
    }

}