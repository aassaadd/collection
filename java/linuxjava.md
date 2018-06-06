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
* [CentOS 安装 Erlang](https://blog.zfanw.com/install-erlang-on-centos/) 
* 下载rabbitMQ [Installing on RPM-based Linux (RHEL, CentOS, Fedora, openSUSE)](https://www.rabbitmq.com/install-rpm.html)
* 安装
````$xslt
yum -y install rabbitmq-server-3.6.6-1.el6.noarch.rpm
````
* 启动web管理界面
````$xslt
rabbitmq-plugins enable rabbitmq_management
````

* 增加用户设置角色
````$xslt
rabbitmqctl add_user dev dev_user
rabbitmqctl set_user_tags dev administrator
rabbitmqctl set_permissions -p "/" admin "." "." ".*"
````


* [centos 7 安装rabbitmq 3.6.12](https://blog.csdn.net/lsb2002/article/details/78128489)