## 参考

## install java8
* [Ubuntu 安装 JDK 7 / JDK8 的两种方式](http://www.cnblogs.com/a2211009/p/4265225.html)
* [Ubuntu的add-apt-repository: command not found](http://blog.csdn.net/dogfish/article/details/67150703)
````aidl
sudo apt-get install software-properties-common python-software-properties  
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | sudo /usr/bin/debconf-set-selections
sudo update-java-alternatives -s java-8-oraclesudo
vi ~/.bashrc 
export JAVA_HOME = /usr/lib/jvm/java-8-oracle
````
## install git
````aidl
sudo apt-get install git
````

## install maven
* [Ubuntu下Maven安装和使用](http://blog.csdn.net/ac_dao_di/article/details/54233520)
````aidl
wget http://apache.fayea.com/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz
tar zxvf apache-maven-3.3.9-bin.tar.gz

sudo vi /etc/profileexport

export M2_HOME=/home/apache-maven-3.3.9
PATH=$M2_HOME/bin:$PATH

````