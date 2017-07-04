HashMap和HashTable都是一种以键值对存储数据的容器，An object that maps keys to values。

## 相同之处
1. 实现接口
> 都实现了Map<K,V>, Cloneable, Serializable。

2. 实现原理
> 数据结构哈希表是由数组+链表组成的。

## 不同之处
1. 出现版本

| HashTable | HashMap |
| :--- | :----: |
| JDK1.0 | JDK1.2 |

2. 作者不同

| HashTable | HashMap |
| :--- | :----: |
| Arthur van Hoff、Josh Bloch、Neal Gafter | Doug Lea、Arthur van Hoff、Josh Bloch、Neal Gafter |

3.继承类不同

| HashTable | HashMap |
| :--- | :----: |
| Dictionary<K,V> | AbstractMap<K,V> |

| HashTable | HashMap |
| :--- | :----: |
|  |  |

4. NULL

5. 锁

6. 支持的遍历种类不同

7. 通过Iterator迭代器遍历时，遍历的顺序不同

8. 容量的初始值

9. 扩容方式

10. hash值算

11. API

## 学习
* [Java 集合系列14之 Map总结(HashMap, Hashtable, TreeMap, WeakHashMap等使用场景)](http://wangkuiwu.github.io/2012/02/14/collection-14-mapsummary/#anchor2)