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
jvm就是jvm规范的一个实例，可用使用多种语言实现jvm虚拟机。

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

* 方法区
> 所有的类级别数据将存储在这里，包括静态变量。每个JVM只有一个方法区，并且它是一个共享资源。
  
* 堆区域
> 所有对象及其对应的实例变量和数组将存储在这里。每个JVM也有一个堆区域。由于方法和堆区域共享多个线程的内存，因此所存储的数据非线程安全。
  
* java虚拟机栈
> 对于每个线程，将创建一个单独的运行时栈。对于每个方法调用，将在堆栈存储器中产生一个条目，称为堆栈帧。所有局部变量将在堆栈内存中创建。
堆栈区域是线程安全的，因为它不是共享资源。堆栈帧分为三个子元素：
> 1. 局部变量数组——与方法相关，涉及局部变量以及将在此存储的相应值的多少。
> 2. 操作数堆栈——如果需要执行任何中间操作，那么操作数堆栈将充当运行时工作空间来执行操作。
> 3. 帧数据——对应于方法的所有符号存储在此处。在任何异常的情况下，捕捉块信息将被保持在帧数据中。

* PC寄存器（程序计数器）
> 每个线程都有单独的PC寄存器，用于保存当前执行指令的地址，一旦指令执行，PC寄存器将更新到下一条指令。
  
* 本地方法堆栈
> 本地方法堆栈保存本地方法信息。对于每个线程，将创建一个单独的本地方法堆栈。

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