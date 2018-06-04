package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillExecption;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 **/

@Service
public class SeckillServerImpl implements SeckillService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    //注入Service注解
    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    private final String slat = "903lje90**98%%^DSFASFASFN";

    @Override
    public List<Seckill> getSecillList() {
        return seckillDao.queryAll(0, 4);
    }

    @Override
    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    @Override
    public Exposer exportSeckillUrl(long seckillId) {
        Seckill seckill = seckillDao.queryById(seckillId);
        if (seckill == null) {
            return new Exposer(false, seckillId);
        }
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        //系统当前时间
        Date nowTime = new Date();

        if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime());
        }

        //转化特定字符串的过程 不可逆
        String md5 = getMd5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMd5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解来控制事务方法的优点
     * 1、开发团队形成约定，明确标注事务方法的编程风格
     * 2、保证事务方法的执行事件足够的短，不要穿插其他的操作(RPC/HTTP请求 ，剥离到方法为)
     * 3、不是所有的方法都需要事务，如：只有一条修改操作，只读操作不需要事务控制
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws RepeatKillException
     * @throws SeckillCloseException
     * @throws SeckillExecption
     */
    @Override
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5) throws RepeatKillException, SeckillCloseException, SeckillExecption {

        if (md5 == null || !md5.equals(getMd5(seckillId))) {
            throw new SeckillExecption("seckill data rewrite");
        }

        //执行秒杀逻辑：减库存 +  记录购买行为
        Date nowTime = new Date();
        try {
            //减库存操作
            int udateCount = seckillDao.reduceNumber(seckillId, nowTime);

            if (udateCount <= 0) {
                //没有更新到记录，秒杀结束
                throw new SeckillCloseException("seckill is closed");
            } else {
                //记录购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);

                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeated");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStatEnum.SUCCESS, successKilled);
                }
            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            //所以编译器异常，转化为运行期异常
            throw new SeckillExecption("seckill inner error :" + e.getMessage());
        }
    }


}
