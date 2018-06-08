package org.seckill.exception;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 **/

public class SeckillExecption extends RuntimeException {

    public SeckillExecption(String message) {
        super(message);
    }

    public SeckillExecption(String message, Throwable cause) {
        super(message, cause);
    }


}
