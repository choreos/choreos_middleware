package eu.choreos.monitoring.platform.daemon.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Host {

	private Map<String, Metric> metrics;
	private String clusterName;
	private String hostName;
	private String ip;
	private boolean isDown;
	
	public Host(String clusterName, String hostname, String ip, Map<String, Metric> metrics) {
		this.clusterName = clusterName;
		this.hostName = hostname;
		this.ip = ip;
		this.metrics = metrics;
		//isDown = this.isMetricsEmpty();
		isDown = this.thereAreTNSurpassed();
	}
	
	private boolean thereAreTNSurpassed() {
		
		for (String s : metrics.keySet()) {
			if(metrics.get(s).getTn() > 2 * metrics.get(s).getTmax() + metrics.get(s).getDmax())
				return true;
		}
		
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
		return isDown;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	private boolean isMetricsEmpty() {
		return metrics.isEmpty();
	}
	
	public String getMetricValue(String metric)  {
		return metrics.get(metric).getValue();
	}
	

}
