# 一 volatile用来解决什么问题

声明为volatile的变量在多线程共享的情况下，java内存模型保证了所有线程看到的变量一致的。（[Java Language Specification volatile Fields](https://docs.oracle.com/javase/specs/jls/se8/html/jls-8.html#jls-8.3.1.4)）

# 二 问题产生的原因

## 1. java多线程内存共享

## 2. CPU缓存

正确理解CPU缓存的知识，推荐参考官方文档[英特尔® 64 位和 IA-32 架构开发人员手册：卷 3A CHAPTER 11](http://www.intel.cn/content/www/cn/zh/architecture-and-technology/64-ia-32-architectures-software-developer-vol-3a-part-1-manual.html?wapkw=ia-32+%E6%9E%B6%E6%9E%84%E5%BC%80%E5%8F%91%E4%BA%BA%E5%91%98%E6%89%8B%E5%86%8C)。



# 三 volatile是如何解决问题的

# 四 volatile的使用



`参考`
* [Java内存访问重排序的研究](https://tech.meituan.com/java-memory-reordering.html)
* [英特尔® 64 位和 IA-32 架构开发人员手册](http://www.intel.cn/content/www/cn/zh/search.html?toplevelcategory=none&query=%20IA-32%20%E6%9E%B6%E6%9E%84%E5%BC%80%E5%8F%91%E4%BA%BA%E5%91%98%E6%89%8B%E5%86%8C&keyword=%20IA-32%20%E6%9E%B6%E6%9E%84%E5%BC%80%E5%8F%91%E4%BA%BA%E5%91%98%E6%89%8B%E5%86%8C&:cq_csrf_token=undefined)
* [Java Language and Virtual Machine Specifications](https://docs.oracle.com/javase/specs/)
* [思路](https://m.baidu.com/from=1012852y/bd_page_type=1/ssid=0/uid=0/pu=usm%401%2Csz%40224_220%2Cta%40iphone___3_537/baiduid=4C4249DFF62D0E2CB400D6692713C2C0/w=0_10_/t=iphone/l=3/tc?ref=www_iphone&lid=14513022425855152384&order=1&fm=alop&tj=www_normal_1_0_10_title&vit=osres&m=8&srd=1&cltj=cloud_title&asres=1&nt=wnor&title=%E6%B7%B1%E5%85%A5%E7%90%86%E8%A7%A3volatile-%E4%BC%98%E9%9B%85de%E6%96%87-%E5%8D%9A%E5%AE%A2%E5%9B%AD&dict=30&w_qd=IlPT2AEptyoA_yijI5ugDyo9ucNVeJIp-jO&sec=22593&di=3846f6fd16cb6a81&bdenc=1&nsrc=IlPT2AEptyoA_yixCFOxXnANedT62v3IEQGG_ytK1DK6mlrte4viZQRAXj05R8qMXlvzsyPQpt5Ywk_h_GMj8hl0wvQkfjS&clk_info=%7B%22srcid%22%3A%221599%22%2C%22tplname%22%3A%22www_normal%22%2C%22t%22%3A1500457324558%2C%22xpath%22%3A%22div-a-h3%22%7D&sfOpen=1)
* [JSR 133 (Java Memory Model) FAQ](http://www.cs.umd.edu/~pugh/java/memoryModel/jsr-133-faq.html#volatile)
* [JVM内存模型、指令重排、内存屏障概念解析](http://www.cnblogs.com/chenyangyao/p/5269622.html)
* [Volatile and memory barriers](http://jpbempel.blogspot.co.uk/2013/05/volatile-and-memory-barriers.html)