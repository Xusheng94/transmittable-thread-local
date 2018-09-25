package com.alibaba.ttl.threadpool;

import com.alibaba.ttl.TransmittableThreadLocal;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Factory Utils for getting TTL wrapper of jdk executors.
 *
 * @author Jerry Lee (oldratlee at gmail dot com)
 * @see java.util.concurrent.Executor
 * @see java.util.concurrent.ExecutorService
 * @see java.util.concurrent.ThreadPoolExecutor
 * @see java.util.concurrent.ScheduledThreadPoolExecutor
 * @see java.util.concurrent.Executors
 * @see java.util.concurrent.CompletionService
 * @see java.util.concurrent.ExecutorCompletionService
 * @since 0.9.0
 */
public final class TtlExecutors {
    /**
     * {@link TransmittableThreadLocal} Wrapper of {@link Executor},
     * transmit the {@link TransmittableThreadLocal} from the task submit time of {@link Runnable}
     * to the execution time of {@link Runnable}.
     */
    public static Executor getTtlExecutor(Executor executor) {
        if (null == executor || executor instanceof ExecutorTtlWrapper) {
            return executor;
        }
        return new ExecutorTtlWrapper(executor);
    }

    /**
     * {@link TransmittableThreadLocal} Wrapper of {@link ExecutorService},
     * transmit the {@link TransmittableThreadLocal} from the task submit time of {@link Runnable} or {@link java.util.concurrent.Callable}
     * to the execution time of {@link Runnable} or {@link java.util.concurrent.Callable}.
     */
    public static ExecutorService getTtlExecutorService(ExecutorService executorService) {
        if (executorService == null || executorService instanceof ExecutorServiceTtlWrapper) {
            return executorService;
        }
        return new ExecutorServiceTtlWrapper(executorService);
    }

    /**
     * {@link TransmittableThreadLocal} Wrapper of {@link ScheduledExecutorService},
     * transmit the {@link TransmittableThreadLocal} from the task submit time of {@link Runnable} or {@link java.util.concurrent.Callable}
     * to the execution time of {@link Runnable} or {@link java.util.concurrent.Callable}.
     */
    public static ScheduledExecutorService getTtlScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
        if (scheduledExecutorService == null || scheduledExecutorService instanceof ScheduledExecutorServiceTtlWrapper) {
            return scheduledExecutorService;
        }
        return new ScheduledExecutorServiceTtlWrapper(scheduledExecutorService);
    }

    /**
     * Unwrap TTL wrapper executor instance to the original/underneath one.
     *
     * @param executor TTL wrapper instance of jdk executor
     * @param <T>      Executor type
     * @return the original/underneath executor
     * @throws NullPointerException     parameter executor is null
     * @throws IllegalArgumentException input executor is not TTL wrapper executor
     * @see #getTtlExecutor(Executor)
     * @see #getTtlExecutorService(ExecutorService)
     * @see #getTtlScheduledExecutorService(ScheduledExecutorService)
     * @since 2.8.0
     */
    @SuppressWarnings("unchecked")
    public static <T extends Executor> T unwrap(T executor) {
        if (executor == null) throw new NullPointerException("parameter executor is null");
        if (!(executor instanceof ExecutorTtlWrapper))
            throw new IllegalArgumentException("parameter executor not TTL executor wrapper");

        return (T) ((ExecutorTtlWrapper) executor).unwrap();
    }

    private TtlExecutors() {
    }
}
