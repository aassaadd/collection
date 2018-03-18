ReentrantLock源码分析
=============

# 概述
 在JAVA中通常实现锁有两种方式，一种是synchronized关键字，另一种是Lock接口。synchronized是基于jvm层面实现的，Lock是基于jdk底层实现，接下来主要来分析ReentrantLock源码，理解ReentrantLock实现原理。 

 为了理解锁的重要性和存在的意义，首先应该在思考以下几个问题。 

 * 程序中为什么要使用锁？
 * 常见实现锁的方法，你知道的有哪些？
 * Lock与synchronized的区别在哪里？

# ReentrantLock源码
 ReentrantLock类在java.util.concurrent.locks包中，它的上一级的包java.util.concurrent主要是常用的并发控制类，它是基于AQS（AbstractQueuedSynchronizer）实现的，这里先不说那么多原理，先从AbstractQueuedSynchronizer的其中一个实现类ReentrantLock说起，先来看看他们直接的关系。
![](../resources/image/Sync结构.png)
![](../resources/image/log4jlogback.png)

## NonfairSync

#### 添加Node到队列尾部
````
private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }
````
````
private Node enq(final Node node) {
        //自旋位置
        for (;;) {
            Node t = tail;
            if (t == null) { // Must initialize
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }
````
    在多个线程进入的情况下，最终还是CAS保证了原子性。
#### acquireQueued
````
final boolean acquireQueued(final Node node, int arg) {
        boolean failed = true;
        try {
            boolean interrupted = false;
            for (;;) {
                final Node p = node.predecessor();
                if (p == head && tryAcquire(arg)) {
                    //保证了header永远是一个空的Node（thread）
                    setHead(node);
                    p.next = null; // help GC
                    failed = false;
                    return interrupted;
                }
                //parkAndCheckInterrupt方法阻塞线程
                if (shouldParkAfterFailedAcquire(p, node) &&
                    parkAndCheckInterrupt())
                    interrupted = true;
            }
        } finally {
            if (failed)
                cancelAcquire(node);
        }
    }
````

# 问题探讨

#### 程序中为什么要使用锁？
 我们写的程序部署在操作系统中，应用程序运行操作的都是资源，应用程序对资源有读写的权限，简单的说为了保证多个应用或者多个线程对同一个数据处理，保持数据的原子性使用的一种策略。

#### 常见实现锁的方法，你知道的有哪些？

#### Lock与synchronized的区别在哪里？

# 分析思路
* 按照多个线程走代码的方式去调试。

# 参考
* [ReentrantLock解析](http://blog.csdn.net/yanlinwang/article/details/40450769)
* [AbstractQueuedSynchronizer源码剖析（六）- 深刻解析与模拟线程竞争资源](http://blog.csdn.net/pfnie/article/details/53191892)
* ![ReentrantLock实现原理深入探究](http://www.cnblogs.com/xrq730/p/4979021.html)


