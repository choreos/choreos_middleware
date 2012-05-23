package eu.choreos.monitoring.platform.daemon.datatypes;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Host {

	private Map<String, Gmetric> metrics;
	private String hostName;
	private String ip;
	
	public Host(Element hostNode) {
		setMetrics(hostNode);
	}
	
	public String getHostName() {
		return hostName;
	}

	public String getIp() {
		return ip;
	}

	
	public void setMetrics(Map<String, Gmetric> metrics) {
		this.metrics = metrics;
	}
	
	public boolean isMetricsEmpty() {
		return metrics.isEmpty();
	}
	
	public String getMetricValue(String metric)  {
		return metrics.get(metric).getValue();
	}
	
	public void setMetrics(Element hostNode) {
		metrics = new HashMap<String, Gmetric>();
		hostName = hostNode.getAttributeNode("NAME").getNodeValue();
		ip = hostNode.getAttributeNode("IP").getNodeValue();
		
		NodeList metricNodeList = hostNode.getElementsByTagName("METRIC");

		for (int i = 0; i < metricNodeList.getLength(); i++) {

			Element el = (Element) metricNodeList.item(i);

			metrics.put(el.getAttributeNode("NAME").getNodeValue(),
					new Gmetric(el.getAttributeNode("NAME").getNodeValue(), el
							.getAttributeNode("VAL").getNodeValue()));
		}
	}

}
