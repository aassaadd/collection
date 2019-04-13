* 先从主从模式说起
    * 架构演讲
    * 主从常见问题
    * 如何设计一个协调框架

* 什么是zookeeper（整体认识）
    * 概念
    * zk架构方式
        * 集群
        * cs访问模型
    * 能干什么
    * 特点

* Standalone模式演示开始，本地启动
    * 配置
        * tickTime
            > ZooKeeper 中使用的基本时间单元, 以毫秒为单位, 默认值是 2000。它用来调节心跳和超时。
        * initLimit
            > 默认值是 10, 即 tickTime 属性值的 10 倍。它用于配置允许 followers 连接并同步到 leader 的最大时间。如果 ZooKeeper 管理的数据量很大的话可以增加这个值。
        * syncLimit
            > 默认值是 5, 即 tickTime 属性值的 5 倍。它用于配置leader 和 followers 间进行心跳检测的最大延迟时间。如果在设置的时间内 followers 无法与 leader 进行通信, 那么 followers 将会被丢弃。
        * dataDir
            > ZooKeeper 用来存储内存数据库快照的目录, 并且除非指定其它目录, 否则数据库更新的事务日志也将会存储在该目录下。
        * clientPort
            > 服务器监听客户端连接的端口, 也即客户端尝试连接的端口, 默认值是 2181。

    * 常用命令

* 复制模式配置演示
    * 配置说明
    * 命令操作演示

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
            ```
            ZooKeeper zk = new ZooKeeper(serverList, sessionTimeout, watcher);
            zk.create("/test", new byte[0], Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            ```
            > 创建客户端session时，应用必须传入一组以逗号分隔的host:port列表，每个都对应一个ZooKeeper服务端，ZooKeeper客户端将选择任意一个服务端并尝试与其连接(这组serverlist会在初始化的时候打乱)，如果连接失败，或者由于某些原因导致客户端与服务端连接断开，客户端将自动的选择列表中的另一个服务端进行连接，直到成功。当session创建成功后，ZooKeeper服务端为session分配一个唯一标识。
        
            * 创建过程
                * client进行tcp建立连接
                * 当tcp连接成功之后，,client发送一个ConnectRequest包，将ZooKeeper构造函数传入的sessionTimeout数值发给Server。zookeeper server会验证客户端发来的sessionTimeout值;zookeeper server中有连个配置项.
                    
                    * minSessionTimeout 单位毫秒。默认2倍tickTime
                    * maxSessionTimeout 单位毫秒。默认20倍tickTime
                
                    （tickTime也是一个配置项。是Server内部控制时间逻辑的最小时间单位）

                    > 如果客户端发来的sessionTimeout超过min-max这个范围，server会自动截取为min或max.

                * server等表决通过后，会为这个session生成一个password，连同sessionId，sessionTimeOut一起返回给客户端（ConnectResponse）。客户端如果需要重连Server，可以新建一个ZooKeeper对象，将上一个成功连接的ZooKeeper 对象的sessionId和password传给Server
                    ZooKeeper zk = new ZooKeeper(serverList, sessionTimeout, watcher, sessionId,passwd);ZKServer会根据sessionId和password为同一个client恢复session，如果还没有过期的话。

        * 会话状态
            > Zookeeper会话在整个运行期间的生命周期中，会在不同的会话状态中之间进行切换，这些状态可以分为CONNECTING, ASSOCIATING, CONNECTED, CLOSED, AUTH_FAILED。

            ![](https://github.com/moxingwang/resource/blob/master/image/kafka/zk-session-1.png?raw=true)

            > 一旦客户端开始创建Zookeeper对象，那么客户端状态就会变成CONNECTING状态，同时客户端开始尝试连接服务端，连接成功后，客户端状态变为CONNECTED，通常情况下，由于断网或其他原因，客户端与服务端之间会出现断开情况，一旦碰到这种情况，Zookeeper客户端会自动进行重连服务，同时客户端状态再次变成CONNCTING，直到重新连上服务端后，状态又变为CONNECTED，在通常情况下，客户端的状态总是介于CONNECTING和CONNECTED之间。但是，如果出现诸如会话超时、权限检查或是客户端主动退出程序等情况，客户端的状态就会直接变更为CLOSE状态。


        * session激活
            > 客户端发送心跳检测，服务端就会进行一次会话激活，心跳检测由客户端主动发起，以PING请求形式向服务端发送，在Zookeeper的实际设计中，只要客户端有请求发送到服务端，那么就会触发一次会话激活，总结下来两种情况都会触发会话激活。

            * 客户端向服务端发送请求，包括读写请求，就会触发会话激活。
            * 客户端发现在sessionTimeout时间内尚未和服务端进行任何通信，那么就会主动发起PING请求，服务端收到该请求后，就会触发会话激活。

        
        * 会话清理
            > leader server的SessionTracker管理线程会管理者session,执行session的过期检查,如果会话过期就执行清理操作.

        * 会话重连


        * 客户端连接指定根路径
            > 在ZooKeeper 3.2.0增加了可选的“chroot”后缀，可以改变当前客户端的根路径。例如，如果使用”127.0.0.1:4545/app/a”，客户端将使用”/app/a”作为其根路径，所有的路径都会相对于该路径。比如操作路径”/foo/bar”将真正对应到”/app/a/foo/bar”。这个特征在多租户环境下是非常有用的，可以简化客户端的应用逻辑（）。

    * ZooKeeper Watches
        > 在ZooKeeper中，所有的读操作（getData，getChildren和exists）都可以设置监听,一个Watch事件是一个一次性的触发器，当被设置了Watch的数据发生了改变的时候，则服务器将这个改变发送给设置了Watch的客户端，以便通知它们。

        * zookeeper机制的特点
            * 一次性的触发器（one-time trigger）  
                > 当数据改变的时候，那么一个Watch事件会产生并且被发送到客户端中。但是客户端只会收到一次这样的通知，如果以后这个数据再次发生改变的时候，之前设置Watch的客户端将不会再次收到改变的通知，因为Watch机制规定了它是一个一次性的触发器。      
            * 发送到客户端（Sent to the client）    
                > 这个表明了Watch的通知事件是从服务器发送给客户端的，是异步的，这就表明不同的客户端收到的Watch的时间可能不同，但是ZooKeeper有保证：当一个客户端在看到Watch事件之前是不会看到结点数据的变化的。例如：A=3，此时在上面设置了一次Watch，如果A突然变成4了，那么客户端会先收到Watch事件的通知，然后才会看到A=4。
            * 监听方式（The data for which the watch was set）
                > znode 节点本身具有不同的改变方式,setData() 会触发设置在某一节点上所设置的数据监视(假定数据设置成功)，而一次成功的 create() 操作则会出发当前节点上所设置的数据监视以及父节点的子节点监视。一次成功的 delete() 操作将会触发当前节点的数据监视和子节点监视事件，同时也会触发该节点父节点的child watch。WatchEvent是最小的通信单元，结构上只包含通知状态、事件类型和节点路径。ZooKeeper服务端只会通知客户端发生了什么，并不会告诉具体内容。

        * 监听事件类型
            * Created event：调用exists方法设置监听；
            * Deleted event：调用exists、getData、getChildren设置监听；
            * Changed event：调用getData设置监听；
            * Child event：调用getChildren设置监听。
    
    * ACL 权限控制
        > zk做为分布式架构中的重要中间件，通常会在上面以节点的方式存储一些关键信息，默认情况下，所有应用都可以读写任何节点，在复杂的应用中，这不太安全，ZK通过ACL机制来解决访问权限问题.

* 常见用例
    + 分布式锁
    * 注册与发现
    * 

* 回顾zookeeper架构

* Leader选举
    * leader选举发生的场景
        1. Server初始化
        2. server运行期间无法和leader保持连接

* 分布式一致性原理
    * CAP
    * 2PC
    * 3PC
    * Paxos
    * ZAB

* zookeeper 一致性算法



* Leader、Follower、Observer之间的关系

* zookeeper是如何工作的
    * leader选举
    * leader协调读写
    * 数据存储模式znode
    * 复制模式工作图
    * 数据一致性
    * 通知机制

* 扩容

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
* [【Zookeeper源码五】Zookeeper 集群版建立连接过程](https://my.oschina.net/xianggao/blog/538839)
* [ZooKeeper解惑](http://jm.taobao.org/2011/05/30/947/)
* [【分布式】Zookeeper会话](http://www.cnblogs.com/leesf456/p/6103870.html)
* [zookeeper curator处理会话过期session expired](https://www.cnblogs.com/kangoroo/p/7538314.html)
* [zookeeper之数据模型](https://blog.csdn.net/usagoole/article/details/82944230)
* [ZooKeeper session管理](https://blog.csdn.net/tomato__/article/details/78560727)
* [ZooKeeper的Znode剖析](https://blog.csdn.net/lihao21/article/details/51810395)
* [ZooKeeper数据一致性](https://blog.csdn.net/tomato__/article/details/78673365)
* [一直对zookeeper的应用和原理比较迷糊，今天看一篇文章，讲得很通透](https://blog.csdn.net/gs80140/article/details/51496925)
* [Zookeeper - CLI](https://www.tutorialspoint.com/zookeeper/zookeeper_cli.htm)
* [分布式一致性原理、Paxos算法与Zookeeper的ZAB协议、Zookeeper使用场景与在电商系统中的应用](https://blog.csdn.net/zhengzhihust/article/details/53456371)
