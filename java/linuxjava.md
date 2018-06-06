# 安装java
### 下载
[Java SE Development Kit 8 Downloads](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
### 安装
* 将文件.tar.gz移动到/usr/java
* 解压：tar -zxvf 文件.tar.gz
* 打开/etc/profile（vim /etc/profile）在最后面添加如下内容：
````apple js
JAVA_HOME=/usr/java/jdk1.
export JRE_HOME=$JAVA_HOME/jre 
export CLASSPATH=$JAVA_HOME/lib:$JRE_HOME/lib:$CLASSPATH 
export PATH=$JAVA_HOME/bin:$JRE_HOME/bin:$PATH
````
* source /etc/profile





# 安装maven
### 下载
[Downloading Apache Maven 3.5.3](http://maven.apache.org/download.cgi)
### 安装
* 将下载文件放到/usr/maven
* 解压：tar -zxvf 文件
* 配置环境在/etc/profile加入
````apple js
export MAVEN_HOME=/usr/maven/maven文件
export MAVEN_HOME
export PATH=$PATH:$MAVEN_HOME/bin
````
* source /etc/profile



# 安装rabbitMQ
### 下载
### 安装