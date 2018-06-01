package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Crete by Marlon
 * Create Date: 2018/6/1
 * Class Describe
 **/

@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring 配置文件
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() throws Exception {
        long id = 1001l;
        long phone = 18374784732L;
        int updateCount = successKilledDao.insertSuccessKilled(id, phone);
        System.out.println("updateNum:" + updateCount);
    }

    @Test
    public void queryByIdWithSeckill() throws Exception {
        long id = 1000l;
        long phone = 18374784738l;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, phone);

        System.out.println("successKilled:" + successKilled.toString());
        System.out.println("Seckill:" + successKilled.getSeckill().toString());
    }

}