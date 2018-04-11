# 现象
 最近项目中突然发现一次锁现象，订单多次付款，最后一次退款。退款完成后支付系统手动第三方回调，支付系统多次通知订单系统，订单系统在这个过程中发生死锁，下面给出订单系统表结构做模拟死锁。

* 数据库结构
````
create database test_deadlock default character set utf8 collate utf8_general_ci;

use test_deadlock;

create table db_order(
  id bigint(1) not null auto_increment comment '主键',
  order_no varchar(64) not null comment '订单号',
  order_status tinyint(4) not null default '1' comment '订单状态',
  create_date timestamp not null default current_timestamp comment '开单时间',
  primary key (id)
)engine=innodb default charset =utf8;

create table db_payment(
  id bigint not null auto_increment comment '主键',
  order_id bigint(1) not null comment '订单主表id',
  payment_amount decimal(19,2) not null default '0' comment '支付金额',
  primary key (id)
)engine=innodb default charset =utf8;;

alter table db_payment add constraint   fk_order_id foreign key(order_id) references db_order(id);
````

* 初始化数据
````
insert into db_order(order_no,order_status) values ('10001',5);
insert into db_payment(order_id, payment_amount) values (1,100);
````

* 第一个事务
````
start transaction;
  insert into db_payment(order_id, payment_amount) values (1,100);
  update db_order set order_status=6 where id=1;
commit ;
````

* 第二个事务
````
start transaction;
  insert into db_payment(order_id, payment_amount) values (1,100);
  update db_order set order_status=7 where id=1;
commit ;
````

