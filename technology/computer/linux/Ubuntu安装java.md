## 参考
* [Ubuntu 安装 JDK 7 / JDK8 的两种方式](http://www.cnblogs.com/a2211009/p/4265225.html)
* [Ubuntu的add-apt-repository: command not found](http://blog.csdn.net/dogfish/article/details/67150703)

## 安装
````aidl

sudo apt-get install software-properties-common python-software-properties  
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
````