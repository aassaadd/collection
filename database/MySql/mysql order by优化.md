> version : 5.7

本节描述MySQL何时可以使用索引来满足ORDER BY子句，当不能使用索引时使用filesort，以及优化器中有关ORDER BY的执行计划信息。

一个order by语句对于有没有使用limit可能存在执行差异。详细内容查看[8.2.1.17 LIMIT Query Optimization](https://dev.mysql.com/doc/refman/5.7/en/limit-optimization.html)。


