## 接口简介

BlockingQueue 继承了 Queue 接口，是队列的一种。Queue 和 BlockingQueue 都是在 Java 5 中加入的。

![](https://lang-image-bed.oss-cn-hangzhou.aliyuncs.com/Queue.png)

---

## 方法纵览

我们把 BlockingQueue 中最常用的和添加、删除相关的 8 个方法列出来，并且把它们分为三组，每组方法都和添加、移除元素相关。这三组方法由于功能很类似，所以比较容易混淆。

1.  抛出异常：add、remove、element
2.  返回结果但不抛出异常：offer、poll、peek
3.  阻塞：put、take

### 抛出异常

#### add()

add 方法是往队列里添加一个元素，如果队列满了，就会抛出异常来提示队列已满。

```java
private static void addTest() {
    BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);
    blockingQueue.add(1);
    blockingQueue.add(1);
    blockingQueue.add(1);
}
```

在这段代码中，我们创建了一个容量为 2 的 BlockingQueue，并且尝试往里面放 3 个值，超过了容量上限，那么在添加第三个值的时候就会得到异常：Exception in thread "main" java.lang.IllegalStateException:Queue full

---

#### remove()

remove 方法的作用是删除元素，如果我们删除的队列是空的，由于里面什么都没有，所以也无法删除任何元素，那么 remove 方法就会抛出异常。

```java
private static void removeTest() {
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);
    blockingQueue.add(1);
    blockingQueue.add(1);
    blockingQueue.remove();
    blockingQueue.remove();
    blockingQueue.remove();
}
```

在这段代码中，我们往一个容量为 2 的 BlockingQueue 里放入 2 个元素，并且删除 3 个元素。在删除前面两个元素的时候会正常执行，因为里面依然有元素存在，但是在删除第三个元素时，由于队列里面已经空了，所以便会抛出异常：Exception in thread "main" java.util.NoSuchElementException

---

#### element()

element 方法是返回队列的头部节点，但是并不删除。和 remove 方法一样，如果我们用这个方法去操作一个空队列，想获取队列的头结点，可是由于队列是空的，我们什么都获取不到，会抛出和前面 remove 方法一样的异常：NoSuchElementException。

```java
private static void elementTest() {
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);
    blockingQueue.element();
}
```

---

### 返回结果但不抛出异常

#### offer()

offer 方法用来插入一个元素，并用返回值来提示插入是否成功。如果添加成功会返回 true，而如果队列已经满了，此时继续调用 offer 方法的话，它不会抛出异常，只会返回一个错误提示：false。

```java
private static void offerTest() {
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);
    System.out.println(blockingQueue.offer(1));
    System.out.println(blockingQueue.offer(1));
    System.out.println(blockingQueue.offer(1));
}
```

我们创建了一个容量为 2 的 ArrayBlockingQueue，并且调用了三次 offer方法尝试添加，每次都把返回值打印出来，运行结果如下：  
true  
true  
false

---

#### poll()

poll 方法和第一组的 remove 方法是对应的，作用也是移除并返回队列的头节点。但是如果当队列里面是空的，没有任何东西可以移除的时候，便会返回 null 作为提示。正因如此，我们是不允许往队列中插入 null 的，否则我们没有办法区分返回的 null 是一个提示还是一个真正的元素。

```java
private static void pollTest() {
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(3);
    blockingQueue.offer(1);
    blockingQueue.offer(2);
    blockingQueue.offer(3);
    System.out.println(blockingQueue.poll());
    System.out.println(blockingQueue.poll());
    System.out.println(blockingQueue.poll());
    System.out.println(blockingQueue.poll());
}
```

在这个代码中我们创建了一个容量为 3 的 ArrayBlockingQueue，并且先往里面放入 3 个元素，然后四次调用 poll 方法，运行结果如下：  
```java
1  
2  
3  
null
```

---

#### peek()

peek 方法和第一组的 element 方法是对应的，意思是返回队列的头元素但并不删除。如果队列里面是空的，它便会返回 null 作为提示。

```java
private static void peekTest() {
    ArrayBlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<Integer>(2);
    System.out.println(blockingQueue.peek());
}
```

我们新建了一个空的 ArrayBlockingQueue，然后直接调用 peek，返回结果 null，代表此时并没有东西可以取出。

---

#### 带超时时间的 offer()

```java
offer(E e, long timeout, TimeUnit unit)
```

它有三个参数，分别是元素、超时时长和时间单位。通常情况下，这个方法会插入成功并返回 true；如果队列满了导致插入不成功，在调用带超时时间重载方法的 offer 的时候，则会等待指定的超时时间，如果时间到了依然没有插入成功，就会返回 false。

---

#### 带超时时间的 poll()

```java
poll(long timeout, TimeUnit unit)
```

带时间参数的 poll 方法和 offer 类似：如果能够移除，便会立刻返回这个节点的内容；如果队列是空的就会进行等待，等待时间正是我们指定的时间，直到超时时间到了，如果队列里依然没有元素可供移除，便会返回 null 作为提示。

---

### 阻塞

#### take()

![](https://cdn.nlark.com/yuque/0/2020/png/125693/1608876714075-c53cdebc-8fa5-4fd9-bcdb-ce549a4e0014.png)

take 方法的功能是获取并移除队列的头结点，通常在队列里有数据的时候是可以正常移除的。可是一旦执行 take 方法的时候，队列里无数据，则阻塞，直到队列里有数据。一旦队列里有数据了，就会立刻解除阻塞状态，并且取到数据。

---

#### put()

![](https://cdn.nlark.com/yuque/0/2020/png/125693/1608876713958-cb8a8acd-905e-4d47-a88f-660aa48b2403.png)

put 方法插入元素时，如果队列没有满，那就和普通的插入一样是正常的插入，但是如果队列已满，那么就无法继续插入，则阻塞，直到队列里有了空闲空间。如果后续队列有了空闲空间，比如消费者消费了一个元素，那么此时队列就会解除阻塞状态，并把需要添加的数据添加到队列中。