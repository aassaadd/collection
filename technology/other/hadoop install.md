## 按照教程
````aidl
http://www.jianshu.com/p/1448d1550c8b
http://www.powerxing.com/install-hadoop/
http://blog.csdn.net/yhao2014/article/details/44938237
````

## 修改hostname
````aidl
hostnamectl set-hostname server1

添加host2

10.11.27.41 server1
10.11.27.43 server2
10.11.27.45 server3

/etc/sysconfig

NETWORKING=yes
HOSTNAME=server1
NTPSERVERARGS=iburst

````

## core-site.xml
````aidl
<configuration>  
    <!-- 指定hdfs的nameservice为ns1 -->  
    <property>  
        <name>fs.defaultFS</name>  
        <value>hdfs://mycluster</value>  
    </property>  
  
    <!-- 指定hadoop临时目录 -->  
    <property>  
        <name>hadoop.tmp.dir</name>  
        <value>/usr/local/hadoop/tmp</value>  
    </property>  
  
    <!-- 指定zookeeper地址 -->  
    <property>  
        <name>ha.zookeeper.quorum</name>  
        <value>server1:2181,h1s2:2181,server2:2181,server3:2181</value>  
    </property>  
</configuration>  
````

## hdfs-site.xml
````aidl
<configuration>  
    <!--指定hdfs的nameservice为mycluster，需要和core-site.xml中的保持一致 -->  
    <property>  
        <name>dfs.nameservices</name>  
        <value>mycluster</value>  
    </property>  
      
    <!-- mycluster下面有两个NameNode，分别是nn1，nn2 -->  
    <property>  
        <name>dfs.ha.namenodes.mycluster</name>  
        <value>nn1,nn2</value>  
    </property>  
      
    <!-- nn1的RPC通信地址 -->  
    <property>  
        <name>dfs.namenode.rpc-address.mycluster.nn1</name>  
        <value>h1m1:9000</value>  
    </property>  
      
    <!-- nn1的http通信地址 -->  
    <property>  
        <name>dfs.namenode.http-address.mycluster.nn1</name>  
        <value>h1m1:50070</value>  
    </property>  
      
    <!-- nn2的RPC通信地址 -->  
    <property>  
        <name>dfs.namenode.rpc-address.mycluster.nn2</name>  
        <value>h1m2:9000</value>  
    </property>  
      
    <!-- nn2的http通信地址 -->  
    <property>  
        <name>dfs.namenode.http-address.mycluster.nn2</name>  
        <value>h1m2:50070</value>  
    </property>  
      
    <!-- 指定NameNode的元数据在JournalNode上的存放位置 -->  
    <property>  
        <name>dfs.namenode.shared.edits.dir</name>  
        <value>qjournal://h1s1:8485;h1s2:8485;h1s3:8485;h1s4:8485;h1s5:8485/mycluster</value>  
    </property>  
      
    <!-- 指定JournalNode在本地磁盘存放数据的位置 -->  
    <property>  
        <name>dfs.journalnode.edits.dir</name>  
        <value>/usr/lib/hadoop/journal</value>  
    </property>  
      
    <!-- 开启NameNode失败自动切换 -->  
    <property>  
        <name>dfs.ha.automatic-failover.enabled</name>  
        <value>true</value>  
    </property>  
      
    <!-- 配置失败自动切换实现方式 -->  
    <property>  
        <name>dfs.client.failover.proxy.provider.mycluster</name>  
        <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>  
    </property>  
      
    <!-- 配置隔离机制方法，多个机制用换行分割，即每个机制暂用一行-->  
    <property>  
        <name>dfs.ha.fencing.methods</name>  
        <value>  
            sshfence  
            shell(/bin/true)  
        </value>  
    </property>  
      
    <!-- 使用sshfence隔离机制时需要ssh免登陆 -->  
    <property>  
        <name>dfs.ha.fencing.ssh.private-key-files</name>  
        <value>/home/hadoop/.ssh/id_rsa</value>  
    </property>  
      
    <!-- 配置sshfence隔离机制超时时间 -->  
    <property>  
        <name>dfs.ha.fencing.ssh.connect-timeout</name>  
        <value>30000</value>  
    </property>  
</configuration>  
````