## 文件copy
````aidl
sudo scp m@10.11.28.65:/Users/M/Downloads/jdk-8u151-linux-x64.tar.gz /usr/local/java
````

## 机器
* 10.11.27.38
* 10.11.27.30
* 10.11.27.20


## jdk安装
````aidl
export JAVA_HOME=/usr/local/java/jdk1.8.0_151  
export JRE_HOME=${JAVA_HOME}/jre  
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib  
export PATH=${JAVA_HOME}/bin:$PATH
````

## vi
* 行首：0
* 行未：$
* 新增一行: o

