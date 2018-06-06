package org.seckill.exception;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 * 重复秒杀异常(运行起异常)
 **/

public class RepeatKillException extends SeckillExecption {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable casuse) {
        super(message, casuse);
    }

}
