package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Crete by Marlon
 * Create Date: 2018/6/8
 * Class Describe
 * redis
 **/
public class RedisDao {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private JedisPool jedisPool;


    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);


    /**
     * Ip 地址和端口号
     *
     * @param ip
     * @param port
     */
    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }


    /**
     * get from cache
     * if null
     * get db
     * return
     * put cache
     * locgoin
     */
    public Seckill getSeckill(long seckillId) {
        //redis 操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "seckill:" + seckillId;
                //并没有实现内部序列化操作

                //get->byte[]  -> 反序列化 -> Object(Seckill)
                //采用自定义序列化

                byte[] bytes = jedis.get(key.getBytes());

                //缓存重新获取到
                if (bytes != null) {
                    Seckill seckill = schema.newMessage();
                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
                    //seckill 被反序列化

                    return seckill;
                }

            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }


    public String putSeckill(Seckill seckill) {
        //set  Object(Seckill)  -> byte[] -> 发送给redis

        //redis 操作逻辑
        try {
            Jedis jedis = jedisPool.getResource();

            try {
                String key = "seckill:" + seckill.getSeckillId();
                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));

                //超时缓存 一个小时
                int timeout = 60 * 60;
                String result = jedis.setex(key.getBytes(), timeout, bytes);
                return result;
            } finally {
                jedis.close();
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


}
