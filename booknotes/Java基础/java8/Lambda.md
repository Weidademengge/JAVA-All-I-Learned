
## 1.Lambda表达式

Lambda 表达式是 JDK8 的一个新特性，可以取代大部分的匿名内部类，写出更优雅的 Java 代码，尤其在集合的遍历和其他集合操作中，可以极大地优化代码结构。

```java
package com.bilibili;  
  
import org.junit.Test;  
import java.util.Comparator;  
import java.util.TreeSet;  
  
public class TestLambda {  
    //原来的匿名内部类  
    @Test  
    public  void test1(){  
        Comparator<Integer> com = new Comparator<Integer>() {  
            @Override  
            public int compare(Integer o1, Integer o2) {  
                return Integer.compare(o1,o2);  
            }  
        };  
        TreeSet<Integer> ts = new TreeSet<>(com);  
    }  
  
    //Lambda表达式  
    @Test  
    public void test2(){  
        Comparator<Integer> com = (x,y) -> Integer.compare(x,y);  
        TreeSet<Integer> ts = new TreeSet<>(com);  
    }  
}
```


## 2.lambda的优化能力

假如有个需求，一会儿一变，怎么写

### 2.1正常写
```java
package com.bilibili;  
  
import org.junit.Test;  
  
import java.util.*;  
  
/**  
 * @author 杜雨萌  
 * @version $ Id: TestLambda, v 0.1 2023/02/20 20:54 banma-0163 Exp $  
 */public class TestLambda {  

    List<Employee> employees = Arrays.asList(  
            new Employee("张三",18,9999.99),  
            new Employee("李四",38,5555.55),  
            new Employee("王五",50,6666.66),  
            new Employee("赵六",15,7777.77),  
            new Employee("田七",71,8888.88)  
    );  
     
    @Test  
    public void test3(){  
        List<Employee> list = filterEmployee(employees);  
        for(Employee employee:list){  
            System.out.println(employee);  
        }  
    }  
	//需求：获取当前公司员工年龄大于35的员工信息 
    public List<Employee> filterEmployee(List<Employee> list){  
        List<Employee> emps = new ArrayList<>();  
        for(Employee emp :list){  
            if(emp.getAge()>=35){  
                emps.add(emp);  
            }  
        }  
        return emps;  
    }  
  
    //需求：获取当前公司中员工工资大于5000的员工信息  
    public List<Employee> filterEmployee2(List<Employee> list){  
        List<Employee> emps = new ArrayList<>();  
        for(Employee emp :list){  
            if(emp.getSalary()>=5000){  
                emps.add(emp);  
            }  
        }  
        return emps;  
    }
}
```

### 2.2优化一策略模式:

采用接口+实现类
```java
package com.bilibili;  
  
/**  
 * @author 杜雨萌  
 * @version $ Id: MyPredicate, v 0.1 2023/02/20 21:17 banma-0163 Exp $  
 */public interface MyPredicate<T> {  
  
    public boolean test(T t);  
}
```

年龄实现类：
```java
package com.bilibili;  
  
/**  
 * @author 杜雨萌  
 * @version $ Id: FilterEmployeeByAge, v 0.1 2023/02/20 21:18 banma-0163 Exp $  
 */public class FilterEmployeeByAge implements MyPredicate<Employee>{  
  
    @Override  
    public boolean test(Employee t) {  
        return t.getAge() > 35;  
    }  
}
```

薪资实现类
```java
package com.bilibili;  
  
/**  
 * @author 杜雨萌  
 * @version $ Id: FilterEmployeeSalary, v 0.1 2023/02/20 21:26 banma-0163 Exp $  
 */public class FilterEmployeeSalary implements MyPredicate<Employee>{  
    @Override  
    public boolean test(Employee t) {  
        return t.getSalary()>6000;  
    }  
}
```

方法：
```java
package com.bilibili;  
  
import org.junit.Test;  
  
import java.util.*;  
  
public class TestLambda {  

    List<Employee> employees = Arrays.asList(  
            new Employee("张三",18,9999.99),  
            new Employee("李四",38,5555.55),  
            new Employee("王五",50,6666.66),  
            new Employee("赵六",15,7777.77),  
            new Employee("田七",71,8888.88)  
    );  

    //优化方式一：策略设计模式  
    @Test  
    public void test4(){  
        List<Employee> list1 = filterEmployee(employees, new FilterEmployeeByAge());  
  
        for(Employee employee:list1){  
            System.out.println(employee);  
        }  
        System.out.println("-------------------");  
  
        List<Employee> list2 = filterEmployee(list1, new FilterEmployeeSalary());  
        for(Employee employee:list2){  
            System.out.println(employee);  
        }  
    }  
  
    public List<Employee> filterEmployee(List<Employee> list,MyPredicate<Employee> mp){  
        List<Employee> emps = new ArrayList<>();  
  
        for(Employee employee:list){  
            if(mp.test(employee)){  
                emps.add(employee);  
            }  
        }  
        return emps;  
    };  
}
```

### 2.3 优化二：匿名内部类

```java
@Test  
public void test5(){  
    List<Employee> list = filterEmployee(employees, new MyPredicate<Employee>() {  
        @Override  
        public boolean test(Employee t) {  
            return t.getSalary() <= 6000;  
        }  
    });  
    for(Employee employee:list){  
        System.out.println(employee);  
    }  
}
```

### 2.4 优化三：Lambda表达式

```java
//优化方式三：Lambda表达式
@Test  
public void test6(){  
    List<Employee> list = filterEmployee(employees, (e)->e.getSalary()<= 6000);  
    list.forEach(System.out::println);  
}
```

### 2.5 优化四：stream流

不需要接口和匿名内部类
```java
//优化方式四：Lambda表达式  
package com.bilibili;  
  
import org.junit.Test;  
import java.util.*;  
  
public class TestLambda {  
  
    List<Employee> employees = Arrays.asList(  
            new Employee("张三",18,9999.99),  
            new Employee("李四",38,5555.55),  
            new Employee("王五",50,6666.66),  
            new Employee("赵六",15,7777.77),  
            new Employee("田七",71,8888.88)  
    );  
  
    //优化方式四：Stream API  
	@Test  
	public void test7(){  
	    employees.stream()  
	            .filter((e)->e.getSalary()<6000)  
	            .limit(2)  
	            .forEach(System.out::println);  
	    System.out.println("--------------------");  
	    employees.stream()  
	            .map(Employee::getName)  
	            .forEach(System.out::println);  
	}
}
```

## 3.lambda基础语法

Lambda 表达式的基础语法：Java8中引入了一个新的操作符"->"该操作符成为箭头操作符或Lambda操作符，箭头操作符将Lambda表达式拆分成两部分

- 左侧：Lambda表达式参数列表
- 右侧：lambda表达式所需执行的功能，即lambda体

### 3.1 语法格式

格式一：无参，无返回值
				（）-> System.out.println("hello world")
```java
package com.bilibili;  

import org.junit.Test;  

public class TestLambda2 {  
      
    int num = 0;//jdk 1.7前，必须是final,1.8以后不用加，但还是final  
  
	@Test  
	public void test1(){  
	    Runnable r1 = new Runnable() {  
	        @Override  
	        public void run() {  
	            System.out.println("hello world" + num);  
	        }  
	    };  
	    r1.run();  
	    System.out.println("-----------------");  
	    Runnable r2 = () -> System.out.println("hello lambda");  
	    r2.run();  
	}
}
```

格式二： 有一个参数，无返回值，借助java本身自带的Consumer接口
			（x）-> System.out.println(x)
```java
package com.bilibili;  
  
import org.junit.Test;  
import java.util.function.Consumer;  
  
public class TestLambda2 {  
  
    @Test  
    public void test2(){  
        Consumer<String> con = (x) -> System.out.println(x);  
        con.accept("hahaha");  
    }  
}
```

格式三： 若有一个参数，小括号可以省略不写
``` java
@Test  
public void test2(){  
    Consumer<String> con = x -> System.out.println(x);  
    con.accept("hahaha");  
}
```

格式四：有两个以上的参数，lambda体中有多条语句，有返回值

```java
@Test  
public void test3(){  
    Comparator<Integer> com = (x,y) -> {  
        System.out.println("函数式接口");  
        return Integer.compare(x,y);  
    };  
}
```

格式五：若Lambda体中只有一条语句，return和大括号都可以省略不写

```java
@Test  
public void test3(){  
    Comparator<Integer> com = (x,y) -> Integer.compare(x,y);  
}
```

格式五：Lambda表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即"类型推断"

Lambda表达式需要“函数式接口”的支持
函数式接口：接口中只有一个抽象方法的接口，成为函数式接口。可以使用注解@FunctionalInterface修饰，可以检查是否式函数式接口（spring中ApplicationListener接口用的此注解）

### 4. lambda表达式练习

```java
@Test  
public void test(){  
    Collections.sort(employees,(e1,e2)->{  
        if(e1.getAge() == e2.getAge()){  
            return e1.getName().compareTo(e2.getName());  
        }else{  
            return Integer.compare(e1.getAge(),e2.getAge());  
        }  
    });  
    employees.forEach(System.out::print);  
}
```

## 5.lambda内置四大核心函数式接口

### 5.1 消费型接口
```java
//Conssumer<T> ：消费型接口
//		void accept(T t);
@Test  
public void test(){  
    Collections.sort(employees,(e1,e2)->{  
        if(e1.getAge() == e2.getAge()){  
            return e1.getName().compareTo(e2.getName());  
        }else{  
            return Integer.compare(e1.getAge(),e2.getAge());  
        }  
    });  
    employees.forEach(System.out::print);  
}
```

### 5.2 供给型接口

```java
//Supplier<T>:
//		T get（）；
//需求：产生指定个数的整数，并放入集合中  
@Test  
public void test2(){  
    List<Integer> list = getNumList(10, () -> (int) (Math.random() * 100));  
    list.forEach(System.out::println);  
}  
  
public List<Integer> getNumList(int num, Supplier<Integer> sup){  
    List<Integer> list = new ArrayList<>();  
    for(int i = 0;i<num;i++){  
        int n = sup.get();  
        list.add(n);  
    }  
    return list;  
}
```

### 5.3 函数型接口

```java
//Function<T,R>:函数型接口
//		R apply(T t);
//需求：用于处理字符串  
    @Test  
    public void test3(){  
        String s = strHandler("\t\t\t 我去我去我去", (str) -> str.trim());  
        System.out.println(s);  
  
    }  
  
    public String strHandler(String str, Function<String,String> fun){  
        return fun.apply(str);  
    };  
}
```

### 5.4 断言型接口
```java
//Predicate<T> 断言型接口：  
//需求：将满足条件的字符串，放入集合中  
@Test  
public void test4(){  
    List<String> list = Arrays.asList("hello","world","lambda","aa","ok");  
    List<String> stringList = filterStr(list, (s) -> s.length() > 3);  
    stringList.forEach(System.out::println);  
}  
  
public List<String> filterStr(List<String> list , Predicate<String> pre){  
    List<String> strList = new ArrayList<>();  
    for(String str:list){  
        if(pre.test(str)){  
            strList.add(str);  
        }  
    }  
    return strList;  
}
```

## 6. 方法引用和构造器引用

方法引用：若lambda体中的内容有方法已经实现了，我们可以使用“方法引用
				（可以理解为方法引用时lambda表达式的另外一种表现形式）
构造器引用：
				格式：
				ClassName：：new

### 6.1 语法格式

主要有三种语法格式：
- 对象：：实例方法名
- 类   ：：静态方法名
- 类   ：：实例方法名

### 6.2 注意
Lambda体中调用方法的参数列表域返回值类型，要与函数式接口中抽象方法的函数列表和返回值类型保持一致
若Lambda参数列表中第一参数是实例方法的调用者，而第二参数是实例方法的参数时，可以使用ClassName：：method
需要调用的构造方法的参数列表要与函数式接口中构造方法的参数列表一致

对象：：实例方法名
```java
public class TestMethodRef {  
  
    //对象：：实例方法名  
    @Test  
    public void test1(){  
        PrintStream ps1 = System.out;  
        Consumer<String> con = (x) -> ps1.println(x);  
		//--------------------------------------------------
        PrintStream ps = System.out;  
        Consumer<String> con1 = ps::println;  
        //--------------------------------------------------
        Consumer<String> con2 = System.out::println;
        con2.accept("abcd")
    }  
}
```


类   ：：静态方法名
```java
public class TestMethodRef {  
    //类::静态方法名  
    @Test  
    public void test2(){  
        Comparator<Integer> com = (x,y)->Integer.compare(x,y);  
        Comparator<Integer> com1 = Integer::compare;  
    }  
}
```


类   ：：实例方法名
```java
public class TestMethodRef {  
    //类::实例方法名  
	@Test  
	public void test3(){  
	    BiPredicate<String,String> bp = (x,y)-> x.equals(y);  
	    BiPredicate<String,String> bp2 = String::equals;  
	    System.out.println(bp2.test("aaa","bbb"));  
	}
}
```






