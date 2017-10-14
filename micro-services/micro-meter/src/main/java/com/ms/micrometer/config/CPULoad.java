package com.ms.micrometer.config;

import java.lang.management.ManagementFactory;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;

/**
 * @author ms
 *
 */
public class CPULoad implements MeterBinder {
	private static Logger LOGGER = LoggerFactory.getLogger(CPULoad.class);
	
	@Override
	public void bindTo(MeterRegistry registry) {
		CPULoad cpu = new CPULoad();
		Gauge.builder("CPU.LOAD.AVG", cpu, (x) -> cpu.getProcessCpuLoad())
				.description("get cpu load")
				.tags("cpuload","get cpu load")
				.register(registry);
	}
	
	/**
	 * @return cpu load
	 */
	public double getProcessCpuLoad() {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		ObjectName name = null;
		AttributeList list = null;
		Attribute att = null;
		Double value = 0.0;
		try {
			name = ObjectName.getInstance("java.lang:type=OperatingSystem");
			list = mbs.getAttributes(name, new String[] { "ProcessCpuLoad" });
			LOGGER.info("Load ValueBefore " +list.isEmpty());
			if (list.isEmpty()) {
				return Double.NaN;
			}
			att = (Attribute) list.get(0);
			value = (Double) att.getValue();
			LOGGER.info("Load Value " +value);
			if (value == -1.0) {
				return Double.NaN;
			}
		} catch (Exception e) {
			LOGGER.error("Failed to calculate CPU Load",e);
		}
		return ((int) (value * 1000) / 10.0);
	}

}
