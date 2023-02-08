package com.cactus.springboot.mysql.batch.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池配置
 *
 * @Author yejx
 * @Date 2023/2/3
 */
public class ExecutorConfig {

    private static final int maxPoolSize = Runtime.getRuntime().availableProcessors();

    private volatile static ExecutorService executorService;

    /**
     * 私有构造方法，禁止实例化
     */
    private ExecutorConfig() {

    }

    /**
     * 获取线程池
     *
     * @return
     */
    public static ExecutorService getThreadPool() {
        if (executorService == null) {
            synchronized (ExecutorConfig.class) {
                if (executorService == null) {
                    executorService = newThreadPool();
                }
            }
        }
        return executorService;
    }

    /**
     * 创建线程池
     *
     * @return
     */
    private static ExecutorService newThreadPool() {
        int queueSize = 500;
        int corePool = Math.min(5, maxPoolSize);
        return new ThreadPoolExecutor(
                corePool,
                maxPoolSize,
                10000L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
