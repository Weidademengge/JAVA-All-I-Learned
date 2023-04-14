## 1. Stream是什么
Stream是一种流，是一种抽象的处理数据的思想，这种编程方式将要处理的元素[集合](https://so.csdn.net/so/search?q=%E9%9B%86%E5%90%88&spm=1001.2101.3001.7020)看作一种流，流在管道中传输，然后在管道的每一个节点上对流进行操作（去重，分组，过滤…），元素流在经过管道的操作后，最后由最终操作得到新的一个元素集合。

通俗点，从数据库得到的list，通过stream可以再操作。例如排序、筛选等

注意：
1. Stream 自己不会储存元素
2. Stream 不会改变源对象 会返回一个持有结果的新Stream
3. Stream 操作时延迟执行的

## 2. Stream操作步骤

Stream三个操作步骤：
1. 创建Stream
2. 中间操作
3. 终止操作

### 2.1 创建Stream四种方法

1. 可以通过Collection 系列集合提供的stream()或parallelStream()  
2. 通过Arrays 中的静态方法stream() 获取数据流 
3. 通过Stream类中的静态方法of() 
4. 创建无限流（迭代、生成）
```java
@Test  
public void test1(){  
    //1. 可以通过Collection 系列集合提供的stream()或parallelStream()  
    List<String> list = new ArrayList<>();  
    Stream<String> stream1 = list.stream();  
  
    //2. 通过Arrays 中的静态方法stream() 获取数据流  
    Employee[] emps =new Employee[10];  
    Stream<Employee> stream2 = Arrays.stream(emps);  
  
    //3. 通过Stream类中的静态方法of()  
    Stream<String> Stream3 =Stream.of("aa","bb","cc");  
  
    //4. 创建无限流  
    //迭代  
    Stream<Integer> stream4 = Stream.iterate(0, (x) -> x+2);  
    stream4.limit(10).forEach(System.out::println);  
  
    //生成  
    Stream.generate(()-> Math.random())  
            .limit(5)  
            .forEach(System.out::println);  
}
```

### 2.2 中见操作

#### 2.2.1筛选与切片
- filter——接收[[Lambda]]，从流中排除某些元素。
- limit——截断流，使其元素不超过给定数量
- skip（n）——跳过元素，返回一个扔掉了前n个元素的流。若流中元素不足n个，则返回一个空流，域limit（n）互补
- distinct——筛选，通过流所生成元素的hashCode（）和equals（）去除重复元素

多个中间操作可以连接起来形成一个流水线，除非流水线上触发终止操作，否则中间操作不会执行任何的处理，而在终止操作时一次性全部处理，成为“惰性求值”。

**filter**
```java
package com.bilibili;  
  
import org.junit.Test;  
import java.util.Arrays;  
import java.util.List;  
import java.util.stream.Stream;  
  
public class TestStreamAPI2 {  
    List<Employee> employees = Arrays.asList(  
            new Employee("张三",18,9999.99),  
            new Employee("李四",38,5555.55),  
            new Employee("王五",50,6666.66),  
            new Employee("赵六",15,7777.77),  
            new Employee("田七",71,8888.88)  
    );  
    //中间操作  ：不执行任何操作
    @Test  
    public void test(){  
        Stream<Employee> stream = employees.stream()  
                .filter((e)->{  
                    System.out.println("StreamAPI中间操作");  
                    return e.getAge()>30;  
                });  

		//终止操作：一次性执行全部内容，即“惰性求值”
        stream.forEach(System.out::println);  
    }  
  
}
```

**limit**
短路操作，后续流不执行
```java
@Test  
public void test2(){  
    employees.stream()  
            .filter((e)-> e.getAge()>30)  
            .limit(2)  
            .forEach(System.out::println);  
}
```

**skip**
跳过操作
```java
@Test  
public void test3(){  
    employees.stream()  
            .filter((e)-> e.getAge()>30)  
            .skip(2)  
            .forEach(System.out::println);  
}
```

**distinct**
去重，但是是根据hashcode和equals去重，需要重写hashcode和equals方法
```java
@Test  
public void test4(){  
    employees.stream()  
            .filter((e)-> e.getAge()>30)  
            .distinct()  
            .forEach(System.out::println);  
}
```

#### 2.2.2 映射

- map-接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
- flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。

**map**
```java
@Test  
public void test5(){  
    List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");  
    list.stream()  
            .map((str)->str.toUpperCase())  
            .forEach(System.out::println);  
    System.out.println("-------------");  
    employees.stream()  
            .map(Employee::getName)  
            .forEach(System.out::println);  
}
```

**flatMap**
这玩意看懂就懂，不懂就算了，太恶心了
```java
@Test  
public void test6(){  
    List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");  
    Stream<Stream<Character>> stream= list.stream()  
            .map(TestStreamAPI2::filterCharacter);  
    stream.forEach((sm) ->{  
        sm.forEach(System.out::println);  
    });  
    System.out.println("-----------------------------------------");  
  
    Stream<Character> sm2 = list.stream()  
            .flatMap(TestStreamAPI2::filterCharacter);  
    sm2.forEach(System.out::println);  
}
```