# Seckill
Seckill  system based on SpringMvc 

业务逻辑和实现过程：

1)MyBatis查询商品列表
2)点击商品进入商品详情，商品详情进行初始化。
3)判断当前用户是否登录，如果没有登录，弹框提醒输入手机号进行登录。
  如果已经登录，请求当前时间，进入倒计时判断。
4) 秒杀三种状态：
已经结束：提示
已经开始：允许用户执行秒杀操作
尚未开始：显示倒计时弹框
5)暴露秒杀地址
用户执行秒杀操作


系统可能用到哪些服务？

CDN 
WebServer
Redis
MySQL

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

### MyBatis

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

QPS:每秒查询率QPS是对一个特定的查询服务器在规定时间内所处理流量多少的衡量标准。






