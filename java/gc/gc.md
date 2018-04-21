 最近一直在继续理解jvm gc，为什么说是继续理解呢？记得上学的时候就知道有gc这一回事，工作到现在快四年了一直不断不断的coding业务代码，对java的研究也随着多少个夜班被遗忘在了备忘录里，想必也有不少朋友有同样的感受。接下来总结我对gc相关的理解，一起探索吧。

## Java runtime data areas
 Java虚拟机规范定义了Java虚拟机在执行Java程序过程中会把内存区域划分为若干个不同的数据区域，这些区域各有各自的用途、创建和销毁时间。

![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/JVM-Runtime-data-area.jpg)

[附：JAVA虚拟机规范-运行时数据区域](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.5)

* PC Register

 程序计数器（Program Counter Register）是一块较小的内存空间，它的作用可以看做是当前线程所执行的字节码行号指示器。  

* JVM Stack

 虚拟机栈描述的是Java方法执行的内存模型：每个方法被执行的时候都会同时创建一个栈帧 （Stack Frame）用于存储局部变量表、操作栈、动态链接、方法出口等信息。每一个方法被调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。  

* Native Method Stack

 本地方法栈（Native Method Stacks）与虚拟机栈所发挥的作用非常类似，区别在于虚拟机栈为虚拟机执行Java方法服务，而本地方法栈则是为虚拟机使用到的Native方法服务。

* Heap

 方法区是存放类型数据的， 而堆则是存放运行时产生的对象的。 和C++不同的是， Java只能在堆中存放对象， 而不能在栈上分配对象， 所有运行时产生的对象全部都存放于堆中， 包括数组。 我们知道， 在Java中， 数组也是对象。一个JVM实例中只有一个堆， 所有线程共享堆中的数据（对象） 。 

* Method Area

 方法区（Method Area）与Java堆一样，是各个线程共享的内存区域，它用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。 




## 什么是垃圾

## 分代回收

## GC 步骤
https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/cms.html


## GC算法
 GC算法用来标记或者筛选哪些对象需要回收或标记或移动。

* 年轻代复制算法(gc的时候回收掉可以回收的，剩余的做移动操作)

## gc研究工具
* jps
* jmap
* jstat
* jvisualvm(Visual GC插件)

## Metaspace


## 问题
* gc触发条件？

## Hotspot JVM GC
#### Serial GC

#### Parallel GC
#### CMS GC
#### G1 GC


* [javase-books](https://docs.oracle.com/javase/8/javase-books.htm)
* [Java Platform, Standard Edition HotSpot Virtual Machine Garbage Collection Tuning Guide](https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/toc.html)
* [Java Hotspot G1 GC的一些关键技术](https://tech.meituan.com/g1.html)
* [[JVM]Java内存区域与垃圾收集 - 思维导图](https://www.jianshu.com/p/088d71f20a47)
* [聊聊JVM的年轻代](http://ifeve.com/jvm-yong-generation/)
* [VisualVM安装插件报错](https://blog.csdn.net/xionglangs/article/details/77603343)
* [Java GC系列：Java垃圾回收详解](https://my.oschina.net/dyyweb/blog/398651)
* [java 8 JVM性能优化-年轻代的理解](http://itindex.net/detail/53159-java-jvm-%E6%80%A7%E8%83%BD%E4%BC%98%E5%8C%96)
* [JVM内存结构--新生代及新生代里的两个Survivor区(下一轮S0与S1交换角色，如此循环往复)、常见调优参数](https://blog.csdn.net/u012799221/article/details/73180509)
* [jvm:停止复制、标记清除、标记整理算法（垃圾回收）](https://blog.csdn.net/u010841296/article/details/50945390)
* [GC算法 垃圾收集器](https://www.cnblogs.com/ityouknow/p/5614961.html)
* [GC详解及Minor GC和Full GC触发条件总结](https://blog.csdn.net/yhyr_ycy/article/details/52566105)
* [Major GC和Full GC的区别是什么？触发条件呢？](https://www.zhihu.com/search?type=content&q=full%20gc)
* [Java PermGen 去哪里了?](http://ifeve.com/java-permgen-removed/)
* [JVM Run-Time Data Areas](https://www.programcreek.com/2013/04/jvm-run-time-data-areas/)