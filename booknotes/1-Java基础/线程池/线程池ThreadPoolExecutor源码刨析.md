## 1.JAVA构建线程的方式（常识）

- 继承Thread（实际上也继承了Runnable）
- 实现Runnable
- 实现Callable（有返回值，可以抛异常，只能是BIO）
- 线程池（Java提供了构建线程池的方式）
	- Java提供了Executors可以去创建（规范中不允许使用这种方式创建线程池，这种方式对线程的控制粒度比较低）
	- 推荐手动创建线程池

## 2.线程池的7个参数（常识）

```java
public ThreadPoolExecutor(int corePoolSize,  //核心线程数
                          int maximumPoolSize,  //最大线程数
                          long keepAliveTime,  //最大空闲时间
                          TimeUnit unit,  //时间单位
                          BlockingQueue<Runnable> workQueue,  //阻塞队列
                          ThreadFactory threadFactory,  //线程工厂（寻找线程名）
                          RejectedExecutionHandler handler) //拒绝策略
```

## 3.线程池的执行策略（常识）

![[Pasted image 20230216220138.png]]

### 为什么先阻塞再尝试创建非核心线程？

饭店（线程池）- 厨子（线程）-人多先排队（阻塞队列）-招厨子（创建最大线程数）-今日客满（拒绝）

## 4.线程池属性标识

### 4.1线程池核心属性

```java
//是一个原子类int类型的数值，表达了两个意思，1：声明当前线程池的状态，2：声明线程池中的线程数
//高3位是：线程池状态           低29位是线程池中线程个数
private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));  
//后29位，方便后面做位运算
private static final int COUNT_BITS = Integer.SIZE - 3;  
//通过位运算得出最大容量（这就是在装逼！！！）
private static final int CAPACITY   = (1 << COUNT_BITS) - 1;  
  
// 线程池状态            1110 0000 0000 0000 0000 0000 0000 0000 表示线程池状态
private static final int RUNNING    = -1 << COUNT_BITS;  
// 000...线程池位SHUTDOWN状态，不接受新任务，但是内部还会处理阻塞队列中的任务，正在进行的任务也正常处理
private static final int SHUTDOWN   =  0 << COUNT_BITS;  
//001 ...代表线程池位STOP状态，不接受新任务，也不去处理阻塞队列中的任务，同时会中断正在执行的任务
private static final int STOP       =  1 << COUNT_BITS;  
//010 ...代表线程池为TIDYING状态，过渡的状态，代表当前线程池将Game Over
private static final int TIDYING    =  2 << COUNT_BITS;  
//011 ... 代表线程池为Game Over 状态
private static final int TERMINATED =  3 << COUNT_BITS;

//得到线程池状态
private static int runStateOf(int c)     { return c & ~CAPACITY; }  
//得到当前线程池的线程数量
private static int workerCountOf(int c)  { return c & CAPACITY; }  
```

### 4.2线程池状态变化
![[Pasted image 20230216225558.png]]

## 5.线程池的execute方法执行

### 5.1 从execute方法开始
```java
public void execute(Runnable command) {
	//健壮性判断
    if (command == null)  
        throw new NullPointerException();
    //拿到32位的int
	int c = ctl.get(); 
	//现在工作线程的个数 < 核心线程数
    if (workerCountOf(c) < corePoolSize) {  
	    //可以创建核心线程
        if (addWorker(command, true))  
            return;
        //代表创建核心线程数失败，重新获取ctl
        c = ctl.get();  
    }  
    //判断线程池是不是running，将任务加到阻塞队列中
    if (isRunning(c) && workQueue.offer(command)) {  
	    //再次获取ctl
        int recheck = ctl.get();  
        //再次判断是否是running状态，如果不是running，移除任务，拒绝策略
        if (! isRunning(recheck) && remove(command))  
            reject(command);  
        //如果线程池处在running状态，but工作线程为0
        else if (workerCountOf(recheck) == 0) 
        //阻塞队列有任务，但是没有工作线程，添加一个任务为空的工作线程处理阻塞队列中的任务
            addWorker(null, false);  
    }  
    //创建非核心线程处理任务
    else if (!addWorker(command, false))  
	    //拒绝策略
        reject(command);  
}
```

通过上述源码，掌握了线程池的执行流程，再次查看addWorker方法内部做了什么处理

```java
private boolean addWorker(Runnable firstTask, boolean core) {
	//标记for循环
    retry:  
    //给工作线程数表示+1
    for (;;) {  
        int c = ctl.get();  
        //获取线程池状态
        int rs = runStateOf(c);  
  
        // 如果是STOP状态以上，都停止了不用再加工作线程
        if (rs >= SHUTDOWN &&  
        //如果是SHUTDOWN，并且队列不为空，同时任务也不为空就不要加工作线程了
        //如果队列为空，这里要在队列中加入一个空的工作线程
            ! (rs == SHUTDOWN &&  
               firstTask == null &&  
               ! workQueue.isEmpty()))  
            //构建工作线程失败
            return false;  
  
        for (;;) {  
	        //获取工作线程个数
            int wc = workerCountOf(c);  
            //如果当前工作线程已经大于线程池最大容量，不去创建
            if (wc >= CAPACITY ||  
            //判断wc是否超过核心线程或者最大线程
                wc >= (core ? corePoolSize : maximumPoolSize))  
                return false;  
            //将工作线程数+1 采用CAS的方式
            if (compareAndIncrementWorkerCount(c))  
            //成功就退出外侧for
                break retry;  
            //重新获取ct1
            c = ctl.get();  
            //重新判断线程池状态， 如果有变化
            if (runStateOf(c) != rs)  
            //结束这次外侧循环，
                continue retry;  
             
        }  
    }  
	//worker 开始 = false
    boolean workerStarted = false;  
    //worker 添加 = false
    boolean workerAdded = false;  
    //worker 就是工作线程
    Worker w = null;  
    try {  
	    //创建worker
        w = new Worker(firstTask);  
        //从worker中获取线程t
        final Thread t = w.thread;  
        if (t != null) {
	        //获取线程池的全局锁，避免我添加任务时，其他线程干掉线程池，干掉线程池需要先获取这个锁
            final ReentrantLock mainLock = this.mainLock;  
            mainLock.lock();  
            try {    
	            //获取线程池装填
				int rs = runStateOf(ctl.get());
				//是running状态
                if (rs < SHUTDOWN || 
	                //是SHUTDOWN状态，创建空任务工作线程，处理阻塞队列中的任务 
                    (rs == SHUTDOWN && firstTask == null)) {
                    //线程是否是运行状态
                    if (t.isAlive()) 
                        throw new IllegalThreadStateException();
                    //将工作线程添加到集合中  
                    workers.add(w); 
                    //获取工作线程个数 
                    int s = workers.size(); 
                    //如果工作线程数，大于之前记录的最大工作线程数，就替换一下 
                    if (s > largestPoolSize)  
                        largestPoolSize = s;
                    // workerAdded成功  
                    workerAdded = true;  
                }  
            } finally {  
                mainLock.unlock();  
            }  
            if (workerAdded) {
	            //启动工作线程  
                t.start();
                //启动工作线程成功  
                workerStarted = true;  
            }  
        }  
    } finally {
	    //启动工作线程失败，调用下面方法  
        if (! workerStarted)  
            addWorkerFailed(w);  
    }
    //返回共工作是否启动  
    return workerStarted;  
}
```

## 6.Worker的封装

```java
private final class Worker  
    extends AbstractQueuedSynchronizer  
    implements Runnable  
{  
    private static final long serialVersionUID = 6138294804551838833L;  
  
    final Thread thread;  

    Runnable firstTask;  

    volatile long completedTasks;  
  
    Worker(Runnable firstTask) {  
        //setState(-1);
        this.firstTask = firstTask;  
        this.thread = getThreadFactory().newThread(this);
        
    }  
  
    public void run() {  
        runWorker(this);  
    }
}
```

看runWorker方法

```java
final void runWorker(Worker w) {
	//获取当前线程
    Thread wt = Thread.currentThread(); 
    //拿到任务 
    Runnable task = w.firstTask;
    //先不关注  
    w.firstTask = null;  
    w.unlock();
    //标识为true 
    boolean completedAbruptly = true;  
    try {
    //如果任务不为null。如果任务为null，通过getTask（）从阻塞队列中获取任务  
        while (task != null || (task = getTask()) != null) {
        //避免你shutdown我任务也不会中断  
            w.lock();
            //获取当前状态，是否大于等于STOP，中断！
            if ((runStateAtLeast(ctl.get(), STOP) ||   
                 (Thread.interrupted() &&  
                  runStateAtLeast(ctl.get(), STOP))) &&  
                !wt.isInterrupted())  
                wt.interrupt();  
            try { 
	            // 执行任务前的操作，和AOP一样
                beforeExecute(wt, task);  
                Throwable thrown = null;  
                try {
	                //开始执行任务  
                    task.run();  
                } catch (RuntimeException x) {  
                    thrown = x; throw x;  
                } catch (Error x) {  
                    thrown = x; throw x;  
                } catch (Throwable x) {  
                    thrown = x; throw new Error(x);  
                } finally {
	                //执行任务后的操作  
                    afterExecute(task, thrown);  
                }  
            } finally {  
                task = null;  
                w.completedTasks++;  
                w.unlock();  
            }  
        }  
        completedAbruptly = false;  
    } finally {  
        processWorkerExit(w, completedAbruptly);  
    }  
}
```

有时间查看getTask方法。processWork