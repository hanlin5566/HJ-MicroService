package com.hanson.graceful.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Create by hanlin on 2018年6月20日
 **/
@ConfigurationProperties(prefix = "endpoints.shutdown.graceful")
public class GracefulShutdownProperties {

    /**
     * The timer before launch graceful shutdown.
     * The health checker return OUT_OF_SERVICE.
     */
    private Integer wait = 30;

    /**
     * 关闭应用超时时间，单位秒
     */
    private Integer timeout = 30;

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public Integer getWait() {
        return wait;
    }

    public void setWait(Integer wait) {
        this.wait = wait;
    }
}
