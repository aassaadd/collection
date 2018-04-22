## 哪些内存区域要被回收
### 一 Java程序的编译和执行过程

![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/执行和编译过程1.png)

### 二 运行时数据区域
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

## 判断对象是否存活（垃圾的判断条件）
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

## 垃圾收集算法（整理回收垃圾）
 当判断了哪些对象是否存活，接下来就是要对已经死亡的对象做收集，最后才是垃圾回收。针对这些垃圾如何收集（清除哪些对象，移动哪些对象），jvm提供了多种算法和思想。

### 一 复制（Copying）算法

 为了解决标记-清除算法的效率问题，一种称为“复制”（Copying）的收集算法出现了，思想为：它将可用内存按容量分成大小相等的两块，每次只使用其中的一块。当这一块内存用完，就将还存活着的对象复制到另一块上面，然后再把已使用过的内存空间一次清理掉。

 这样做使得每次都是对整个半区进行内存回收，内存分配时也就不用考虑内存碎片等复杂情况，只要移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。只是这种算法的代价是将内存缩小为原来的一半，代价可能过高了。复制算法的执行过程如下图所示：

 ![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/复制算法原理图.png)

### 二 标记-清除（Mark-Sweep）算法

 标记－清除（Mark-Sweep）算法是最基础的垃圾收集算法，后续的收集算法都是基于它的思路并对其不足进行改进而得到的。顾名思义，算法分成“标记”、“清除”两个阶段：首先标记出所有需要回收的对象，在标记完成后统一回收所有被标记的对象，标记过程在前一节讲述对象标记判定时已经讲过了。

 标记－清除算法的不足主要有以下两点：

 1. 空间问题：标记清除之后会产生大量不连续的内存碎片，空间碎片太多可能会导致以后在程序运行过程中需要分配较大对象时，无法找到足够的连续内存而不得不触发另一次垃圾收集动作。
 2. 效率问题：因为内存碎片的存在，操作会变得更加费时，因为查找下一个可用空闲块已不再是一个简单操作。

 ![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/标记清除算法.png)

### 三 标记-整理（Mark-Compact）算法
 复制算法在对象存活率较高时要进行较多的复制操作，效率将会变低。更关键的是：如果不想浪费50%的空间，就需要有额外的空间进行分配担保，以应对被使用的内存中所有对象都100%存活的极端情况，所以在老年代一般不能直接选用复制算法。

 根据老年代的特点，标记－整理（Mark-Compact）算法被提出来，主要思想为：此算法的标记过程与标记－清除算法一样，但后续步骤不是直接对可回收对象进行清理，而是让所有存活的对象都向一端移动，然后直接清理掉边界以外的内存。

 ![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/标记整理算法.png)

### 四 分代收集（Generation Collection）算法

 当前商业虚拟机的垃圾收集都采用分代收集（Generational Collection）算法，此算法相较于前几种没有什么新的特征，主要思想为：根据对象存活周期的不同将内存划分为几块，一般是把Java堆分为新生代和老年代，这样就可以根据各个年代的特点采用最适合的收集算法，可见这些算法也是组合使用的一个过程：

1. 新生代：在新生代中，每次垃圾收集时都发现有大批对象死去，只有少量存活，那就选用复制算法，只需要付出少量存活对象的复制成本就可以完成收集。
2. 老年代：在老年代中，因为对象存活率高、没有额外空间对它进行分配担保，就必须使用“标记-清除”或“标记-整理”算法来进行回收。

 ![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/分代回收算法1.png)

## HotSpot的算法实现
 前面两大节主要从理论上介绍了对象存活判定算法和垃圾收集算法，而在HotSpot虚拟机上实现这些算法时，必须对算法的执行效率有严格的考量，才能保证虚拟机高效运行。

### 一 枚举根节点
 从可达性分析中从GC Roots节点找引用链这个操作为例，可作为GC Roots的节点主要在全局性的引用（例如常量或类静态属性）与执行上下文（例如栈帧中的局部变量表）中，现在很多应用仅仅方法区就有数百兆，如果要逐个检查这里面的引用，那么必然会消耗很多时间。

* GC停顿（”Stop The World”）

> 另外，可达性分析工作必须在一个能确保一致性的快照中进行——这里“一致性”的意思是指在整个分析期间整个执行系统看起来就像被冻结在某个时间点上，不可以出现分析过程中对象引用关系还在不断变化的情况，这是保证分析结果准确性的基础。这点是导致GC进行时必须停顿所有Java执行线程（Sun将这件事情称为“Stop The World”）的其中一个重要原因，即使是在号称（几乎）不会发生停顿的CMS收集器中，枚举根节点时也是必须要停顿的。

* 准确式GC与OopMap

> 由于目前的主流Java虚拟机使用的都是准确式GC（即使用准确式内存管理，虚拟机可用知道内存中某个位置的数据具体是什么类型），所以当执行系统停顿下来后，并不需要一个不漏地检查完所有执行上下文和全局的引用位置，虚拟机应当是有办法直接得知哪些地方存放着对象引用。在HotSpot的实现中，是使用一组称为OopMap的数据结构来达到这个目的的，在类加载完成的时候，HotSpot就把对象内什么偏移量上是什么类型的数据计算出来，在JIT编译过程中，也会在特定的位置记录下栈和寄存器中哪些位置是引用。这样，GC在扫描时就可以直接得知这些信息了。
> 
### 二 安全点（Safepoint）——进行GC时程序停顿的位置
 
 在OopMap的协助下，HotSpot可以快速且准确地完成GC Roots枚举，但一个很现实的问题随之而来：可能导致引用关系变化，或者说OopMap内容变化的指令非常多，如果为每一条指令都生成对应的OopMap，那将会需要大量的额外空间，这样GC的空间成本将会变得很高。

 为此，HotSpot选择不为每条指令都生成OopMap，而是只在“特定的位置”记录这些信息，这些位置便被称为安全点（Safepoint）。也就是说，程序执行时并非在所有地方都能停顿下来开始GC，只有在到达安全点时才能暂停。Safepoint的选定既不能太少以致于让GC等待时间太长，也不能过于频繁以致于过分增大运行时的负荷。所以，安全点的选定基本上是以程序“是否具有让程序长时间执行的特征”为标准进行选定的——因为每条指令执行的时间都非常短暂，程序不太可能因为指令流长度太长这个原因而过长时间运行，“长时间执行”的最明显特征就是指令序列复用，例如方法调用、循环跳转、异常跳转等，所以具有这些功能的指令才会产生Safepoint。

 对于Sefepoint，另一个需要考虑的问题是如何在GC发生时让所有线程（这里不包括执行JNI调用的线程）都“跑”到最近的安全点上再停顿下来。这里有两种方案可供选择：

* 抢先式中断（Preemptive Suspension） 

> 抢先式中断不需要线程的执行代码主动去配合，在GC发生时，首先把所有线程全部中断，如果发现有线程中断的地方不在安全点上，就恢复线程，让它“跑”到安全点上。现在几乎没有虚拟机实现采用抢先式中断来暂停线程从而响应GC事件。

* 主动式中断（Voluntary Suspension）

> 主动式中断的思想是当GC需要中断线程的时候，不直接对线程操作，仅仅简单地设置一个标志，各个线程执行时主动去轮询这个标志，发现中断标志为真时就自己中断挂起。轮询标志的地方和安全点是重合的，另外再加上创建对象需要分配内存的地方。

### 三 安全区域（Safe Region）
 Safepoint机制保证了程序执行时，在不太长的时间内就会遇到可进入GC的Safepoint。但是，程序“不执行”的时候（如线程处于Sleep状态或Blocked状态），这时线程无法响应JVM的中断请求，“走到”安全的地方去中断挂起，这时候就需要安全区域（Safe Region）来解决。

 安全区域是指在一段代码片段之中，引用关系不会发生变化。在这个区域中的任意地方开始GC都是安全的。我们也可以把Safe Region看做是被扩展了的Safepoint。

 在线程执行到Safe Region中的代码时，首先标识自己已经进入了Safe Region，那样，当在这段时间里JVM要发起GC时，就不用管标识自己为Safe Region状态的线程了。在线程要离开Safe Region时，它要检查系统是否已经完成了根节点枚举（或者是整个GC过程），如果完成了，那线程就继续执行，否则它就必须等待直到收到可以安全离开Safe Region的信号为止。

## 垃圾回收器
### 一 相关概念
##### 1. 并行和并发

* 并行（Parallel）：指多条垃圾收集线程并行工作，但此时用户线程仍然处于等待状态。
* 并发（Concurrent）：指用户线程与垃圾收集线程同时执行（但不一定是并行的，可能会交替执行），用户程序在继续运行。而垃圾收集程序运行在另一个CPU上。

##### 2. 吞吐量（Throughput）

 吞吐量就是CPU用于运行用户代码的时间与CPU总消耗时间的比值，即吞吐量 = 运行用户代码时间 /（运行用户代码时间 + 垃圾收集时间）。假设虚拟机总共运行了100分钟，其中垃圾收集花掉1分钟，那吞吐量就是99%。

##### 3. Minor GC 和 Full GC

* 新生代GC（Minor GC）：指发生在新生代的垃圾收集动作，因为Java对象大多都具备朝生夕灭的特性，所以Minor GC非常频繁，一般回收速度也比较快。具体原理见上一篇文章。
* 老年代GC（Major GC / Full GC）：指发生在老年代的GC，出现了Major GC，经常会伴随至少一次的Minor GC（但非绝对的，在Parallel Scavenge收集器的收集策略里就有直接进行Major GC的策略选择过程）。Major GC的速度一般会比Minor GC慢10倍以上。

### 二 新生代收集器
#### Serial收集器
 Serial（串行）收集器是最基本、发展历史最悠久的收集器，它是采用复制算法的新生代收集器，曾经（JDK 1.3.1之前）是虚拟机新生代收集的唯一选择。它是一个单线程收集器，只会使用一个CPU或一条收集线程去完成垃圾收集工作，更重要的是它在进行垃圾收集时，必须暂停其他所有的工作线程，直至Serial收集器收集结束为止（“Stop The World”）。这项工作是由虚拟机在后台自动发起和自动完成的，在用户不可见的情况下把用户正常工作的线程全部停掉，这对很多应用来说是难以接收的。

  ![](https://raw.githubusercontent.com/moxingwang/collection/master/resources/image/gc/serial收集器.png)

#### ParNew收集器

#### Parallel Scavenge收集器

### 三 老年代收集器
### 四 G1收集器
### 五 收集器特点总结






## 动手透视GC知识点    




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