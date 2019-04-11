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
        * 相关文章
            * [zookeeper之数据模型](https://blog.csdn.net/usagoole/article/details/82944230)
            * [ZooKeeper session管理](https://blog.csdn.net/tomato__/article/details/78560727)
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
        * 客户端如何选择连接到server
            > ZooKeeper的每个客户端都维护一组服务端信息，在创建连接时由应用指定，客户端随机选择一个服务端进行连接，连接成功后，服务端为每个连接分配一个唯一标识。客户端在创建连接时可以指定溢出时间，客户端会周期性的向服务端发送PING请求来保持连接，当客户端检测到与服务端断开连接后，客户端将自动选择服务端列表中的另一个服务端进行重连。客户端允许应用修改服务端列表，但修改可能导致客户端与服务端的重连。
        * client和server建立session过程
            > 创建客户端session时，应用必须传入一组以逗号分隔的host:port列表，每个都对应一个ZooKeeper服务端，ZooKeeper客户端将选择任意一个服务端并尝试与其连接，如果连接失败，或者由于某些原因导致客户端与服务端连接断开，客户端将自动的选择列表中的另一个服务端进行连接，直到成功。当session创建成功后，ZooKeeper服务端为session分配一个唯一标识。

            > ZooKeeper使用session来表示客户端和服务端的连接。ZooKeeper的客户端管理一个可用的服务端列表，ZooKeeper客户端首先创建一个handle，handle建立后处于CONNECTING状态，然后客户端随机选择一个服务端进行连接，连接成功后，handle的状态更换到CONNECTED状态。如果出现无法恢复的错误，例如；会话终止或者认证失败，或者应用直接关闭handle，handle将转换到CLOSED状态。 

            ![](https://github.com/moxingwang/resource/blob/master/image/kafka/zookeeper%20session%20connect.png?raw=true)



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
* [ZooKeeper的Znode剖析](https://blog.csdn.net/lihao21/article/details/51810395)
* [ZooKeeper数据一致性](https://blog.csdn.net/tomato__/article/details/78673365)
* [一直对zookeeper的应用和原理比较迷糊，今天看一篇文章，讲得很通透](https://blog.csdn.net/gs80140/article/details/51496925)
* [Zookeeper - CLI](https://www.tutorialspoint.com/zookeeper/zookeeper_cli.htm)

