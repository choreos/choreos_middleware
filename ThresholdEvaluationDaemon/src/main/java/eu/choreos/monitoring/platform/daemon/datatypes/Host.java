package eu.choreos.monitoring.platform.daemon.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Host {

	private static final double REST = 1.5;
	private Map<String, Metric> metrics;
	private String clusterName;
	private String hostName;
	private String ip;
	private int tn;
	private int tmax;
	
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

	public Host(String clusterName, String hostname, String ip, Map<String, Metric> metrics,
			int tn, int tmax) {
		this.clusterName = clusterName;
		this.hostName = hostname;
		this.ip = ip;
		this.metrics = metrics;
		this.tn = tn;
		this.tmax = tmax;
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
	
	public String getMetricValue(String metric)  {
		return metrics.get(metric).getValue();
	}
	

}
