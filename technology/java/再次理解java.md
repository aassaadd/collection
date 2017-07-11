# 理解与学习的思路
> 不断学习和理解，反复提问为什么，仔细推敲和琢磨，多思考。

# 计算机

## 硬件

### CPU
### 内存
### 总线
### 存储
### 冯洛伊曼体系

## 软件

### 汇编
### c语言
### Java
### 面向过程编程
### 面向对象编程
### 函数式编程

# Java

## java基础

* Java发展历史
![image](https://github.com/moxingwang/collection/blob/master/resources/image/java_history.png)
* 特点
* jdk
* java Api
* jvm
* jre
* 工作流程原理简单介绍
> 我们写的java程序是遵循java语言规范的，是面向java开发者能够读的懂的语言。计算机不能够直接读懂我们写的java文件代码。
我们想要让cpu做事情，让它处理运算，而cup只能够读懂特殊的指令。我们写的java程序代码首先被编译成class文件，
能够让jvm读懂，再由ClassLoader把这些class文件加载到jvm运行时的数据区域，并最终由jvm翻译、调用c/c++执行。

![image](https://github.com/moxingwang/collection/blob/master/resources/image/java%E6%96%87%E4%BB%B6%E7%BC%96%E8%AF%91%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B%E7%AE%80%E5%8D%95%E5%9B%BE.png?raw=true)

参考
[Java详解之——Javac 编译原理](http://blog.csdn.net/qq756161569/article/details/50486946)

## java技术体系
![image](https://github.com/moxingwang/collection/blob/master/resources/image/java%E6%8A%80%E6%9C%AF%E4%BD%93%E7%B3%BB%E5%9B%BE.png)

## java文件编译

![image](http://images2015.cnblogs.com/blog/866881/201602/866881-20160216214708767-653617912.jpg)

参考
[Javac编译原理](http://www.cnblogs.com/java-zhao/p/5194064.html)

## jvm启动过程概述

![image](http://images0.cnblogs.com/blog/641601/201508/211701559568932.png)

参考
[Java虚拟机详解02----JVM内存结构](http://www.cnblogs.com/smyhvae/p/4748392.html)
[JVM启动过程——JVM之一](http://www.cnblogs.com/muffe/p/3540001.html)

## jvm架构
jvm就是jvm规范的一个实例，可用使用多种语言实现jvm虚拟机。hostspot 是stack-based architecture；

![image](http://static.codeceo.com/images/2016/10/JVM-Architecture.png)

参考
[JVM 架构解读](http://www.codeceo.com/article/jvm-architecture-explained.html)
[The JVM Architecture Explained](https://dzone.com/articles/jvm-architecture-explained)

JVM分为三个主要子系统,分别是
* 类加载子系统
* 运行时数据区
* 执行引擎

### 类加载子系统

Java的动态类加载功能由类加载器子系统处理。它在运行时首次引用类的时候加载、链接、并初始化类文件。

#### 加载

类将通过这些组件加载。Boot Strap Class Loader，Extension Class Loader和Application Class Loader是有助于实现的三个类加载器。
1. Boot Strap Class Loader——负责加载来自于Bootstrap类路径的类，就是rt.jar。此加载程序将给予最高优先级。
2. Extension Class Loader——负责加载在ext文件夹（jre \ lib）内的类。
3. Application Class Loader——负责加载应用程序级类路径，路径提到环境变量等

上面的类记载器在加载类文件时遵循Delegation Hierarchy 算法。

#### 链接
1. 验证——字节码验证器将验证生成的字节码是否正确，如果验证失败，我们将得到verification error。
2. 准备——对于所有的静态变量，内存将被分配和配置默认值。
3. 解决——所有的符号存储器引用都将替换为来自Method Area的原始引用。

#### 初始化

这是类加载的最后阶段，这里所有的静态变量都将被赋予原始值，并执行静态块。





### 运行时数据区（jvm内存模型）

运行时数据区分为5个主要组件。
![image](https://github.com/moxingwang/collection/blob/master/resources/image/jvm%20data%20areas%20structure.png)

#### the pc register
> 每个线程都有单独的PC寄存器，用于保存当前执行指令的地址，一旦指令执行，PC寄存器将更新到下一条指令。
程序计数器（Program Counter Register）是一块较小的内存空间，
它的作用可以看做是当前线程所执行的字节码的行号指示器。
在虚拟机的概念模型里（仅是概念模型，各种虚拟机可能会通过一些更高效的方式去实现），
字节码解释器工作时就是通过改变这个计数器的值来选取下一条需要执行的字节码指令，分支、循环、跳转、异常处理、线程恢复等基础功能都需要依赖这个计数器来完成。 

> 由于Java虚拟机的多线程是通过线程轮流切换并分配处理器执行时间的方式来实现的，在任何一个确定的时刻，一个处理器（对于多核处理器来说是一个内核）只会执行一条线程中的指令。
因此，为了线程切换后能恢复到正确的执行位置，每条线程都需要有一个独立的程序计数器，各条线程之间的计数器互不影响，独立存储，我们称这类内存区域为“线程私有”的内存。 
                                                      
> 如果线程正在执行的是一个Java方法，这个计数器记录的是正在执行的虚拟机字节码指令的地址；如果正在执行的是Natvie方法，这个计数器值则为空（Undefined）。
此内存区域是唯一一个在Java虚拟机规范中没有规定任何OutOfMemoryError情况的区域。

为什么要有pc register，并且是私有的？
> 当在执行多线程时候，CPU会不停的切换任务(对于多核来说是一个内核)，本质上在一个确定的时间点，只会执行某一个线程的指令。 
那么这时候为了能够准确的记录各个线程正在执行的当前字节码指令的地址，最后的办法就是为每一个线程一人分配一个计数器， 
这样一来各个线程独立计算互不干扰，虽然理论上浪费了些空间，但问题则变得简单多了。

* java virtual machine stacks
> 首先我要知道jvm虚拟机的实现大概有两种，一种是基于栈的架构（stack-based 不同），另一种是基于寄存器的架构（register-based）。
sun jvm是基于栈架构的实现。

> 在jvm的内存模型里面，栈、堆...各司其职,概括的来说栈内存用来存储局部变量和方法调用。栈中存放了多个栈帧。
与程序计数器一样，Java 虚拟机栈（Java Virtual Machine Stacks）也是线程私有的，它的生命周期与线程相同。
虚拟机栈描述的是Java方法执行的内存模型：每个方法被执行的时候都会同时创建一个栈帧（Stack Frame①）用于存储局部变量表、操作栈、动态链接、方法出口等信息。
每一个方法被调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。

##### 栈帧
> 栈帧(Stack Frame)是用于支持虚拟机进行方法调用和方法执行的数据结构，它是虚拟机运行时数据区的虚拟机栈(Virtual Machine Stack)的栈元素。栈帧存储了方法的局部变量表，操作数栈，动态连接和方法返回地址等信息。第一个方法从调用开始到执行完成，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。
   每一个栈帧都包括了局部变量表，操作数栈，动态连接，方法返回地址和一些额外的附加信息。在编译代码的时候，栈帧中需要多大的局部变量表，多深的操作数栈都已经完全确定了，并且写入到了方法表的Code属性中，因此一个栈帧需要分配多少内存，不会受到程序运行期变量数据的影响，而仅仅取决于具体虚拟机的实现。
   一个线程中的方法调用链可能会很长，很多方法都同时处理执行状态。对于执行引擎来讲，活动线程中，只有虚拟机栈顶的栈帧才是有效的，称为当前栈帧(Current Stack Frame)，这个栈帧所关联的方法称为当前方法(Current Method)。执行引用所运行的所有字节码指令都只针对当前栈帧进行操作。



参考
[深入理解Java虚拟机笔记---运行时栈帧结构](http://blog.csdn.net/xtayfjpk/article/details/41924283)

#### heap
> 。

* method area
> 所有的类级别数据将存储在这里，包括静态变量。每个JVM只有一个方法区，并且它是一个共享资源。
  
* run-time constant pool
> 所有对象及其对应的实例变量和数组将存储在这里。每个JVM也有一个堆区域。由于方法和堆区域共享多个线程的内存，因此所存储的数据非线程安全。

#### native method stacks
  





#### 对象的创建过程
![image](https://github.com/moxingwang/collection/blob/master/resources/image/%E5%AF%B9%E8%B1%A1%E5%88%9B%E5%BB%BA%E5%86%85%E5%AD%98%E5%88%86%E9%85%8D%E8%BF%87%E7%A8%8B.png)

#### 对象内存布局

#### 对象访问定位

### jvm执行引擎

参考
[深入JVM字节码执行引擎](http://blog.csdn.net/dd864140130/article/details/49515403)
[java虚拟机字节码执行引擎浅析](http://blog.csdn.net/chdjj/article/details/23468761)


### jvm GC

#### 哪些内存需要回收

#### 如何回收

#### 垃圾回收算法

#### 垃圾收集器

#### jvm虚拟机和OS CPU

#### 线程通信

#### jvm 锁机制

参考
[深入JVM锁机制1-synchronized](http://blog.csdn.net/chen77716/article/details/6618779)
[synchronized、锁、多线程同步的原理是咋样的](http://www.jianshu.com/p/5dbb07c8d5d5)
[CPU并发特性CAS、Volatile](http://blog.sina.com.cn/s/blog_ee34aa660102wsuv.html)
[缓存一致性（Cache Coherency）入门](http://www.infoq.com/cn/articles/cache-coherency-primer/)
[原子操作和竞争](http://www.infoq.com/cn/articles/atomic-operations-and-contention)
[单核,多核CPU的原子操作](https://my.oschina.net/jcseg/blog/316726)
[关于单CPU，多CPU上的原子操作](https://software.intel.com/zh-cn/blogs/2010/01/14/cpucpu)

### jvm对象生命周期

参考
[解读JVM对象生命周期](http://developer.51cto.com/art/201009/227897_all.htm)

# 参考资料
* [深入探讨 java.lang.ref 包](https://www.ibm.com/developerworks/cn/java/j-lo-langref/)
* [JVM源码分析之FinalReference完全解读](http://www.infoq.com/cn/articles/jvm-source-code-analysis-finalreference)
* [JVM结构、GC工作机制详解](http://www.jianshu.com/p/a94912709e29)
* [JAVA GC 原理详解](https://segmentfault.com/a/1190000008384410)
* [Java垃圾回收基础的系列文章](http://youli9056.github.io/blog/java-garbage-collection-introduction/)
* [【深入理解Java虚拟机-0】思维导图汇总](http://hippo-jessy.com/2017/02/03/%E3%80%90%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3Java%E8%99%9A%E6%8B%9F%E6%9C%BA-0%E3%80%91%E6%80%9D%E7%BB%B4%E5%AF%BC%E5%9B%BE%E6%B1%87%E6%80%BB/)
* [深入理解JVM读书笔记思维导图，深入理解jvm读书笔记](http://www.bkjia.com/Javabc/861553.html)
* [Java 代码编译和执行的整个过程](http://wiki.jikexueyuan.com/project/java-vm/java-debug.html)
* [Java 虚拟机规范](http://files.cnblogs.com/files/zhuYears/Java%E8%99%9A%E6%8B%9F%E6%9C%BA%E8%A7%84%E8%8C%83%EF%BC%88JavaSE7%EF%BC%89.pdf)
* [Java Memory Model](http://tutorials.jenkov.com/java-concurrency/java-memory-model.html)
* [Java Virtual Machine Specification 官方文档](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-2.html)
* [Is stack in CPU or RAM?](https://stackoverflow.com/questions/15433390/is-stack-in-cpu-or-ram)
* [JVM memory model](http://coding-geek.com/jvm-memory-model/)
* [《Java虚拟机原理图解》5. JVM类加载器机制与类加载过程](http://blog.csdn.net/luanlouis/article/details/50529868)
* [JVM体系结构与工作方式概览](https://segmentfault.com/a/1190000006914597?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io)
* [ JVM 指令集与 X86 等真实cpu指令集的异同](http://blog.csdn.net/zhaoyw2008/article/details/9321313)
* [ Java 虚拟机内存模型 与 cpu类比](http://blog.csdn.net/zhaoyw2008/article/details/9316189)
* [虚拟机随谈（一）：解释器，树遍历解释器，基于栈与基于寄存器，大杂烩（牛逼）](http://rednaxelafx.iteye.com/blog/492667)
* [Dalvik 虚拟机和 Sun JVM 在架构和执行方面有什么本质区别？](https://www.zhihu.com/question/20207106)