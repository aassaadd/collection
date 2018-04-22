## 哪些内存区域要被回收
### Java程序的编译和执行过程

![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/执行和编译过程1.png)

### 运行时数据区域
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

 JVM 中，程序计数器、虚拟机栈、本地方法栈都是随线程而生随线程而灭，栈帧随着方法的进入和退出做入栈和出栈操作，实现了自动的内存清理，因此，我们的内存垃圾回收主要集中于 Java堆和方法区中，在程序运行期间，这部分内存的分配和使用都是动态的，所以可以看出jvm gc主要是针对堆和方法区这块的内存做回收。

## 内存垃圾
* 什么是内存垃圾

 简单的说就是那些不再被引用的对象或无用的信息对象。

* 垃圾回收的意义

 垃圾回收可以有效的防止内存泄露，有效的使用空闲的内存。

## 判断对象是否存活
 首先，内存中的垃圾要被回收要解决的第一个问题就是如何判断这些对象是否还存活是否还有用，jvm垃圾回收器通常有两种算法来决定对象是否还存活需要回收。

* 引用计数

 每个对象有一个引用计数属性，新增一个引用时计数加1，引用释放时计数减1，计数为0时可以回收。此方法简单，无法解决对象相互循环引用的问题。

* 可达分析

 从GC Roots开始向下搜索，搜索所走过的路径称为引用链。当一个对象到GC Roots没有任何引用链相连时，则证明此对象是不可用的。不可达对象。在Java语言中，GC Roots包括：虚拟机栈中引用的对象，方法区中类静态属性实体引用的对象，方法区中常量引用的对象，本地方法栈中JNI引用的对象。

![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/gcroots可达.png)

## Garbage Collection
  Java垃圾回收是一个自动运行的管理程序运行时使用的内存的过程。通过GC的自动执行JVM将程序员从申请和释放内存的繁重操作中解放出来。那么Java的自动内存管理最终可以归结为自动化地解决了两个问题：

1. 给对象分配内存
2. 回收分配给对象的内存

## 分代回收

## finalize()方法

## GC 步骤
https://docs.oracle.com/javase/8/docs/technotes/guides/vm/gctuning/cms.html


## GC算法
 GC算法用来标记或者筛选哪些对象需要回收或标记或移动。

* 年轻代复制算法(gc的时候回收掉可以回收的，剩余的做移动操作)


## GC触发条件

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
* [jvm优化必知系列——监控工具](https://my.oschina.net/u/1859679/blog/1552290)
* [怎么在面试时回答Java垃圾回收机制（GC）相关问题？](https://www.zhihu.com/question/35164211)
* [深入理解JVM(2)——GC算法与内存分配策略](https://crowhawk.github.io/2017/08/10/jvm_2/)