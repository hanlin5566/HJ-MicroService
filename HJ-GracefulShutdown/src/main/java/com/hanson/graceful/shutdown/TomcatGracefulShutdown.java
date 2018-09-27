package com.hanson.graceful.shutdown;

import org.apache.catalina.connector.Connector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * Create by hanlin on 2018年6月19日
 * 只处理的tomcat容器的线程池，当请求到shutdown时，关闭容器的线城池，也就是说只有controller内的线程会执行完毕，
 * 如果controller内的线程又另启动了线程或者线程池无法控制。 会报出停止异常。
 **/
public class TomcatGracefulShutdown implements Shutdown, TomcatConnectorCustomizer {

	private volatile Connector connector;

	private static final Log logger = LogFactory.getLog(TomcatGracefulShutdown.class);

	@Override
	public void pause() throws InterruptedException {
		// 容器暂停，不接收新的请求
		this.connector.pause();
	}

	@Override
	public void customize(Connector connector) {
		this.connector = connector;
	}

	@Override
	public void shutdown(Integer waitTime) throws InterruptedException {
		// 关闭容器的线程池
		Executor executor = this.connector.getProtocolHandler().getExecutor();
		if (executor instanceof ThreadPoolExecutor) {
			try {
				ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
				threadPoolExecutor.shutdown();
				if (!threadPoolExecutor.awaitTermination(waitTime, TimeUnit.SECONDS)) {
					logger.warn("Tomcat thread pool did not shut down gracefully within " + waitTime
							+ " seconds. Proceeding with forceful shutdown");
				} else {
					logger.debug("Tomcat thread pool is empty, we stop now");
				}
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
