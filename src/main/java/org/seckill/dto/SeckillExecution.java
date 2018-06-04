package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStatEnum;

/**
 * Crete by Marlon
 * Create Date: 2018/6/4
 * Class Describe  封装秒杀执行后结果
 **/

public class SeckillExecution {

    private long seckill;

    /**
     * 秒杀执行结果状态
     */
    private int state;

    /**
     * 状态的标识
     */
    private String stateInfo;

    /**
     * 秒杀成功对象
     */
    private SuccessKilled successKilled;


    public SeckillExecution(long seckill, SeckillStatEnum statEnum, SuccessKilled successKilled) {
        this.seckill = seckill;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    public SeckillExecution(long seckill, SeckillStatEnum statEnum) {
        this.seckill = seckill;
        this.state = statEnum.getState();
        this.stateInfo = statEnum.getStateInfo();
    }


    public long getSeckill() {
        return seckill;
    }

    public void setSeckill(long seckill) {
        this.seckill = seckill;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                       "seckill=" + seckill +
                       ", state=" + state +
                       ", stateInfo='" + stateInfo + '\'' +
                       ", successKilled=" + successKilled +
                       '}';
    }


}