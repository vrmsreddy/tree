package com.ms.micrometer.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedServletContainerInitializedEvent;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.ms.micrometer.other.ThreadPoolMetrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.JvmGcMetrics;
import io.micrometer.core.instrument.binder.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.ProcessorMetrics;

@Component
public class TomcatStartedListener implements ApplicationListener<EmbeddedServletContainerInitializedEvent> {

	@Autowired
	private MeterRegistry registry;

	@Autowired
	private ThreadPoolMetrics metrics;
	
	@Override
	public void onApplicationEvent(EmbeddedServletContainerInitializedEvent event) {

		TomcatEmbeddedServletContainer tomcat = (TomcatEmbeddedServletContainer) event.getSource();
		Executor e = tomcat.getTomcat().getConnector().getProtocolHandler().getExecutor();
		ThreadPoolExecutor tpe = (ThreadPoolExecutor)e;
		
		if (tomcat.getTomcat().getService().getDomain().equals("Tomcat")){
			//metrics = new ThreadPoolMetrics(tpe);
			metrics.setExecutor(tpe);
			// This is done by spring boot autoconf, before we even arrive here.
			//metrics.bindTo(registry);
		}
		
		new ClassLoaderMetrics().bindTo(registry);
		new JvmMemoryMetrics().bindTo(registry);
		new JvmGcMetrics().bindTo(registry);
		new ProcessorMetrics().bindTo(registry);
		new JvmThreadMetrics().bindTo(registry);
	}
}

