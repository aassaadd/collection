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

* 开机自动启动
````$xslt
systemctl enable rabbitmq-server
````

* 启动关闭
````$xslt
rabbitmq-server start
rabbitmq-server stop
````

* 增加用户设置角色
````$xslt
rabbitmqctl add_user dev dev_user
rabbitmqctl set_user_tags dev administrator
rabbitmqctl set_permissions -p "/" dev "." "." ".*"
````

* 附 [centos 7 安装rabbitmq 3.6.12](https://blog.csdn.net/lsb2002/article/details/78128489)



# 安装Mysql
* 查看Linux发行版本
````$xslt
cat /etc/redhat-release
````
* 下载MySQL官方的Yum Repository [Download MySQL Yum Repository](https://dev.mysql.com/downloads/repo/yum/)
* 安装MySQL的Yum Repository
````$xslt
yum -y install mysql57-community-release-el7-7.noarch.rpm
````
* 安装MySQL数据库的服务器版本
````$xslt
yum -y install mysql-community-server
````
* 启动数据库
````$xslt
systemctl start  mysqld.service

````
* 获取初始密码
````$xslt
grep "password" /var/log/mysqld.log
````
* 修改root用户密码
````$xslt
ALTER USER 'root'@'localhost' IDENTIFIED BY 'Password@123';
update user set host = '%'  where user ='root';
flush privileges;
````


* 附 [CentOS 7.2使用yum安装MYSQL 5.7.10](https://typecodes.com/linux/yuminstallmysql5710.html)