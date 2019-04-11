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




---PPT---
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
            * aversion The number of changes to the ACL of this znode.
            * ephemeralOwner The session id of the owner of this znode if the znode is an ephemeral node. If it is not an ephemeral node, it will be zero.
            * dataLength The length of the data field of this znode.
            * numChildren The number of children of this znode.


* zookeeper是如何工作的
    * leader选举
    * leader协调读写
    * 数据存储模式znode
    * 复制模式工作图
    * 数据一致性
    * 通知机制

* 应用举例

* api操作演示



---思考问题---
* 集群中clientPort不一致，可以等了解了读写机制理解