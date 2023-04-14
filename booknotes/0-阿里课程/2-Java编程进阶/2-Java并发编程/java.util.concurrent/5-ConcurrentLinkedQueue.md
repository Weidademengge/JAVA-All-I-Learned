## 类简介

-   使用链表作为其数据结构的，利用 CAS 非阻塞算法 + 不停重试来实现线程安全。
-   适合用在不需要阻塞功能，且并发不是特别剧烈的场景。

---

## 源码分析

### offer()

```java
public boolean offer(E e) {
    checkNotNull(e);
	final Node<E> newNode = new Node<E>(e);
	for (Node<E> t = tail, p = t;;) {
	    Node<E> q = p.next;
	    if (q == null) {
	        // p is last node
	        if (p.casNext(null, newNode)) {
	            // Successful CAS is the linearization point
	            // for e to become an element of this queue,
	            // and for newNode to become "live".
	            if (p != t) // hop two nodes at a time
	                casTail(t, newNode);  // Failure is OK.
	            return true;
	        }
	        // Lost CAS race to another thread; re-read next
	    }
	    else if (p == q)
	        // We have fallen off list.  If tail is unchanged, it
	        // will also be off-list, in which case we need to
	        // jump to head, from which all live nodes are always
	        // reachable.  Else the new tail is a better bet.
	        p = (t != (t = tail)) ? t : head;
	    else
	        // Check for tail updates after two hops.
	        p = (p != t && t != (t = tail)) ? t : q;
	}
}
```

从整体的代码结构上看，在检查完空判断之后，可以看到它整个是一个大的 for 循环，而且是一个非常明显的死循环。在这个循环中有一个非常亮眼的 p.casNext 方法，这个方法正是利用了 CAS 来操作的，而且这个死循环去配合 CAS 也就是典型的乐观锁的思想。

---

### casNext()

```java
boolean casNext(Node<E> cmp, Node<E> val) {
    return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
}
```

这里运用了 UNSAFE.compareAndSwapObject 方法来完成 CAS 操作，而 compareAndSwapObject 是一个 native 方法，最终会利用 CPU 的 CAS 指令保证其不可中断。