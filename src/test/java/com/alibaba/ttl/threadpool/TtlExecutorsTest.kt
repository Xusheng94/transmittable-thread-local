package com.alibaba.ttl.threadpool

import org.junit.Assert.*
import org.junit.Test
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * @author Jerry Lee (oldratlee at gmail dot com)
 */
class TtlExecutorsTest {
    @Test
    fun test_null() {
        assertNull(TtlExecutors.getTtlExecutor(null))
        assertNull(TtlExecutors.getTtlExecutorService(null))
        assertNull(TtlExecutors.getTtlScheduledExecutorService(null))
    }

    @Test
    fun test_unwrap() {
        val scheduledThreadPool = Executors.newScheduledThreadPool(3)
        val ttlScheduledExecutorService = TtlExecutors.getTtlScheduledExecutorService(scheduledThreadPool)

        val unwrap: Executor = TtlExecutors.unwrap(ttlScheduledExecutorService as Executor)
        assertSame(scheduledThreadPool, unwrap)

        val executorService: ExecutorService = TtlExecutors.unwrap(ttlScheduledExecutorService as ExecutorService)
        assertSame(scheduledThreadPool, executorService)

        val scheduledExecutorService: ScheduledExecutorService = TtlExecutors.unwrap(ttlScheduledExecutorService)
        assertSame(scheduledThreadPool, scheduledExecutorService)
    }


    @Test
    fun test_unwrap_null() {
        try {
            TtlExecutors.unwrap(null)
            fail()
        } catch (expected: NullPointerException) {
            assertEquals("parameter executor is null", expected.message)
        }
    }

    @Test
    fun test_unwrap_IllegalArgumentException() {
        try {
            TtlExecutors.unwrap(Executors.newScheduledThreadPool(3))
            fail()
        } catch (expected: IllegalArgumentException) {
            assertEquals("parameter executor not TTL executor wrapper", expected.message)
        }
    }
}
