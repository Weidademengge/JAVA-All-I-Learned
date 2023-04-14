package com.duym.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version3Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 根据商品总价过滤超过2000元的商品列表
        List<Sku> result = CartV3Service.filterSkus(
                cartSkuList, null,
                2000, false);

        System.out.println(JSON.toJSONString(
                result, true));
    }

}