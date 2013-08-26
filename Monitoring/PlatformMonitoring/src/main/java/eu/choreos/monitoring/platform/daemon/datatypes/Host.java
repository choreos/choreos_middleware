package eu.choreos.monitoring.platform.daemon.datatypes;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Host {

    /* small | medium | large | extralarge */
    private String instanceType = "";
    private static int SMALL;
    private static int MEDIUM;
    private static int LARGE;
    private static int EXTRALARGE;

    private static final double REST = 1.5;
    private Map<String, Metric> metrics;
    private String clusterName;
    private String hostName;
    private String ip;
    private int tn;
    private int tmax;
    private long measurementTimestamp;

    public String getInstanceType() {
	return instanceType;
    }

    public void setInstanceType(String instanceType) {
	this.instanceType = instanceType;
    }

    public int getTmax() {
	return tmax;
    }

    public void setTmax(int tmax) {
	this.tmax = tmax;
    }

    public int getTn() {
	return tn;
    }

    public void setTn(int tn) {
	this.tn = tn;
    }

    public Host(String clusterName, String hostname, String ip, Map<String, Metric> metrics, int tn, int tmax,
	    String instanceType) {
	this(clusterName, hostname, ip, metrics, tn, tmax);
	this.instanceType = instanceType;
    }

    public Host(String clusterName, String hostname, String ip, Map<String, Metric> metrics, int tn, int tmax) {
	this.clusterName = clusterName;
	this.hostName = hostname;
	this.ip = ip;
	this.metrics = metrics;
	this.tn = tn;
	this.tmax = tmax;
	this.instanceType = verifyMyInstanceType();

	// set values
	Properties myProps = new Properties();

	try {
	    myProps.load(ClassLoader.getSystemResourceAsStream("monitoring.properties"));
	} catch (IOException e) {
	    System.err.println("Error while loading configuration");
	    return;
	}

	String sm = myProps.getProperty("SmallInstance.memory");
	String me = myProps.getProperty("MediumInstance.memory");
	String lr = myProps.getProperty("LargeInstance.memory");
	String xl = myProps.getProperty("ExtraLargeInstance.memory");

	SMALL = Integer.parseInt(sm);
	MEDIUM = Integer.parseInt(me);
	LARGE = Integer.parseInt(lr);
	EXTRALARGE = Integer.parseInt(xl);
    }

    private String verifyMyInstanceType() {
	if (!this.metrics.containsKey("mem_total"))
	    return "small";

	Metric m = this.metrics.get("mem_total");
	int mem_total = Integer.parseInt(m.getValue());

	if (isApproximated(mem_total, SMALL))
	    return "small";
	else if (isApproximated(mem_total, MEDIUM))
	    return "medium";
	else if (isApproximated(mem_total, LARGE))
	    return "large";
	else if (mem_total > LARGE * 1.25)
	    return "extralarge";

	return "small";
    }

    private boolean isApproximated(int measured, int thr) {
	if (measured >= ((double) thr * 0.75) && measured <= ((double) thr * 1.25))
	    return true;
	return false;
    }

    public String toString() {
	return this.getHostName() + " " + this.getIp();
    }

    public String getHostName() {
	return hostName;
    }

    public String getIp() {
	return ip;
    }

    public boolean isDown() {
	return tn > REST * tmax;// + dmax;
    }

    public String getClusterName() {
	return clusterName;
    }

    public String getMetricValue(String metric) {
	if (metrics.containsKey(metric)) {
	    return metrics.get(metric).getValue();
	} else {
	    return "0";
	}
    }

    public void setLastMeasurementTimestamp(long timestamp) {
	this.measurementTimestamp = timestamp;
    }

    public long getLastMeasurementTimestamp() {
	return measurementTimestamp;
    }
}
