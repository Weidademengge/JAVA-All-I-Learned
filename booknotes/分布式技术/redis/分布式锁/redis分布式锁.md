redis分布式锁是如何实现的？
1. 加过期时间
	如果不加过期时间，进程挂了直接锁死
	Redis 2.6.12之后将set锁和expire设置为原子级操作
2. 加UUID，开启守护线程（开启第二线程，用于对锁续期，一般检测时间为过期时间1/3）
	客户1操作共享资源过久，锁过期，被客户2加锁
	客户1操作完释放客户的锁

（Lua是redis 2.6 版本最大的亮点，通过内嵌对Lua 环境的支持，Redis 解决了长久以来不能高效地处理CAS （check-and-set）命令的缺点，并且可以通过组合使用多个命令，轻松实现以前很难实现或者不能高效实现的模式。）

Redisson将其全部封装完成

以上为高并发场景下使用的redis分布式锁，但在redis主从切换时仍有隐患：
 1. 客户端 1 在主库上执行 SET 命令，加锁成功
 2. 此时，主库异常宕机，SET 命令还未同步到从库上（主从复制是异步的）
 3. 从库被哨兵提升为新主库，这个锁在新的主库上，丢失了！


红锁：

锁太重了，就两步，全部加锁，过半就算加上了，没过半就再试一次，其中UUID当token用，还有redis自带的数据一致性的消息和发布应用。


![[Pasted image 20230212223715.png]]


```java
package com.bilibili.redis;  
  
import org.redisson.Redisson;  
import org.redisson.api.RLock;  
import org.springframework.data.redis.core.StringRedisTemplate;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RestController;  
  
import java.util.UUID;  
import java.util.concurrent.TimeUnit;  
  
@RestController  
public class IndexController {  
  
    private Redisson redisson;  
    private StringRedisTemplate stringRedisTemplate;  
    @RequestMapping("/deduct_stock")  
    public String deductStock(){  
  
        String lockKey = "product_001";  
        String clientId = UUID.randomUUID().toString();  
        RLock redissonLock = redisson.getLock(lockKey);  
  
        try{  
  
            Boolean result = stringRedisTemplate.opsForValue().setIfAbsent(lockKey,clientId,10,TimeUnit.SECONDS);  
  
            redissonLock.lock(30, TimeUnit.SECONDS);  
  
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));  
            if(stock > 0){  
                int realStock = stock - 1;  
                stringRedisTemplate.opsForValue().set("stock",realStock + "");  
                System.out.println("扣减成功，剩余库存：" + realStock + "");  
            }else{  
                System.out.println("扣减失败，库存不足");  
            }  
        }finally{  
            if(clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))){  
                //释放锁  
                redissonLock.unlock();  
            }  
  
        }  
        return "end";  
    }  
  
}
```


zookeeper分布式锁
zookeeper有临时节点、临时有序节点、持久节点、持久有序节点

每个锁就是临时有序节点

添加临时有序节点，对所有抢锁客户端进行排序，创建临时有序节点，用watch检测前一客户端是否释放锁。

问题是zookeeper的锁依赖于心跳机制，如果客户端由于gc问题，无法给出心跳，或者网络延迟，默认锁会失效

![[v2-4822fd574471b8588d41162d4270de8b_1440w.webp]]