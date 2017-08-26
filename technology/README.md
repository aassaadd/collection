# 2017年08月26日15:40:11
* UTF8
* [UTF-8编码规则](http://blog.csdn.net/sandyen/article/details/1108168)
> UTF-8是一种变长字节编码方式。对于某一个字符的UTF-8编码，如果只有一个字节则其最高二进制位为0；如果是多字节，其第一个字节从最高位开始，连续的二进制位值为1的个数决定了其编码的位数，其余各字节均以10开头。UTF-8最多可用到6个字节。 
  如表： 
  1字节 0xxxxxxx 
  2字节 110xxxxx 10xxxxxx 
  3字节 1110xxxx 10xxxxxx 10xxxxxx 
  4字节 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx 
  5字节 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 
  6字节 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 
  因此UTF-8中可以用来表示字符编码的实际位数最多有31位，即上表中x所表示的位。除去那些控制位（每字节开头的10等），这些x表示的位与UNICODE编码是一一对应的，位高低顺序也相同。 
  实际将UNICODE转换为UTF-8编码时应先去除高位0，然后根据所剩编码的位数决定所需最小的UTF-8编码位数。 
  因此那些基本ASCII字符集中的字符（UNICODE兼容ASCII）只需要一个字节的UTF-8编码（7个二进制位）便可以表示。 

* Base64
* [Base64笔记](http://www.ruanyifeng.com/blog/2008/06/base64.html)

# 20170824
* 正则表达式
* Mybatis 返回hashmap 
* 仔细聆听他人说问题

# 20170823
* 压缩算法lz77 lz78
* 微服务Rpc restful 
* 分布式事务
* Apache License

# 20170822
* 思维方式：看代码思考为什么，对比自己的思想和作者的设计器想；不但要知道结果还要反推为什么。

# 20170821
* major.version
* maven classifier

# 2017年08月22日15:39:19之前
* 日志查看技巧文章
* Jsp jms spring原理
* 通信原理
* 几大通信协议
* 文章:各种知识书写
* Log4j
* Spring Session策略
* rabbitMQ 数据延迟问题；
* Threadlocal 线程池 线程死亡过程
* Exception
* java代理(动态代理),反射,AOP原理
* java字节码增强
* Docker 搭建
* redis搭建
* MQ搭建
* 写Sql多动脑子,思考优化
* Git 常见使用命令操作
* 为什么 useGeneratedKeys="true"keyProperty="id"
* dubbo原理
* ioc和aop
* Tomcat
* mina（可以忽略）
* spring原理
* 长连接
* Socket
* 阿里云计算课程  https://yq.aliyun.com/articles/62910
* Hadoop
* [JVM性能调优监控工具jps、jstack、jmap、jhat、jstat等使用详解](http://blog.csdn.net/tzs_1041218129/article/details/61630981)
* Https
* 设计模式
* 一致性哈希算法
* 多线程交替输出变量
* tcp/ip
* mq模式 direct headers topic match fanout
* nio
* mysql 其它几本好书
* 通讯原理
* 长连接
* 机器学习
* zookeeper，Hbase，HDFS，Lucene，Redis,Cassandra,MongoDB
* SOA和Web Services（REST, SOAP）
* Jenkins, Maven/Sbt/Npm/Ant, Git/SVN
* storm,spark stream
* ThreadPoolExecutor 多线程创建的理解
* hadoop hive 本地搭建安装
* RandomAccess实现原理
* GC策略好好学习  [垃圾收集](https://www.ibm.com/developerworks/cn/java/i-garbage2/index.html?ca=drs-)
* jvm理解 [深入理解Java内存模型（一）——基础](http://www.infoq.com/cn/articles/java-memory-model-1)
* 继承关系内存分布
* Https流程
* bigdecimal原理
* 子类继承夫类的变量在内存中是如何存放的，比如书同名变量？
* IO
* java对象创建过程
* [Java架构师必会的技能（你都会了吗？）](https://juejin.im/entry/5992b4a65188254891517793?utm_source=gold_browser_extension)
