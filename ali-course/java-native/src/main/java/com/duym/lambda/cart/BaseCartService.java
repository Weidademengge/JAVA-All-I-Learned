package com.duym.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务基类
 */
public abstract class BaseCartService {

    // 加入到购物车中的商品信息
    private static List<Sku> cartSkuList =
            new ArrayList<Sku>(){
                {
                    add(new Sku(654032, "无人机",
                            4999.0, 1,
                            4999.0, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(642934, "VR一体机",
                            2299.0, 1,
                            2299.0, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(645321, "纯色衬衫",
                            409.0, 3,
                            1227.0, SkuCategoryEnum.CLOTHING));

                    add(new Sku(654327, "牛仔裤",
                            528.0, 1,
                            528.0, SkuCategoryEnum.CLOTHING));

                    add(new Sku(675489, "跑步机",
                            2699.0, 1,
                            2699.0, SkuCategoryEnum.SPORTS));

                    add(new Sku(644564, "Java编程思想",
                            79.0, 1,
                            79.0, SkuCategoryEnum.BOOKS));

                    add(new Sku(678678, "Java核心技术",
                            149.0, 1,
                            149.0, SkuCategoryEnum.BOOKS));

                    add(new Sku(697894, "数据结构与算法",
                            78.0, 1,
                            78.0, SkuCategoryEnum.BOOKS));

                    add(new Sku(696968, "TensorFlow进阶指南",
                            85.10, 1,
                            85.10, SkuCategoryEnum.BOOKS));
                }
            };

    /**
     * 获取商品信息列表
     * @return
     */
    public static List<Sku> getCartSkuList() {
        return cartSkuList;
    }
}