package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.List;
import static org.junit.Assert.*;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 **/

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSecillList() throws Exception {
        List<Seckill> list = seckillService.getSecillList();
        logger.info("list={}", list);
    }

    @Test
    public void getById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);

    }

    @Test
    public void exportSeckillUrl() throws Exception {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        logger.info("exposer={}", exposer);

        /**
         * exposer=Exposer{exposed=true, md5='6e1dbdfd3c1d662f85d005bb754d4344', seckillId=1000, now=0, start=0, end=0}
         */
    }

    @Test
    public void executeSeckill() throws Exception {
        long id = 1000;
        long userPhone = 18747584734l;
        String md5 = "6e1dbdfd3c1d662f85d005bb754d4344";
        SeckillExecution execution = seckillService.executeSeckill(id, userPhone, md5);
        logger.info("execution={}", execution);
    }


}