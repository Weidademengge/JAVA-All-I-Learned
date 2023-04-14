
IDEA 格式化代码快捷键  
Mac：Command + option + L  
Win：Ctrl + Alt + L

# 代码实践

## 搭建工程

### 新建空项目：alibaba-faw-demo

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676421393150-7637f161-0714-4cb9-8096-10f74b55d2cb.png)

---

### 新建Java原生技术项目：java-native

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676421455746-95971186-65a4-4d00-b897-0cb874dbf262.png)

---

### 设置项目缺省SDK

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676421505334-48374809-2572-46f0-b3b9-8626ada43a7a.png)

---

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676421526450-645e0895-fbfc-43e3-aa61-10b79e5a3ff8.png)

---

### 新建包名：lambda.cart

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676421685251-669579b9-ae20-4d73-adad-b5bae8364281.png)

---

### 新建类：Sku

```java
package com.ebanma.cloud.lambda.cart;

/**
 * 下单商品信息对象
 */
public class Sku {
    // 编号
    private Integer skuId;
    // 商品名称
    private String skuName;
    // 单价
    private Integer skuPrice;
    // 购买个数
    private Integer totalNum;
    // 总价
    private Integer totalPrice;
    // 商品类型
    private Enum skuCategory;

    /**
     * 构造函数
     * @param skuId
     * @param skuName
     * @param skuPrice
     * @param totalNum
     * @param totalPrice
     * @param skuCategory
     */
    public Sku(Integer skuId, String skuName,
               Integer skuPrice, Integer totalNum,
               Integer totalPrice, Enum skuCategory) {
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuPrice = skuPrice;
        this.totalNum = totalNum;
        this.totalPrice = totalPrice;
        this.skuCategory = skuCategory;
    }

    /**
     * Get方法
     * @return
     */
    public Integer getSkuId() {
        return skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public Integer getSkuPrice() {
        return skuPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Enum getSkuCategory() {
        return skuCategory;
    }
}
```

---

### 新建枚举类：SkuCategory

```java
package com.ebanma.cloud.lambda.cart;

/**
 * 商品类型枚举
 */
public enum SkuCategoryEnum {
    CLOTHING(10, "服装类"),
    ELECTRONICS(20, "数码类"),
    SPORTS(30, "运动类"),
    BOOKS(40, "图书类");

    // 商品类型的编号
    private Integer code;
    // 商品类型的名称
    private String name;

    /**
     * 构造函数
     * @param code
     * @param name
     */
    SkuCategoryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }
}
```
---

## 第一版需求

找出购物中所有电子产品

---

### 新建购物车服务类：CartV1Service

```java
package com.ebanma.cloud.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 */
public class CartV1Service {

    // 加入到购物车中的商品信息
    private static List<Sku> cartSkuList =
            new ArrayList<Sku>(){
        {
            add(new Sku(654032, "无人机",
                    4999, 1,
                    4999, SkuCategoryEnum.ELECTRONICS));

            add(new Sku(642934, "VR一体机",
                    2299, 1,
                    2299, SkuCategoryEnum.ELECTRONICS));

            add(new Sku(645321, "纯色衬衫",
                    409, 3,
                    1227, SkuCategoryEnum.CLOTHING));

            add(new Sku(654327, "牛仔裤",
                    528, 1,
                    528, SkuCategoryEnum.CLOTHING));

            add(new Sku(675489, "跑步机",
                    2699, 1,
                    2699, SkuCategoryEnum.SPORTS));

            add(new Sku(644564, "Java编程思想",
                    79, 1,
                    79, SkuCategoryEnum.BOOKS));

            add(new Sku(678678, "Java核心技术",
                    149, 1,
                    149, SkuCategoryEnum.BOOKS));

            add(new Sku(697894, "数据结构与算法",
                    78, 1,
                    78, SkuCategoryEnum.BOOKS));

            add(new Sku(696968, "TensorFlow进阶指南",
                    85, 1,
                    85, SkuCategoryEnum.BOOKS));
        }
    };

    /**
     * 获取商品信息列表
     * @return
     */
    public static List<Sku> getCartSkuList() {
        return cartSkuList;
    }

    /**
     * Version 1.0
     * 找出购物车中所有电子产品
     * @param cartSkuList
     * @return
     */
    public static List<Sku> filterElectronicsSkus(
            List<Sku> cartSkuList) {

        List<Sku> result = new ArrayList<Sku>();
        for (Sku sku: cartSkuList) {
            // 如果商品类型 等于 电子类
            if (SkuCategoryEnum.ELECTRONICS.
                    equals(sku.getSkuCategory())) {
                result.add(sku);
            }
        }
        return result;
    }
}
```

---

### 新建V1测试类：Version1Test

  

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676422434083-026336b5-8cfa-4cc0-af1e-9d46429f1f0d.png)

---

### 加入相关依赖

  

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.ebanma.cloud</groupId>
    <artifactId>java-native</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
    </properties>

    <dependencies>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.58</version>
        </dependency>

        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

---

### 完成V1测试类并运行

  

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

测试结果：

  

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676432087571-ce7167aa-9f6c-4285-a4ab-170b581c65f4.png)

---

## 第二版需求

根据传入商品类型参数，找出购物车中同种商品类型的商品列表

---

### 新建购物车服务类：CartV2Service

  

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

### 抽取公共服务类：BaseCartService

  

```java
package com.ebanma.cloud.lambda.cart;

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
                            4999, 1,
                            4999, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(642934, "VR一体机",
                            2299, 1,
                            2299, SkuCategoryEnum.ELECTRONICS));

                    add(new Sku(645321, "纯色衬衫",
                            409, 3,
                            1227, SkuCategoryEnum.CLOTHING));

                    add(new Sku(654327, "牛仔裤",
                            528, 1,
                            528, SkuCategoryEnum.CLOTHING));

                    add(new Sku(675489, "跑步机",
                            2699, 1,
                            2699, SkuCategoryEnum.SPORTS));

                    add(new Sku(644564, "Java编程思想",
                            79, 1,
                            79, SkuCategoryEnum.BOOKS));

                    add(new Sku(678678, "Java核心技术",
                            149, 1,
                            149, SkuCategoryEnum.BOOKS));

                    add(new Sku(697894, "数据结构与算法",
                            78, 1,
                            78, SkuCategoryEnum.BOOKS));

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
```

---

### 新建V2测试类：Version2Test

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

## 第三版需求

支持通过商品类型或总价来过滤商品

---

### 新建购物车服务类：CartV3Service

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

### 新建V3测试类：Version3Test

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

## 第四版需求

根据不同的Sku判断标准，不仅局限于某些固定的标准，对Sku列表进行过滤

---

### 新建判断标准接口：SkuPredicate

```java
package com.ebanma.cloud.lambda.cart;

/**
 * Sku选择谓词接口
 */
public interface SkuPredicate {

    /**
     * 选择判断标准
     * @param sku
     * @return
     */
    boolean test(Sku sku);

}
```

---

### 新建购物车服务类：CartV4Service

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

### 新建判断标准实现类

```java
package com.ebanma.cloud.lambda.cart;

/**
 * 对Sku的商品类型为图书类的判断标准
 */
public class SkuBooksCategoryPredicate implements SkuPredicate {
    @Override
    public boolean test(Sku sku) {
        return SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory());
    }
}

  

package com.ebanma.cloud.lambda.cart;

/**
 * 对Sku的总价是否超出2000作为判断标准
 */
public class SkuTotalPricePredicate implements SkuPredicate {
    @Override
    public boolean test(Sku sku) {
        return sku.getTotalPrice() > 2000;
    }
}
```

---

### 新建V4测试类：Version4Test

```java
package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version4Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 过滤商品总价大于2000的商品
        List<Sku> result = CartV4Service.filterSkus(
                cartSkuList, new SkuBooksCategoryPredicate());

        System.out.println(JSON.toJSONString(
                result, true));
    }

}
```

  
这里值得停下来小小地庆祝一下。这段代码比我们第一次尝试的时候灵活多了，读起来、用起来也更容易！现在你可以创建不同的SkuPredicate 对象，并将它们传递给filterSkus 方法。免费的灵活性！

---

### 解析：通过行为参数化传递代码

软件工程中一个众所周知的问题就是，不管你做什么，用户的需求肯定会变。

**行为参数化** 就是可以帮助你处理频繁变更的需求的一种软件开发模式。

一言以蔽之，它意味着拿出一个代码块，把它准备好却不去执行它。这个代码块以后可以被你程序的其他部分调用，这意味着你可以推迟这块代码的执行。例如，你可以将代码块作为参数传递给另一个方法，稍后再去执行它。这样，这个方法的行为就基于那块代码被参数化了。

---

## 第五版需求

我们都知道，人们不愿意用那些很麻烦的功能或概念。目前，当要把新的行为传递给filterSkus 方法的时候，你不得不声明好几个实现SkuPredicate 接口的类，然后实例化好几个只会提到一次的SkuPredicate 对象。

能不能做得更好呢？

---

Java有一个机制称为**匿名类** ，它可以让你同时声明和实例化一个类。它可以帮助你进一步改善代码，让它变得更简洁。

**匿名类** 和你熟悉的Java局部类（块中定义的类）差不多，但匿名类没有名字。它允许你同时声明并实例化一个类。换句话说，它允许你随用随建。

---

### 新建V5测试类：Version5Test

```java
package com.ebanma.cloud.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

public class Version5Test {

    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = BaseCartService.getCartSkuList();

        // 过滤商品总价大于3000的商品
        List<Sku> result = CartV4Service.filterSkus(
                cartSkuList, new SkuPredicate() {
                    @Override
                    public boolean test(Sku sku) {
                        return sku.getSkuPrice() > 3000;
                    }
                });

        System.out.println(JSON.toJSONString(
                result, true));
    }

}
```

---

但匿名类还是不够好。

第一，它往往很笨重，因为它占用了很多空间。

第二，很多程序员觉得它用起来很让人费解。

好的代码应该是一目了然的。即使匿名类处理在某种程度上改善了为一个接口声明好几个实体类的啰唆问题，但它仍不能令人满意。在只需要传递一段简单的代码时（例如表示选择标准的boolean 表达式），你还是要创建一个对象，明确地实现一个方法来定义一个新的行为（例如Predicate 中的test 方法）。

---

## 第六版需求

在理想的情况下，我们想鼓励程序员使用行为参数化模式，因为正如你在前面看到的，它让代码更能适应需求的变化。接下来，你会看到Java 8的语言设计者通过引入Lambda表达式——一种更简洁的传递代码的方式——解决了这个问题。

---

### 新建V6测试类：Version6Test

```java
package com.ebanma.cloud.lambda.cart;

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
```

不得不承认这段代码看上去比先前干净很多。这很好，因为它看起来更像问题陈述本身了。

---

总结：行为参数化有哪些方式？

1.  类
2.  匿名类
3.  Lambda

---

## 第七版需求

在通往抽象的路上，还可以更进一步。目前，filterSkus 方法还只适用于Sku 。你还可以将List 类型抽象化，从而超越你眼前要处理的问题

---

### 新建购物车服务类：CartV7Service

```java
package com.ebanma.cloud.lambda.cart;

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
```

---

# 函数编程

---

## Lambda表达式的简介

-   Java 8 引入函数式编程风格
-   可以理解为一种匿名函数的替代
-   通过行为参数化传递代码

---

## Lambda表达式的语法

-   (parameters) -> expression
-   (parameters) -> { statements; }

Java语言设计者选择这样的语法，是因为C#和Scala等语言中的类似功能广受欢迎。JavaScript也有类似的语法。Lambda的基本语法是（被称为**表达式–风格** 的Lambda）

---

## Lambda表达式的形式一

-   没有参数

```java
() -> System.out.println("Hello World");
```

---

## Lambda表达式的形式二

-   只有一个参数

```java
name -> System.out.println("Hello World from " + name + "!");
```

---

## Lambda表达式的形式三

-   没有参数，逻辑复杂

```java
() -> {
    System.out.println("Hello");
    System.out.println("World");
}
```

---

## Lambda表达式的形式四

-   包含两个参数的方法

```java
BinaryOperator<Long> functionAdd = (x, y) -> x + y;
Long result = functionAdd.apply(1L, 2L);
```

---

## Lambda表达式的形式五

-   对参数显示声明

```java
BinaryOperator<Long> functionAdd = (Long x, Long y) -> x + y;
Long result = functionAdd.apply(1L, 2L);
```

---

## 测验：Lambda语法

```java
() -> {};

() -> "Raoul";

() -> {return "Mario";};

(Integer i) -> return "Alan" + i;

(String s) -> {"Iron Man";};
```

---

## 函数式接口

-   接口只有一个抽象方法
-   Java8 的函数式接口注解：[@FunctionInterface](/FunctionInterface)
-   函数式接口的抽象方法签名：函数描述符

```java
@FunctionalInterface
public interface Runnable {
    public abstract void run();
}
```

---

```java
public void process(Runnable r){
    r.run();
}
process(() -> System.out.println("This is awesome!!"));`
```

---

## 代码实践

实战：自定义函数式接口，实现读取本地文件后自定义处理逻辑功能

---

### 新建函数式接口：FileConsumer

  

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676455408262-88f11c1b-7d1e-4b99-9b1c-437c1488aa6e.png)

---

```java
package com.ebanma.cloud.lambda.file;

/**
 * 文件处理函数式接口
 */
@FunctionalInterface
public interface FileConsumer {

    /**
     * 函数式接口抽象方法
     * @param fileContent - 文件内容字符串
     */
    void fileHandler(String fileContent);

}
```

---

### 新建文件服务类：FileService

```java
package com.ebanma.cloud.lambda.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 文件服务类
 */
public class FileService {

    /**
     * 从过url获取本地文件内容，调用函数式接口处理
     * @param url
     * @param fileConsumer
     */
    public void fileHandle(String url, FileConsumer fileConsumer)
            throws IOException {

        // 创建文件读取流
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(url)));

        // 定义行变量和内容sb
        String line;
        StringBuilder stringBuilder = new StringBuilder();

        // 循环读取文件内容
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line + "\n");
        }

        // 调用函数式接口方法，将文件内容传递给lambda表达式，实现业务逻辑
        fileConsumer.fileHandler(stringBuilder.toString());
    }

}
```

---

### 新建文件服务测试类：FileServiceTest

```java
package com.ebanma.cloud.lambda.file;

import org.junit.Test;

import java.io.IOException;

public class FileServiceTest {

    @Test
    public void fileHandle() throws IOException {
        FileService fileService = new FileService();

        // TODO 此处替换为本地文件的地址全路径
        String filePath = "";

        // 通过lambda表达式，打印文件内容
        fileService.fileHandle(filePath,
                System.out::println);
    }
}
```

---

构建测试数据，复制任意文件绝对路径，例如：/Users/xlang/Code/train/alibaba-faw-demo/java-native/src/test/java/com/ebanma/cloud/lambda/file/FileServiceTest.java

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676455751586-b6b1be41-5735-4ebd-a18f-33fd1c4c93ed.png)

---

## 方法引用的简介

调用特定方法的Lambda表达式的一种快捷写法，可以让你重复使用现有的方法定义，并像Lambda表达式一样传递他们。

---

## 方法引用的语法

![](https://cdn.nlark.com/yuque/0/2023/png/125693/1676449751725-a9ad8d6e-e341-48d3-a23d-d836ed094f4a.png)

---

## 方法引用的形式一

-   指向静态方法的方法引用

  

```java
public void test1() {
	Consumer<String> consumer1 = (String number) -> Integer.parseInt(number);

    Consumer<String> consumer2 = Integer::parseInt;
}
```

---

## 方法引用的形式二

-   指向任意类型的实例方法的方法引用


```java
public void test2() {
	Consumer<String> consumer1 = (String str) -> str.length();

    Consumer<String> consumer2 = String::length;
}
```

---

## 方法引用的形式三

-   指向现有对象的实例方法的方法引用

```java
public void test3() {
	StringBuilder stringBuilder = new StringBuilder();
    
	Consumer<String> consumer1 = (String str) -> stringBuilder.append(str);

    Consumer<String> consumer2 = stringBuilder::append;
}
```

---

## 你为什么应该关注方法引用？

你可以把方法引用看作针对仅仅涉及单一方法的Lambda的语法糖，因为你表达同样的事情时要写的代码更少了。

---