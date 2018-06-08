-- 秒杀执行的存储过程

DELIMITER $$  -- console  转化为

-- 定义存储过程
-- 参数： in   输入参数 ； out 输出参数
-- count_count() 返回上一条修改类型sql（delete ,insert ,update ）的影响的行数
-- insert_count : 0 : 未修改数据 >0 ： 修改的行数   <0 :sql错误/未执行修改sql

CREATE PROCEDURE seckill.execute_seckill (
  IN v_seckill_id BIGINT,
  IN v_phone BIGINT,
  IN v_kill_time TIMESTAMP,
  OUT r_result INT
)
  BEGIN

    DECLARE insert_count INT DEFAULT 0;

    START TRANSACTION;

    INSERT IGNORE INTO success_killed (
      seckill_id,
      user_phone,
      create_time
    )
    VALUES
      (
        v_seckill_id,
        v_phone,
        v_kill_time
      );

    SELECT
      row_count() INTO insert_count;


    IF (insert_count < 0) THEN
      ROLLBACK;


      SET r_result = - 1;


    ELSEIF (insert_count < 0) THEN
      ROLLBACK;


      SET r_result = - 2;


    ELSE
      UPDATE seckill
      SET number = number - 1
      where seckill_id = v_seckill_id
            and end_time > v_kill_time
            and start_time < v_kill_time
            and number > 0;

      SELECT row_count() into insert_count;

      IF (insert_count = 0) THEN
        ROLLBACK;
        SET r_result = 0;
      ELSEIF(insert_count < 0) THEN
        ROLLBACK;
        SET r_result = - 2;
      ELSE
        COMMIT;
        SET r_result = 1;

      END IF;

    END IF;

  END;

$$

DELIMITER  ;

set @r_result = -3;
-- 执行存储过程
call execute_seckill(1003,13761022837,now(),@r_result);


-- 获取结果
select @r_result;

-- 存储过程
-- 1: 存储过程优化是：事务锁行级持有的时间
-- 2：不要过度依赖存储过程
-- 3：简单的逻辑存储过程适用
-- 4：kps 一个商品,一个秒杀单 6000/qps

