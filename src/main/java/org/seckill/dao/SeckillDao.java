package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Crete by Marlon
 * Create Date: 2018/6/1
 * Class Describe
 **/
public interface SeckillDao {

    /**
     * 减库存
     *
     * @param seckillId
     * @param killTime
     * @return 如果影响行数 > 1, 表示更新行数纪录
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据id查询秒杀对象
     *
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     *
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 适用存储过程来执行秒杀操作
     *
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);


}
