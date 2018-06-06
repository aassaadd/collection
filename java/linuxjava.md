# 安装java
### 下载
[Java SE Development Kit 8 Downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

### 安装

1. 将文件.tar.gz移动到/usr/java
2. 解压：tar -zxvf 文件.tar.gz
3. 打开/etc/profile（vim /etc/profile）在最后面添加如下内容：
````apple js
    JAVA_HOME=/usr/java/jdk1.
    export JRE_HOME=$JAVA_HOME/jre 
    export CLASSPATH=$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH 
    export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
````
4. source /etc/profile