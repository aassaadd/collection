* 先从主从模式说起
    * 架构演讲
    * 主从常见问题

* Standalone模式演示开始，本地启动
    * 配置
    * 常用命令

* 复制模式配置演示
    * 配置说明

* 什么是zookeeper（整体认识）
    * 概念
    * 能干什么
    * 特点

* 核心概念
    * 数据模型znode
        * 存储空间
            > client and server会校验数据不能超过1M
        
        * 临时（Ephemeral）znode
            * as long as the session
            * 只能是在叶子节点上创建
        * 持久（PERSISTENT）znode
        * 顺序（SEQUENTIAL）znode
            * 在父节点下有序自增
            * int 
        * zxid
            * 有序
            * 全局唯一
        * zookeeper stat 结构
            * czxid Created ZXID表示该数据节点被创建时的事务ID
            * mzxid Modified ZXID 表示该节点最后一次被更新时的事务ID
            * pzxid 表示该节点的子节点列表最后一次被修改时的事务ID。只有子节点列表变更了才会变更pZxid,子节点内容变更不会影响pZxid
            * ctime Created Time表示节点被创建的时间
            * mtime Modified Time表示节点最后一次被更新的时间
            * dataVersion 数据节点版本号
            * cversion 子节点的版本号
            * aclVersion 节点的ACL版本号
            * ephemeralOwner 创建该临时节点的会话的SessionID。如果节点是持久节点，这个属性为0
            * dataLength 数据内容的长度
            * numChildren 当前节点的子节点个数

    * ZooKeeper Sessions
        * 创建会话
            > 创建客户端session时，应用必须传入一组以逗号分隔的host:port列表，每个都对应一个ZooKeeper服务端，ZooKeeper客户端将选择任意一个服务端并尝试与其连接，如果连接失败，或者由于某些原因导致客户端与服务端连接断开，客户端将自动的选择列表中的另一个服务端进行连接，直到成功。当session创建成功后，ZooKeeper服务端为session分配一个唯一标识。
        
        * 会话状态
            > Zookeeper会话在整个运行期间的生命周期中，会在不同的会话状态中之间进行切换，这些状态可以分为CONNECTING、CONNECTED、CLOSE。

            ![](https://github.com/moxingwang/resource/blob/master/image/kafka/zk-session-1.png?raw=true)

            > 一旦客户端开始创建Zookeeper对象，那么客户端状态就会变成CONNECTING状态，同时客户端开始尝试连接服务端，连接成功后，客户端状态变为CONNECTED，通常情况下，由于断网或其他原因，客户端与服务端之间会出现断开情况，一旦碰到这种情况，Zookeeper客户端会自动进行重连服务，同时客户端状态再次变成CONNCTING，直到重新连上服务端后，状态又变为CONNECTED，在通常情况下，客户端的状态总是介于CONNECTING和CONNECTED之间。但是，如果出现诸如会话超时、权限检查或是客户端主动退出程序等情况，客户端的状态就会直接变更为CLOSE状态。

        * session实体
            > Session是Zookeeper中的会话实体，代表了一个客户端会话，其包含了如下四个属性

        * 心跳检测
            >  客户端会在会话超时时间过期范围内向服务端发送PING请求来保持会话的有效性
         
        * 客户端连接指定根路径
            > 在ZooKeeper 3.2.0增加了可选的“chroot”后缀，可以改变当前客户端的根路径。例如，如果使用”127.0.0.1:4545/app/a”，客户端将使用”/app/a”作为其根路径，所有的路径都会相对于该路径。比如操作路径”/foo/bar”将真正对应到”/app/a/foo/bar”。这个特征在多租户环境下是非常有用的，可以简化客户端的应用逻辑（）。

        * session id和password
            > zookeeper的session id是每个会话的唯一标识，

        * session过期



* zookeeper是如何工作的
    * leader选举
    * leader协调读写
    * 数据存储模式znode
    * 复制模式工作图
    * 数据一致性
    * 通知机制

* 应用举例

* api操作演示



* 思考问题
    * 集群中clientPort不一致，可以等了解了读写机制理解




# 学习重点
* leader选举
* znode
* 通知机制
* 运行模式
* 集群数据一致性
* 读写机制


# 学习流程

## zk是什么
## zk能干什么
## 举例说明，先观察现象
## 主从模式系统
* 主节点崩溃
* 从节点崩溃
* 通行故障
### 主从架构模式带来的思考

# reference
* [ZooKeeper解惑](http://jm.taobao.org/2011/05/30/947/)
* [【分布式】Zookeeper会话](http://www.cnblogs.com/leesf456/p/6103870.html)
* [zookeeper curator处理会话过期session expired](https://www.cnblogs.com/kangoroo/p/7538314.html)
* [zookeeper之数据模型](https://blog.csdn.net/usagoole/article/details/82944230)
* [ZooKeeper session管理](https://blog.csdn.net/tomato__/article/details/78560727)
* [ZooKeeper的Znode剖析](https://blog.csdn.net/lihao21/article/details/51810395)
* [ZooKeeper数据一致性](https://blog.csdn.net/tomato__/article/details/78673365)
* [一直对zookeeper的应用和原理比较迷糊，今天看一篇文章，讲得很通透](https://blog.csdn.net/gs80140/article/details/51496925)
* [Zookeeper - CLI](https://www.tutorialspoint.com/zookeeper/zookeeper_cli.htm)

