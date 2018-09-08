> jvm setting的参数确实比较多（Oracle官网[Java HotSpot VM Options](https://www.oracle.com/technetwork/articles/java/vmoptions-jsp-140102.html)），但是作为一名java开发者，那几个最常用最基本的参数设置和意义一定要死记和理解。这里推荐一个网站[http://jvmmemory.com/](http://jvmmemory.com/)在上面你可以方便的设置jvm的参数（当然这个网站上也是有些错误的，最可靠的还是去查下Oracle的文档）。

`Notice`:这里我们默认以Linux JAVA8 hotspot环境为例，其Oracle官网[Java Platform, Standard Edition Tools Reference](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)（这里面有详细参数配置说明）。

下面我先罗列最常见的一些jvm内存设置相关参数，这些参数设置就是开发过程中最常用的，也是必要理解和记住的。

参数                        | 等同于
----                        | ---
-Xss1024k                   | -XX:ThreadStackSize=1024k
-Xms512m                    | 
-Xmx1024m                   | -XX:MaxHeapSize=1024m
-Xmn512m                    | 
-XX:NewSize=512m            | 
-XX:MaxNewSize=512m         |
-XX:NewRatio=8              |
-XX:SurvivorRatio=32        |
-XX:MinHeapFreeRatio=40     |
-XX:MaxHeapFreeRatio=70     |
-XX:MetaspaceSize=128m      |
-XX:MaxMetaspaceSize=256m   |



首先我们来看看jvm内存模型结构，这里不详细叙述，我用一张图来让你过目不忘（jvisualvm visualVM插件）。当然了，这张图死磕也要永久记住的，有了他我们记忆以上几个参数不再是难事，时间久了也不会感觉陌生或者忘记。

![](https://raw.githubusercontent.com/moxingwang/resource/master/image/jvm/java-8-hostspot-visualvm-0.png)

一个重要的概念就是我们常常所说的设置jvm的内存参数大多关注的是堆内存的大小，你可以简单理解成这样：`堆内存 = Old + Eden + S0 + S1` ；`年轻的 = Eden + S0 + S1 `。




# REFERENCE
* [Java HotSpot VM Options](https://www.oracle.com/technetwork/articles/java/vmoptions-jsp-140102.html)
* [Java Platform, Standard Edition Tools Reference](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html)
* [JVM -XX:NewRatio、-XX:SurvivorRatio参数含义](https://blog.csdn.net/mn960mn/article/details/46641111)
* [JVM实用参数（五）新生代垃圾回收](http://ifeve.com/useful-jvm-flags-part-5-young-generation-garbage-collection/)