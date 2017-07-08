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

### jvm执行引擎

### jvm GC

### jvm对象生命周期

参考
[解读JVM对象生命周期](http://developer.51cto.com/art/201009/227897_all.htm)

# 参考资料
* [深入探讨 java.lang.ref 包](https://www.ibm.com/developerworks/cn/java/j-lo-langref/)
* [JVM源码分析之FinalReference完全解读](http://www.infoq.com/cn/articles/jvm-source-code-analysis-finalreference)
* [JVM结构、GC工作机制详解](http://www.jianshu.com/p/a94912709e29)
* [JAVA GC 原理详解](https://segmentfault.com/a/1190000008384410)
* [Java垃圾回收基础的系列文章](http://youli9056.github.io/blog/java-garbage-collection-introduction/)
