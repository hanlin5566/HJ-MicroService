package com.hanson.graceful.shutdown;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create by hanlin on 2018年6月19日
 */
public class UndertowGracefulShutdown implements Shutdown {

    @Autowired
    private UndertowGracefulShutdownWrapper undertowGracefulShutdownWrapper;

    private static final Log logger = LogFactory.getLog(UndertowGracefulShutdown.class);

    public void pause() throws InterruptedException {
        // Nothing todo with Undertow.
    }

    public void shutdown(Integer waitTime) throws InterruptedException {
    	try {
    		undertowGracefulShutdownWrapper.getGracefulShutdownHandler().shutdown();
    		undertowGracefulShutdownWrapper.getGracefulShutdownHandler().awaitShutdown(waitTime * 1000);
    		logger.debug("Undertow shut down gracefully,we stop now.");
		} catch (Exception e) {
			logger.warn("Undertow thread pool did not shut down gracefully within " + waitTime
					+ " seconds. Proceeding with forceful shutdown");
		}
    }
}
