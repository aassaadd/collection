* count(*) count(字段)的区别
>

* 指定索引字段的排序
> eg: alter table tx_order_item add index idx_product_creat_date(product_id(10) desc ,create_date desc ) using btree ;
> 这种方式MySQL8才有效
> https://coyee.com/article/11341-mysql-8-0-descending-indexes-can-speed-up-your-queries












* [MySQL Explain详解](http://www.cnblogs.com/xuanzhi201111/p/4175635.html)