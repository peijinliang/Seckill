package org.seckill.dto;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe
 * 封装json结果
 * 所有ajax请求返回类型，疯转json结果
 **/

public class SeckillResult<T> {

    private boolean success;
    private T data;
    private String error;

    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    @Override
    public String toString() {
        return "SeckillResult{" +
                       "success=" + success +
                       ", data=" + data +
                       ", error='" + error + '\'' +
                       '}';
    }

}
