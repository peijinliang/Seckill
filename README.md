# Seckill
Seckill  system based on SpringMvc 


##  java  秒杀系统
技术栈：Spring + SpringMvc + MyBatis + BootStrap + Redis

#### 秒杀本质是对库存进行处理
MySQL  事务


Start Transaction
Update 库存数量 
Insert 购买明细
Commit

ORM (Object  Relational  Mapping)
    
    数据库   映射  对象 
    myBatis   hibernate 

###myBatis

    xml 提供Sql
    注解提供Sql 

Restful 风格定义了Url书写的一种格式

URL 

    优雅的表达方式
    资源的状态和状态转移

技术实现细节：

1、秒杀项目整体架构分析
2、Maven搭建Spring项目、数据库业务建表
3、MyBatis整合Spring配置(xml)
4、整合Redis 提高整体并发能力

