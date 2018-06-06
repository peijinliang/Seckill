package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExecption;
import java.util.List;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 * 业务接口： 站在使用者的角度 去设计接口
 * 三个方面：
 * 方法定义的粒度
 * 参数越简练越好
 * 返回类型（return 友好 或者 异常和类型）
 **/
public interface SeckillService {

    /**
     * 查询秒杀记录
     *
     * @return
     */
    List<Seckill> getSecillList();


    /**
     * 查询单个秒杀记录
     *
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);


    /**
     * 秒杀开启：输出秒杀接口的地址
     * 否则输出系统时间和秒杀时间
     *
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillExecption;


}
