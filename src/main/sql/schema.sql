--  数据库初始化脚本

--  创建数据库
CREATE DATABASE seckill;
-- 使用数据库
use seckill;
-- 创建秒杀数据库表

CREATE  TABLE  seckill(
  seckill_id bigint not null AUTO_INCREMENT comment '商品库存id',
  name varchar(120) not null comment '商品名称',
  number int not null comment '库存数量',
  start_time timestamp  not null comment '秒杀开始时间',
  end_time timestamp  not null comment '秒杀结束时间',
  create_time timestamp  not null  DEFAULT  CURRENT_TIMESTAMP  comment '创建时间',
  PRIMARY  KEY (seckill_id),
  key idx_start_time(start_time),
  key idx_end_time(end_time),
  key idx_create_time(create_time)
)ENGINE = InnoDB  AUTO_INCREMENT =1000 DEFAULT  CHARSET=utf8 COMMENT='秒杀库存表';

-- 初始化数据
insert into seckill (name ,number ,start_time,end_time)
values
('1000元秒杀Iphone X',100,'2018-5-31 00:00:00','2018-6-1 00:00:00'),
('500元秒杀Ipad2',100,'2018-5-31 00:00:00','2018-6-1 00:00:00'),
('1000元秒杀小米4',100,'2018-5-31 00:00:00','2018-6-1 00:00:00'),
('200元秒杀红米Note2',100,'2018-5-31 00:00:00','2018-6-1 00:00:00');


-- 秒杀成功明细表
-- 用户登陆认证相关的信息
create table success_killed (
  seckill_id bigint Not Null  COMMENT '秒杀商品Id',
  user_phone bigint  not null comment '用户手机号',
  state tinyint not null DEFAULT  -1 COMMENT  '状态标识： -1：无效  0：成功   1：已付款  2：已发货',
  create_time TIMESTAMP not null comment '创建时间',
  PRIMARY KEY (seckill_id ,user_phone),    /*联合主键*/
  key idx_create_time(create_time )
)ENGINE = InnoDB   DEFAULT  CHARSET=utf COMMENT='秒杀成功明细表';

-- 连接数据库控制台
-- mysql -uroot -p