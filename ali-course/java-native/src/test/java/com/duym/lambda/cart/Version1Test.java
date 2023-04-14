package com.duym.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version1Test {

    @Test
    public void filterElectronicsSkus() {
        List<Sku> cartSkuList = CartV1Service.getCartSkuList();

        // 查找购物车中数码类商品
        List<Sku> result =
                CartV1Service.filterElectronicsSkus(cartSkuList);

        System.out.println(
                JSON.toJSONString(result, true));
    }

}