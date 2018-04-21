
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