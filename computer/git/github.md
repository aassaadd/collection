# github慢解决办法
### 第一步
> 在https://www.ipaddress.com/ 使用 IP Lookup 工具获得下面这两个github域名的ip地址，该网站可能需要梯子，输入上述域名后，分别获得github.com和github.global.ssl.fastly.net对应的ip，比如192.30.xx.xx和151.101.xx.xx。准备工作做完之后，打开的hosts文件中添加如下格式，IP修改为自己查询到的IP：

```
192.30.xx.xx github.com
151.101.xx.xx github.global.ssl.fastly.net
```

### 第二步刷新DNS
* mac
```
sudo killall -HUP mDNSResponder
sudo killall mDNSResponderHelper
sudo dscacheutil -flushcache
```

* windows
```
ipconfig /flushdns
```

### reference
* [GitHub秘籍（中文版）](https://www.kancloud.cn/thinkphp/github-tips/37873)
* [解决GitHub下载速度太慢的问题](https://blog.csdn.net/qing666888/article/details/79123742)
* [Mac OS X 清除DNS缓存](https://www.cnblogs.com/qq952693358/p/9126860.html)
* [在Linux/Windows/Mac上刷新DNS缓存的方法](http://cnzhx.net/blog/how-to-flush-dns-cache-in-linux-windows-mac/)
