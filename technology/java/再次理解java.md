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
![](https://github.com/moxingwang/collection/blob/master/resources/image/java%E6%96%87%E4%BB%B6%E7%BC%96%E8%AF%91%E6%89%A7%E8%A1%8C%E6%B5%81%E7%A8%8B%E7%AE%80%E5%8D%95%E5%9B%BE.png?raw=true)

## java文件编译

## jvm启动过程概述

## class loader

## jvm架构

## jvm内存模型

## jvm GC

# 参考资料
* [深入探讨 java.lang.ref 包](https://www.ibm.com/developerworks/cn/java/j-lo-langref/)
* [JVM源码分析之FinalReference完全解读](http://www.infoq.com/cn/articles/jvm-source-code-analysis-finalreference)
* [JVM结构、GC工作机制详解](http://www.jianshu.com/p/a94912709e29)
* [JAVA GC 原理详解](https://segmentfault.com/a/1190000008384410)
* [Java垃圾回收基础的系列文章](http://youli9056.github.io/blog/java-garbage-collection-introduction/)
* [JVM 架构解读](http://www.codeceo.com/article/jvm-architecture-explained.html)
