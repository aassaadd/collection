* 任务计算

    ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/workers.png?raw=true)

* 主从架构

    ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/worker-master-slae.png?raw=true)

    > 在分布式系统设计中一个得到广泛应用的架构：一个主-从（master-worker）架构,该系统中遵循这个架构的一个重要例子是HBase——一个Google的数据存储系统（BigTable）模型的实现，在最高层，主节点服务器（HMaster）负责跟踪区域服务器（HRegionServer）是否可用，并分派区域到服务器。

    * master-worker模式面临的问题
        * 主节点崩溃
            > 如果主节点发送错误并失效，系统将无法分配新的任务或重新分配已失败的任务。这就需要重选备份主节点接管主要主节点的角色，进行故障转移,数据恢复等等，更糟的是，如果一些从节点无法与主要主节点通信，如由于网络分区（network partition）错误导致，这些从节点可能会停止与主要主节点的通信，而与第二个主要主节点建立主-从关系。针对这个场景中导致的问题，我们一般称之为脑裂（split-brain）：系统中两个或者多个部分开始独立工作，导致整体行为不一致性。我们需要找出一种方法来处理主节点失效的情况，关键是我们需要避免发生脑裂的情况。
        * 从节点崩溃
            > 如果从节点崩溃，已分配的任务将无法完成。如果从节点崩溃了，所有已派发给这个从节点且尚未完成的任务需要重新派发。其中首要需求是让主节点具有检测从节点的崩溃的能力。主节点必须能够检测到从节点的崩溃，并确定哪些从节点是否有效以便派发崩溃节点的任务。一个从节点崩溃时，从节点也许执行了部分任务，也许全部执行完，但没有报告结果。如果整个运算过程产生了其他作用，我们还有必要执行某些恢复过程来清除之前的状态。
        * 通信故障
            > 如果主节点和从节点之间无法进行信息交换，从节点将无法得知新任务分配给它。如果一个从节点与主节点的网络连接断开，比如网络分区（network partition）导致，重新分配一个任务可能会导致两个从节点执行相同的任务。如果一个任务允许多次执行，我们在进行任务再分配时可以不用验证第一个从节点是否完成了该任务。如果一个任务不允许，那么我们的应用需要适应多个从节点执行相同任务的可能性。

    * 主从模式总结
        * 主节点选举
            > 这是关键的一步，使得主节点可以给从节点分配任务。
        * 崩溃检测
            > 主节点必须具有检测从节点崩溃或失去连接的能力。
        * 组成员关系管理
            > 主节点必须具有知道哪一个从节点可以执行任务的能力。
        * 元数据管理

            ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/coordinate-dream.png?raw=true)

            > 主节点和从节点必须具有通过某种可靠的方式来保存分配状态和执行状态的能力。

    * 理想
        > 理想的方式是，以上每一个任务都需要通过原语(内核或微核提供核外调用的过程或函数称为原语(primitive))的方式暴露给应用，对开发者完全隐藏实现细节。ZooKeeper提供了实现这些原语的关键机制，因此，开发者可以通过这些实现一个最适合他们需求、更加关注应用逻辑的分布式应用。

* 什么是zookeeper
    * 来源
        > Zookeeper 最早起源于雅虎研究院的一个研究小组。在当时，研究人员发现，在雅虎内部很多大型系统基本都需要依赖一个类似的系统来进行分布式协调，但是这些系统往往都存在分布式单点问题。所以，雅虎的开发人员就试图开发一个通用的无单点问题的分布式协调框架，以便让开发人员将精力集中在处理业务逻辑上。

    * zookeeper是什么
        > ZooKeeper是一种用于分布式应用程序的高性能协调服务.

        > ZooKeeper is a high-performance coordination service for distributed applications. It exposes common services - such as naming, configuration management, synchronization, and group services - in a simple interface so you don't have to write them from scratch. You can use it off-the-shelf to implement consensus, group management, leader election, and presence protocols. And you can build on it for your own, specific needs.
        
        > ZooKeeper是一个典型的分布式数据一致性解决方案,其设计目标是将那些复杂且容易出错的分布式一致性服务封装起来，构成一个高效可靠的原语集，并以一系列简单易用的接口提供给用户使用。分布式应用程序可以基于 ZooKeeper 实现诸如数据发布/订阅、负载均衡、命名服务、分布式协调/通知、集群管理、Master 选举、分布式锁和分布式队列等功能。

    * 一个最常见的使用场景(dubbo)

        ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/dubbo-architecture-future.jpg?raw=true)
        
    * 特点
        * 简单化：ZooKeeper允许各分布式进程通过一个共享的命名空间相互联系，该命名空间类似于一个标准的层次型的文件系统。
        * 顺序一致性：按照客户端发送请求的顺序更新数据。
        * 原子性：更新要么成功，要么失败，不会出现部分更新。
        * 单一性 ：无论客户端连接哪个 server，都会看到同一个视图。
        * 可靠性：一旦数据更新成功，将一直保持，直到新的更新。
        * 及时性：客户端会在一个确定的时间内得到最新的数据。
        * 速度优势：ZooKeeper特别适合于以读为主要负荷的场合。ZooKeeper可以运行在数千台机器上，如果大部分操作为读，例如读写比例为10:1，ZooKeeper的效率会很高。

    * 常见运用场景
        * 数据发布与订阅（配置中心）
        * 负载均衡
        * 命名服务(Naming Service)
        * 分布式通知/协调
        * 集群管理与Master选举
        * 分布式锁
        * 分布式队列

* zk架构
    * 角色
        ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/zookeeper-construct-1.png?raw=true)

        * Leader
            > Leader作为整个ZooKeeper集群的主节点，负责响应所有对ZooKeeper状态变更的请求。它会将每个状态更新请求进行排序和编号，以便保证整个集群内部消息处理的FIFO。

        * Follower
            > Follower主要是响应本服务器上的读请求外，另外follower还要处理leader的提议，并在leader提交该提议时在本地也进行提交。另外需要注意的是，leader和follower构成ZooKeeper集群的法定人数，也就是说，只有他们才参与新leader的选举、响应leader的提议。
        * Observe
            > 为客户端提供读服务器，如果是写服务则转发给Leader。不参与选举过程中的投票，也不参与“过半写成功”策略。在不影响写性能的情况下提升集群的读性能。
        * client
            > 连接zookeeper服务器的使用着，请求的发起者。独立于zookeeper服务器集群之外的角色。
    
    * 数据模型znode
        ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/zknamespace.jpg?raw=true)

    * client读写操作

        ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/zookeeper-construct-readandwrite.png?raw=true)
    
    * ZAB协议

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
    
    * /bin/命令
        * zkCleanup：清理Zookeeper历史数据，包括食物日志文件和快照数据文件
        * zkCli：Zookeeper的一个简易客户端
        * zkEnv：设置Zookeeper的环境变量
        * zkServer：Zookeeper服务器的启动、停止、和重启脚本

    * 监控命令
        > 在客户端可以通过 telnet 或 nc 向 ZooKeeper 提交相应的服务信息查询命令。使用方式`echo mntr | nc localhost 2181 `.
        * conf: 输出相关服务配置的详细信息。比如端口、zk数据及日志配置路径、最大连接数，session超时时间、serverId等
        * cons: 列出所有连接到这台服务器的客户端连接/会话的详细信息。包括“接受/发送”的包数量、session id 、操作延迟、最后的操作执行等信息.
        * stat: 输出服务器的详细信息：接收/发送包数量、连接数、模式（leader/follower）、节点总数、延迟。 所有客户端的列表。
        * envi: 输出关于服务器的环境详细信息（不同于conf命令），比如host.name、java.version、java.home、user.dir=/data/zookeeper-3.4.6/bin之类信息
        * ...


* 复制模式配置演示
    * 配置server id
        > zookeeper集群模式下还要配置一个myid文件,这个文件需要放在dataDir目录下,问价中写入一个id即可。
    * zoo.cfg配置集群server列表
        * 集群模式多了 server.id=host:port1:port2 的配置。
            ```
            server.1= 192.168.1.9:2888:3888
            server.2= 192.168.1.124:2888:3888
            server.3= 192.168.1.231:2888:3888
            ```
        > 其中，id 被称为 Server ID，用来标识该机器在集群中的机器序号（在每台机器的 dataDir 目录下创建 myid 文件，文件内容即为该机器对应的 Server ID 数字）。host 为机器 IP，port1 用于指定 Follower 服务器与 Leader 服务器进行通信和数据同步的端口，port2 用于进行 Leader 选举过程中的投票通信。
    

* 核心概念
    * 数据模型znode
        ![](https://github.com/moxingwang/resource/blob/master/image/zookeeper/zknamespace.jpg?raw=true)
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

* 回顾zookeeper架构

* 选举
    * 问题1: 为什么要选举leader
        > 我们在了解分布式选举算法之前，我们需要这样一种算法产生的背景。在一个分布式系统中，因为各种意外的因素，有的服务器可能会崩溃或变得不可靠，它就不能和其他服务器达成一致状态。因而这样就需要一种Consensus协议，来确保服务器的容错性，也就是说即使系统中有一两个服务器节点Crash，也不会影响其处理过程。为了让容错方式达成一致，我们不可能要求所有的服务器节点100%都达成Consensus状态，只要超过半数的大多数服务器节点Consensus即可，假设有N台服务器节点，(N/2)+1 就超过半数，即可代表大多数了。
    * 问题2: 如何选举leader
        > 某个服务可以配置为多个实例共同构成一个集群对外提供服务。其每一个实例本地都存有冗余数据，每一个实例都可以直接对外提供读写服务。在这个集群中为了保证数据的一致性，需要有一个Leader来协调一些事务。那么问题来了：如何确定哪一个实例是Leader呢？

        * 问题的难点
            1. 没有一个仲裁者来选定Leader
            2. 每一个实例本地可能已经存在数据，不确定哪个实例上的数据是最新的

    
    * 分布式选举算法
        * 拜占庭问题
        * Paxos
        * raft
        * ZooKeeper ZAB
            > Zookeeper的核心是原子广播，这个机制保证了各个Server之间的同步。实现这个机制的协议叫做Zab协议。Zab协议有两种模式，它们分别是恢复模式（选主）和广播模式（同步）。当服务启动或者在领导者崩溃后，Zab就进入了恢复模式，当领导者被选举出来，且大多数Server完成了和leader的状态同步以后，恢复模式就结束了。状态同步保证了leader和Server具有相同的系统状态。leader选举是保证分布式数据一致性的关键。

* zookeeper选举
    * 搞清楚几个问题
        * 一个Server是如何知道其它的Server？
            > 在ZooKeeper集群中，Server的信息都在zoo.conf配置文件中，根据配置文件的信息就可以知道其它Server的信息。
        * ZooKeeper服务器有哪几种状态？（选主相关的状态）
            * LOOKING：寻找leader状态
            * LEADING：领导状态（节点为leader）
            * FOLLOWING：跟随者状态
            * OBSERVING：观察者状态（此状态不参与选举）
        * 成为Leader的必要条件？
            > Leader要具有最高的zxid；集群中大多数的机器（至少n/2+1）得到响应并follow选出的Leader。
        * 如果所有zxid都相同(例如: 刚初始化时)，此时有可能不能形成n/2+1个Server，怎么办？
            > ZooKeeper中每一个Server都有一个ID，这个ID是不重复的，如果遇到这样的情况时，ZooKeeper就推荐ID最大的哪个Server作为Leader。
        * ZooKeeper中Leader怎么知道Fllower还存活，Fllower怎么知道Leader还存活？
            > Leader定时向Fllower发ping消息，Fllower定时向Leader发ping消息，当发现Leader无法ping通时，就改变自己的状态(LOOKING)，发起新的一轮选举。

    * leader选主时机
        1. Server初始化
        2. server运行期间无法和leader保持连接
    
    * 核心概念
        * myid
            > 每个Zookeeper服务器，都需要在数据文件夹下创建一个名为myid的文件，该文件包含整个Zookeeper集群唯一的ID（整数）。例如某Zookeeper集群包含三台服务器，hostname分别为zoo1、zoo2和zoo3，其myid分别为1、2和3，则在配置文件中其ID与hostname必须一一对应，如下所示。在该配置文件中，server.后面的数据即为myid.
            ```
            server.1=zoo1:2888:3888
            server.2=zoo2:2888:3888
            server.3=zoo3:2888:3888
            ```
        * zxid
            > 每次对Zookeeper的状态的改变都会产生一个zxid（ZooKeeper Transaction Id），zxid是全局有序的，如果zxid1小于zxid2，则zxid1在zxid2之前发生。为了保证顺序性，该zkid必须单调递增。因此Zookeeper使用一个64位的数来表示，高32位是Leader的epoch，从1开始，每次选出新的Leader，epoch加一。低32位为该epoch内的序号，每次epoch变化，都将低32位的序号重置。这样保证了zkid的全局递增性。

    * 选举步骤
        * 状态变更
            > 服务器启动的时候每个server的状态时Looking，如果是leader挂掉后进入选举，那么余下的非Observer的Server就会将自己的服务器状态变更为Looking，然后开始进入Leader的选举状态；
        * 发起投票
            > 每个server会产生一个（sid，zxid）的投票，系统初始化的时候zxid都是0，如果是运行期间，每个server的zxid可能都不同，这取决于最后一次更新的数据。将投票发送给集群中的所有机器；
        * 接收并检查投票
            > server收到投票后，会先检查是否是本轮投票，是否来自looking状态的server；
        * 处理投票
            > 对自己的投票和接收到的投票进行PK：
                1. 先检查zxid，较大的优先为leader；
                2. 如果zxid一样，sid较大的为leader；
                3. 根据PK结果更新自己的投票，在次发送自己的投票；
        * 统计投票
            > 每次投票后，服务器统计投票信息，如果有过半机器接收到相同的投票，那么leader产生，如果否，那么进行下一轮投票；
        * 改变server状态
            > 一旦确定了Leader，server会更新自己的状态为Following或者是Leading。选举结束。

    * 几种leader选举场景
        * 集群启动选举
        * Follower重启选举
        * Leader重启选举

* zookeeper 数据一致
    * 拜占庭问题

    * 分布式一致问题
        > 分布式中有这么一个疑难问题，客户端向一个分布式集群的服务端发出一系列更新数据的消息，由于分布式集群中的各个服务端节点是互为同步数据的，所以运行完客户端这系列消息指令后各服务端节点的数据应该是一致的，但由于网络或其他原因，各个服务端节点接收到消息的序列可能不一致，最后导致各节点的数据不一致。

    * 分布式一致性原理
        * CAP
            * Consistency (一致性)
            * Availability (可用性)
            * Partition tolerance (分区容错)

            > 这三个基本需求，最多只能同时满足其中的两项。
        * 2PC
        * 3PC
        * Paxos
        * ZAB
    
    * ZAB协议
        > ZAB 协议是为分布式协调服务ZooKeeper专门设计的一种支持崩溃恢复的一致性协议。基于该协议，ZooKeeper 实现了一种主从模式的系统架构来保持集群中各个副本之间的数据一致性。ZAB协议运行过程中，所有的客户端更新都发往Leader，Leader写入本地日志后再复制到所有的Follower节点。一旦Leader节点故障无法工作，ZAB协议能够自动从Follower节点中重新选择出一个合适的替代者，这个过程被称为选主.
        * 特点
            * 一致性保证
                * 可靠提交(Reliable delivery) -如果一个事务 A 被一个server提交(committed)了，那么它最终一定会被所有的server提交
                * 全局有序(Total order) - 假设有A、B两个事务，有一台server先执行A再执行B，那么可以保证所有server上A始终都被在B之前执行
                * 因果有序(Causal order) - 如果发送者在事务A提交之后再发送B,那么B必将在A之前执行
            * 只要大多数（法定数量）节点启动，系统就行正常运行
            * 当节点下线后重启，它必须保证能恢复到当前正在执行的事务
    
    * ZAB工作模式
        * 广播(broadcast)模式
            > Zab 协议中，所有的写请求都由 leader 来处理。正常工作状态下，leader 接收请求并通过广播协议来处理。

            * 工作步骤
                1. leader从客户端收到一个写请求
                2. leader生成一个新的事务并为这个事务生成一个唯一的ZXID，
                3. leader将这个事务发送给所有的follows节点
                4. follower节点将收到的事务请求加入到历史队列(history queue)中,并发送ack给ack给leader
                5. 当leader收到大多数follower（超过法定数量）的ack消息，leader会发送commit请求
                6. 当follower收到commit请求时，会判断该事务的ZXID是不是比历史队列中的任何事务的ZXID都小，如果是则提交，如果不是则等待比它更小的事务的commit
        * 恢复(recovery)模式
            > 当服务初次启动，或者 leader 节点挂了，系统就会进入恢复模式，直到选出了有合法数量 follower 的新 leader，然后新 leader 负责将整个系统同步到最新状态。

            * 恢复模式需要解决的两个重要问题
                * 已经被处理的消息不能丢
                * 被丢弃的消息不能再次出现

            * 工作步骤
                * 选举
                * 同步


* 扩展
    * java api操作演示

* 整体回顾


* 思考问题
    * 集群中clientPort不一致，可以等了解了读写机制理解
    * observer是怎么设置的
    * zxid溢出变成负数了怎么办
    * 水平扩容



# reference
* [ZooKeeper基本原理](https://www.cnblogs.com/luxiaoxun/p/4887452.html)
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
* [关于若干选举算法的解释与实现](http://blog.jobbole.com/104832/)
* [Zookeeper的FastLeaderElection算法分析](https://www.jianshu.com/p/ccaecde36dd3)
* [深入浅出Zookeeper（一） Zookeeper架构及FastLeaderElection机制](http://www.jasongj.com/zookeeper/fastleaderelection/)
* [聊聊zookeeper的ZAB算法](https://juejin.im/entry/5b84d589e51d453885032159)
* [ZAB协议的那些事？](https://juejin.im/post/5b0633f96fb9a07ab45903ed)
* [ZooKeeper典型应用场景一览](http://jm.taobao.org/2011/10/08/1232/)