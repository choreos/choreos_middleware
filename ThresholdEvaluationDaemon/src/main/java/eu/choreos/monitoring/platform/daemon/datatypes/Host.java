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
	
	public Host(Element hostNode, String clusterName) {
		setMetrics(hostNode);
		this.clusterName = clusterName;
		isDown = false;
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
	
	public boolean getIsDown() {
		return isDown;
	}
	
	public void setIsDown(boolean isDown) {
		this.isDown = isDown;
	}
	
	public String getClusterName() {
		return clusterName;
	}
	
	public void setMetrics(Map<String, Metric> metrics) {
		this.metrics = metrics;
	}
	
	public boolean isMetricsEmpty() {
		return metrics.isEmpty();
	}
	
	public String getMetricValue(String metric)  {
		return metrics.get(metric).getValue();
	}
	
	public void setMetrics(Element hostNode) {
		metrics = new HashMap<String, Metric>();
		hostName = hostNode.getAttributeNode("NAME").getNodeValue();
		ip = hostNode.getAttributeNode("IP").getNodeValue();
		
		NodeList metricNodeList = hostNode.getElementsByTagName("METRIC");

		for (int i = 0; i < metricNodeList.getLength(); i++) {

			Element el = (Element) metricNodeList.item(i);

			metrics.put(el.getAttributeNode("NAME").getNodeValue(),
					new Metric(el.getAttributeNode("NAME").getNodeValue(), el
							.getAttributeNode("VAL").getNodeValue()));
		}
	}

}
