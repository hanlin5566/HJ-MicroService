package com.hanson.graceful.shutdown;

import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.GracefulShutdownHandler;

/**
 * Create by hanlin on 2018年6月19日
 * Undertow优雅停机
 */
public class UndertowGracefulShutdownWrapper implements HandlerWrapper {

    private GracefulShutdownHandler gracefulShutdownHandler;

    public HttpHandler wrap(final HttpHandler handler) {
        if(gracefulShutdownHandler == null) {
            this.gracefulShutdownHandler = new GracefulShutdownHandler(handler);
        }
        return gracefulShutdownHandler;
    }

    public GracefulShutdownHandler getGracefulShutdownHandler() {
        return gracefulShutdownHandler;
    }

}
