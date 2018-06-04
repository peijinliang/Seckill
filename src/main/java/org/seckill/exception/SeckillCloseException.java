package org.seckill.exception;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe 秒杀关闭异常
 **/

public class SeckillCloseException extends SeckillExecption {

    public SeckillCloseException(String mesasge) {
        super(mesasge);
    }

    public SeckillCloseException(String mesasge, Throwable cause) {
        super(mesasge);
    }

}
