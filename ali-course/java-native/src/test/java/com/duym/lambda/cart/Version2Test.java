package com.duym.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version2Test {

    @Test
    public void filterSkusByCategory() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 查找购物车中图书类商品集合
        List<Sku> result = CartV2Service.filterSkusByCategory(
                cartSkuList, SkuCategoryEnum.BOOKS);

        System.out.println(JSON.toJSONString(
                result, true));
    }

}