## 词典
* [苹果Mac自带词典完美扩充](https://www.jianshu.com/p/c57be986589b)
* [StarDict Dictionaries -- 星际译王词库 词典下载](http://download.huzheng.org/)

## Terminal
* 忽略大小写设置
````apple js
https://my.oschina.net/weichou/blog/369146
https://blog.csdn.net/f6619082/article/details/73473028
````
#### 计算机名称
[http://xiaosheng.me/2015/08/23/article5/](http://xiaosheng.me/2015/08/23/article5/)

## 性能优化
* [让变慢的Mac获得重生这15个Tips不得不学](https://bbs.feng.com/mobile-news-read-673744.html)

## Mac修改mac地址[acbook修改mac地址](http://geoffrey-qiao.iteye.com/blog/2053137)
```
sudo ifconfig en0 ether 20-7C-8F-73-B1-1E 
```

## Pulse Secure
* 关闭Pulse Secure 开机自动启动
```
sudo vim /Library/LaunchAgents/net.juniper.pulsetray.plist
```
修改KeepAlive值为false就可以了，这样，如果想再次改为开机启动，再把值改回true就好，同理可以推广到其他软件，找到相应的KeepAlive值就可以.

如果找不到这个文件，先进入这个目录设置。
