 项目中E端有一个订单导出的功能能（导出销售订单或者销售退单，导出列颇多，且必须满足实时数据）。我们使用POI导出数据，并且后端加了熔断措施，导出限流，大促期间导出开关控制。相对来说有了这些机制线上应用不会因为导出操作流量过大内存爆掉，也保证了应用安全稳定的运行，但是最近监控发现导出操作性能急剧下降（数据量已经超过3百万），先看看监控。

![](https://github.com/moxingwang/collection/blob/master/resources/image/majorization/%E8%AE%A2%E5%8D%95%E5%AF%BC%E5%87%BA%E4%BC%98%E5%8C%96%E5%89%8D.png?raw=true)
![](https://github.com/moxingwang/collection/blob/master/resources/image/majorization/%E8%AE%A2%E5%8D%95%E5%AF%BC%E5%87%BA%E4%BC%98%E5%8C%96%E5%89%8Dheap.png?raw=true)

 再来看看使用POI导出本地jvisualvm的内存动态变化图。

![](https://github.com/moxingwang/collection/blob/master/resources/image/majorization/poiExportOne.gif?raw=true)

# 分析问题

1. 导出请求耗时，排查SQL语句以及数据量。
2. 应用内存居高不下（内存在新生代未回收掉进入了老年代），分析Thread Dump。