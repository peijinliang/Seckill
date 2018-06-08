package org.seckill;

import redis.clients.jedis.Jedis;

/**
 * Crete by Marlon
 * Create Date: 2018/6/8
 * Class Describe
 **/
public class Test {

    public static void main(String[] rags) {
        Jedis jedis = new Jedis("localhost", 6379);
        System.out.println("连接成功");
        System.out.println("ping:" + jedis.ping());
    }

}
