> jvm setting的参数确实比较多，但是作为一名java开发者，那几个最常用最基本的参数设置和意义一定要死记和理解。这里推荐一个网站[http://jvmmemory.com/](http://jvmmemory.com/)在上面你可以方便的设置jvm的参数配置如图。

![](https://raw.githubusercontent.com/moxingwang/resource/master/image/jvm/jvm-setting-demo-01.png)

接下来我就说上图这几个参数，这几个参数就是我所说的最常用的，必须要死记住的，你都能够一口无误的说明它们是什么意思吗？

```
-Xms512m                    -Xmx1024m 
-XX:MetaspaceSize=128m      -XX:MaxMetaspaceSize=256m 
-XX:MinHeapFreeRatio=40     -XX:MaxHeapFreeRatio=70 
-XX:NewRatio=8              -XX:SurvivorRatio=32 
-XX:+UseG1GC
```