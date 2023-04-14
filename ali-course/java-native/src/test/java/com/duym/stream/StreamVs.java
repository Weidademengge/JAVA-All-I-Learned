package com.duym.stream;

import com.alibaba.fastjson.JSON;
import com.duym.lambda.cart.BaseCartService;
import com.duym.lambda.cart.Sku;
import com.duym.lambda.cart.SkuCategoryEnum;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author duym
 * @version $ Id: StreamVs, v 0.1 2023/04/06 17:48 duym Exp $
 */
public class StreamVs {

    /**
     * 1. 想看看购物车中都有什么商品
     * 2. 除了图书类商品都给买
     * 3. 其余的商品中买两件最贵的
     * 4. 只需要两件商品的名称和总价
     */

    @Test
    public void oldCartHandle(){

        // 1. 想看看购物车中都有什么商品
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();
        // 2. 除了图书类商品都给买
        List<Sku> notBooks = new ArrayList<>();
        for (Sku sku : cartSkuList) {
            if(!SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory())){
                notBooks.add(sku);
            }
        }
        // 3. 其余的商品中买两件最贵的
        notBooks.sort(Comparator.comparing(Sku::getTotalPrice).reversed());

        List<Sku> top2Books = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            top2Books.add(notBooks.get(i));
        }

        for (Sku sku : top2Books) {
            System.out.println(JSON.toJSONString(sku,true));
        }
        // 4. 只需要两件商品的名称和总价
        double money = 0;
        List<String> names = new ArrayList<>();
        for (Sku sku : top2Books) {
            money += sku.getTotalPrice();
            names.add(sku.getSkuName());
        }
        System.out.println("商品金额:"+money);
        System.out.println("商品名称："+JSON.toJSONString(names,true));
    }

    @Test
    public void newCartHandle(){
        AtomicInteger money = new AtomicInteger(0);
        List<String> names = BaseCartService.getCartSkuList().stream()
                // 1. 想看看购物车中都有什么商品
                .peek(sku -> System.out.println(JSON.toJSONString(sku, true)))
                // 2. 除了图书类商品都给买
                .filter(sku -> !SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory()))
                // 3.其余的商品中买两件最贵的
                .sorted(Comparator.comparing(Sku::getTotalPrice).reversed())
                .limit(2)
                // 4.只需要两件商品的名称和总价
                .peek(sku -> money.set((int) (money.get() + sku.getTotalPrice())))
                .map(sku -> sku.getSkuName())
                .collect(Collectors.toList());
        System.out.println("商品金额:"+money);
        System.out.println("商品名称："+JSON.toJSONString(names,true));

    }
}
