package com.ms.micrometer.other;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import io.micrometer.core.instrument.Counter;
import org.springframework.beans.factory.annotation.Autowired;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.instrument.Gauge;


public class ThreadPoolMetrics implements MeterBinder, RejectedExecutionHandler {

    static Logger logger = Logger.getLogger("ThreadPoolMetric");
    private MeterRegistry registry;
    private ThreadPoolExecutor executor;

    private Gauge activeCount;
    private Gauge maxPoolSize;
    private Gauge corePoolSize;
    private Gauge queueSize;
    private Gauge version;
    private Counter rejectCount;


    public ThreadPoolMetrics() {

    }

    public void setExecutor(ThreadPoolExecutor executor) {
        this.executor = executor;
        this.executor.setRejectedExecutionHandler(this);
        this.executor.setCorePoolSize(5);
        this.executor.setMaximumPoolSize(200);
    }

    // We need this getters also for the healthz checks.
    public Double getMaximumPoolSize() {
        if (executor != null) {
            return (double) executor.getMaximumPoolSize();
        }
        return -1.0;
    }

    public Double getCorePoolSize() {
        if (executor != null) {
            return (double) executor.getCorePoolSize();
        }
        return -1.0;
    }

    public Double getActiveCount() {
        if (executor != null) {
            return (double) executor.getActiveCount();
        }
        return -1.0;
    }

    public Double getQueueLength() {
        if (executor != null) {
            return (double) executor.getQueue().size();
        }
        return -1.0;
    }

	@Override
    public void bindTo(MeterRegistry registry) {
        maxPoolSize = Gauge.builder("tomcat_threads_max_pool_size", this, ThreadPoolMetrics::getMaximumPoolSize)
                .baseUnit("count")
                .description("The maximum allowed number of threads.")
                .register(registry);

        corePoolSize = Gauge.builder("tomcat_threads_core_pool_size", this, ThreadPoolMetrics::getCorePoolSize)
                .baseUnit("count")
                .description("The number of threads to keep in the pool, even if they are idle.")
                .register(registry);

        activeCount = Gauge.builder("tomcat_threads_active_count", this, ThreadPoolMetrics::getActiveCount)
                .baseUnit("count")
                .description("The approximate number of threads that are actively executing tasks.")
                .register(registry);

        queueSize = Gauge.builder("tomcat_threads_queue_size", this, ThreadPoolMetrics::getQueueLength)
                .baseUnit("count")
                .description("The number of tasks queued, because no thread available")
                .register(registry);

        version = Gauge.builder("version", 1, Number::doubleValue)
                .tags("branch", "rel-1.0.0")
                .register(registry);

        rejectCount = Counter.builder("tomcat_threads_rejected")
                .baseUnit("count")
                .description("The number of thread executions dropped")
                .register(registry);
    }

    @Override
    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
        rejectCount.increment();
    }
}
