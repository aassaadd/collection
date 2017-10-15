# 问题现象
1. 项目默认走logback，log4j不起作用。
2. 工程里面找不到logback的jar包。
![](../../resources/image/log4jlogback.png)
command+F未发现logback的jar包。
3. 测试环境打包后发现lib目录下面有logback的jar包。


# 解决思路
> jar相互依赖导致的。

1. 搜索logback
![](../../resources/image/logback_search.png)
发现