package org.ow2.choreos.services.datamodel.qos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResourceLimits implements Serializable {

    private static final String MAX_CPU_USAGE = "max_cpu_usage";
    private static final String MIN_CPU_USAGE = "min_cpu_usage";

    public static Map<String, Float> limits = new HashMap<String, Float>();

    static {
	limits.put(MAX_CPU_USAGE, 90.0f);
	limits.put(MIN_CPU_USAGE, 30.0f);
    }

    public float getMaxCPUUsage() {
	return limits.get(MAX_CPU_USAGE);
    }

    public float getMinCPUUsage() {
	return limits.get(MIN_CPU_USAGE);
    }

}
