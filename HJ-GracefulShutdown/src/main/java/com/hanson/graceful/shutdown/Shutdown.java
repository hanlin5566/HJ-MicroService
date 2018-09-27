package com.hanson.graceful.shutdown;

/**
 * Create by hanlin on 2018年6月19日
 * 停机服务接口
 */
public interface Shutdown {

    /**
     * 执行中断操作，中断服务，使容器不接收新的请求。
     * @throw InterruptedException
     */
    void pause() throws InterruptedException;

    /**
     * 执行停止操作，停止容器。
     * @param 等待容器关闭时间
     * @throw InterruptedException
     */
    void shutdown(Integer waitTime) throws InterruptedException;
}
