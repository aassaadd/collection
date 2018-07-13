> version : 5.7

本节描述MySQL何时可以使用索引来满足ORDER BY子句，当不能使用索引时使用filesort，以及优化器中有关ORDER BY的执行计划信息。

一个order by语句对于有没有使用limit可能存在执行差异。详细内容查看[8.2.1.17 LIMIT Query Optimization](https://dev.mysql.com/doc/refman/5.7/en/limit-optimization.html)。


# 使用索引满足order by

在某些情况下，MySQL可能会使用索引来满足一个ORDER BY子句，并避免执行filesort 操作时涉及的额外排序。

虽然ORDER BY并不完全精确地匹配索引，但是索引还是会被使用，只要在WHERE子句中，所有未被使用的那部分索引（一个索引多个字段-联合索引的情况）以及所有ORDER BY字段都是一个常量就没问题，都会走到索引而不是filesort。



# 便于理解
* [How MySQL executes ORDER BY](http://s.petrunia.net/blog/?p=24)
* []()