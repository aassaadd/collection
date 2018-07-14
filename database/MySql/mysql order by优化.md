> version : 5.7, from [8.2.1.14 ORDER BY Optimization](https://dev.mysql.com/doc/refman/5.7/en/order-by-optimization.html)

本节描述MySQL何时可以使用索引来满足ORDER BY子句，当不能使用索引时使用filesort，以及优化器中有关ORDER BY的执行计划信息。

一个order by语句对于有没有使用limit可能存在执行差异。详细内容查看[8.2.1.17 LIMIT Query Optimization](https://dev.mysql.com/doc/refman/5.7/en/limit-optimization.html)。


# 使用索引满足order by

在某些情况下，MySQL可能会使用索引来满足一个ORDER BY子句，并避免执行filesort 操作时涉及的额外排序。

虽然ORDER BY并不完全精确地匹配索引，但是索引还是会被使用，只要在WHERE子句中，所有未被使用的那部分索引（一个索引多个字段-联合索引的情况）以及所有ORDER BY字段都是一个常量就没问题，都会走到索引而不是filesort。

这里我们有一张表tx_order,
````
CREATE TABLE `tx_order` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT ,
  `serial_number` varchar(255) NOT NULL ,
  `order_status` int unsigned DEFAULT 0 NOT NULL ,
  `market_id` varchar(10) DEFAULT NULL ,
  `market_name` varchar(255) DEFAULT NULL ,
  `shop_id` varchar(50) DEFAULT NULL ,
  `shop_name` varchar(100) DEFAULT NULL ,
  `mobile` varchar(64) DEFAULT NULL ,
  `create_date` datetime DEFAULT NULL ,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2333702 DEFAULT CHARSET=utf8;
````
并且添加索引
````
alter table tx_order add index idx_market_date(market_id,create_date);
````
在接下来的sql中分析order by对索引的使用情况。其中MySQL优化器实际执行sql是否使用索引还是表扫描取决于两者的效率。

* 下面这个sql中，优化器使用了idx_market_date索引避开了表扫描.
````
desc select market_id,create_date from tx_order.tx_order order by market_id,create_date;

1	SIMPLE	tx_order		index		idx_market_date	39		1671956	100	Using index

````

然而这句sql中的查询字段都在索引中，如果查询字段不被包含在索引中，如「select market_id,create_date,market_name」。这种情况下，扫描整个索引并且查找表行以查不在索引中的列，这样的操作的代价可能比表扫描更高，此时优化器可能不会使用索引。
````
desc select market_id,create_date,market_name from tx_order.tx_order order by market_id,create_date;

1	SIMPLE	tx_order		ALL					1671956	100	Using filesort

````

在InnoDB中，我们知道主键（聚集索引）本身是索引的一部分，下面这个查询中索引就会被使用。
````
desc select id,market_id,create_date from tx_order.tx_order order by market_id,create_date;

1	SIMPLE	tx_order		index		idx_market_date	39		1671956	100	Using index

````

* 下面这种情况，在where条件中索引中的一个字段是一个常量，并且where子语句产生的范围索引的性能比表扫描高的多，那么这样的查询会选择索引而不是表扫描。
````
desc select market_id,create_date from tx_order.tx_order where  market_id = '1009' order by create_date;

1	SIMPLE	tx_order		ref	idx_market_date	idx_market_date	33	const	170398	100	Using where; Using index

````

* 下面两条sql比较特殊，也可以对比前面几个order by ... asc的语句。看看下面的执行结果我们可以思考这是为什么。首先添加索引的时候暂时是无法指定字段排序的，alter table tx_order add index idx_market_date(market_id asc,create_date desc)，虽然这样的写法语法是支持的，但是当前版本的MySQL不做任逻辑何支持，都是统一安装默认升序排列。在一个联合索引中，查询按照索引中的字段排序，如果排序方式不一致，优化器还是会部分走表扫描的。
````
desc select market_id,create_date from tx_order.tx_order order by market_id desc ,create_date desc ;

1	SIMPLE	tx_order		index		idx_market_date	39		1671956	100	Using index

desc select market_id,create_date from tx_order.tx_order order by market_id asc ,create_date desc ;

1	SIMPLE	tx_order		index		idx_market_date	39		1671956	100	Using index; Using filesort

````

* 下面的查询where子语句中的范围索引优于表扫描，优化器会选择索引解析order by。
````
desc select market_id,create_date from tx_order.tx_order where market_id > '1009' order by market_id asc;

1	SIMPLE	tx_order		range	idx_market_date	idx_market_date	33		835978	100	Using where; Using index

desc select market_id,create_date from tx_order.tx_order where market_id < '1009' order by market_id desc;

1	SIMPLE	tx_order		range	idx_market_date	idx_market_date	33		230966	100	Using where; Using index
````

* 下面这个查询中，order by的不再是market_id，但是所有查询的行market_id都是一个常量，所以还是会走到索引的解析order by。
````
desc select market_id,create_date from tx_order.tx_order where market_id = '1009' and create_date>'2018-01-01' order by create_date desc;

1	SIMPLE	tx_order		range	idx_market_date	idx_market_date	39		94002	100	Using where; Using index
````

在一些情况下，虽然MySQL对where条件处理的时候用会用到索引，但是不能够用索引来解析order by, 看下面的例子。

* order by使用到的索引非连续，MySQL解析order by还是会扫描表，我这里有一个索引 idx_market_id(market_id,order_status,create_date)，看下面的sql执行结果。
````
desc select market_id,create_date from tx_order.tx_order where  market_id='1009' order by market_id ,create_date ;

1	SIMPLE	tx_order		ref	idx_market_id,idx_market_type_create_date	idx_market_id	33	const	138084	100	Using where; Using index; Using filesort
````

* 混合排序asc,desc
````
desc select market_id,create_date from tx_order.tx_order order by market_id asc ,create_date desc;

1	SIMPLE	tx_order		index		idx_market_date	39		1671956	100	Using index; Using filesort
````

* order by字段使用函数，优化器解析order by放弃索引
````
desc select mobile from tx_order.tx_order order by  abs(mobile);

1	SIMPLE	tx_order		index		idx_mobile	768		1671956	100	Using index; Using filesort

````

* 在多表关联查询中，并且ORDER BY中的列并不是全部来自第1个用于搜索行的非常量表。(这是EXPLAIN输出中的没有const联接类型的第1个表）。
````
desc select a.market_id from tx_order.tx_order a ,tx_order_item b where a.id = b.order_id and a.market_id = '1009'  order by a.market_id,b.sku;

1	SIMPLE	b		ALL	idx_order_create				1	100	Using filesort
1	SIMPLE	a		eq_ref	PRIMARY,idx_market_date	PRIMARY	8	tx_order.b.order_id	1	10.19	Using where

````

* 有不同的ORDER BY和GROUP BY表达式。
````
desc select market_id,create_date from tx_order.tx_order   group by market_id,create_date order by create_date;

1	SIMPLE	tx_order		index	idx_market_date	idx_market_date	39		1671956	100	Using index; Using temporary; Using filesort

````

* 对于指定了排序索引长度的索引。在这种情况下，索引不能完全解析排序顺序，需要使用filesort来排序。例如，建立索引alter table tx_order add index idx_mobile(mobile(5)); 然而mobile varchar(64).
````
desc select mobile from tx_order.tx_order order by mobile desc ;

1	SIMPLE	tx_order		ALL					1671956	100	Using filesort

````

# 便于理解
* [How MySQL executes ORDER BY](http://s.petrunia.net/blog/?p=24)
* [【mysql】order by 优化与索引的应用](http://www.itkeyword.com/doc/1647618271369999201/sql-order-mysql)
* 