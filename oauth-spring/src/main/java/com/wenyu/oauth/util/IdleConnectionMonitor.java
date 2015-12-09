package com.wenyu.oauth.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.ContextLoader;

import java.util.concurrent.TimeUnit;

/**
 * Created by Zhaowy on 2014/9/10.
 */
public final class IdleConnectionMonitor {
    private final Log logger = LogFactory.getLog(getClass());


    @Scheduled(fixedRate = 20000)
    public void httpConnectionRelease() {
        PoolingHttpClientConnectionManager connectionManager = (PoolingHttpClientConnectionManager) ContextLoader.getCurrentWebApplicationContext().getBean("httpPoolManager");
        if (logger.isInfoEnabled()) {
            logger.info("release start connect count:=" + connectionManager.getTotalStats().getAvailable());
        }
        // Close expired connections
        connectionManager.closeExpiredConnections();
        // Optionally, close connections
        // that have been idle longer than readTimeout*2 MILLISECONDS
        connectionManager.closeIdleConnections(60000, TimeUnit.MILLISECONDS);

        if (logger.isInfoEnabled()) {
            logger.info("release end connect count:=" + connectionManager.getTotalStats().getAvailable());
            logger.info("release end connect count:=" + connectionManager.getTotalStats().getMax());

        }

    }
}
