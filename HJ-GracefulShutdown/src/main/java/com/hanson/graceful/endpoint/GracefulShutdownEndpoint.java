package com.hanson.graceful.endpoint;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import com.hanson.graceful.shutdown.Shutdown;

/**
 * Actuator endpoint，扩展了actuator，实现优雅停机属性的监控。
 */
@ConfigurationProperties(prefix = "endpoints.shutdown.graceful")
public class GracefulShutdownEndpoint extends AbstractEndpoint<Map<String, Object>>
		implements ApplicationListener<ContextClosedEvent>, ApplicationContextAware {

	private ConfigurableApplicationContext context;

	private Integer timeout;

	private Integer wait;

	private Date startShutdown;

	private Date stopShutdown;

	@Autowired
	private Shutdown shutdown;

	private static final Map<String, Object> NO_CONTEXT_MESSAGE = Collections
			.unmodifiableMap(Collections.<String, Object> singletonMap("message", "No context to shutdown."));

	private static final Map<String, Object> SHUTDOWN_MESSAGE = Collections
			.unmodifiableMap(Collections.<String, Object> singletonMap("message", "Graceful shutting down, bye..."));

	private static final Log logger = LogFactory.getLog(GracefulShutdownEndpoint.class);

	public GracefulShutdownEndpoint(final Integer timeout, final Integer wait) {
		super("shutdowngraceful", Boolean.TRUE, Boolean.FALSE);
		this.timeout = timeout;
		this.wait = wait;
	}

	public void onApplicationEvent(final ContextClosedEvent event) {
		if (stopShutdown != null && startShutdown != null) {
			final long seconds = (stopShutdown.getTime() - startShutdown.getTime()) / 1000;
			logger.info("Shutdown performed in " + seconds + " second(s)");
		}
	}

	public Map<String, Object> invoke() {
		if (this.context == null) {
			return NO_CONTEXT_MESSAGE;
		}
		try {
			return SHUTDOWN_MESSAGE;
		} finally {

			final Thread thread = new Thread(new Runnable() {

				public void run() {

					try {
						// We top the start
						startShutdown = new Date();

						// Set Health Checker in OUT_OF_SERVICE state.
						logger.info("We are now in OUT_OF_SERVICE mode, please wait " + wait + " second(s)...");
						Thread.sleep(wait * 1000);
						shutdown.pause();

						// Pause the protocol.
						logger.info(
								"Graceful shutdown in progess... We don't accept new connection... Wait after latest connections (max : "
										+ timeout + " seconds)... ");

						// perform stop
						shutdown.shutdown(timeout);

						// Close spring context.
						stopShutdown = new Date();
						context.close();
					} catch (final InterruptedException ex) {
						logger.error("The await termination has been interrupted : " + ex.getMessage());
						Thread.currentThread().interrupt();
					}
				}
			});

			// Link and start thread.
			thread.setContextClassLoader(getClass().getClassLoader());
			thread.start();
		}
	}

	public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
		if (applicationContext instanceof ConfigurableApplicationContext) {
			this.context = (ConfigurableApplicationContext) applicationContext;
		}
	}

	public Date getStartShutdown() {
		return startShutdown;
	}
}
