package eu.choreos.monitoring.platform.daemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdManager {

	private HostManager hostManager;
	private Set<AbstractThreshold> thresholds;
	Map<String, List<AbstractThreshold>> surpassedThresholds;

	public ThresholdManager(HostManager hostManager) {
		this.hostManager = hostManager;
		thresholds = new HashSet<AbstractThreshold>();
		surpassedThresholds = new HashMap<String, List<AbstractThreshold>>();
	}

	public void addThreshold(AbstractThreshold threshold) {
			thresholds.add(threshold);
		return;
	}

	public void addMultipleThresholds(List<AbstractThreshold> thresholdList) {
		thresholds.addAll(thresholdList);
	}
	
	public void updateThresholdsInfo() throws GangliaException {
		surpassedThresholds.clear();
		hostManager.getDataReaderHostInfo();
		surpassedThresholds = getSurpassedThresholdsForAllHosts();
	}
	
	public boolean thereAreSurpassedThresholds() throws GangliaException {
		updateThresholdsInfo();
		return !surpassedThresholds.isEmpty();
	}
	
	public Map<String, List<AbstractThreshold>> getSurpassedThresholds() throws GangliaException {
		return surpassedThresholds;
	}
	

	private double getMetricNumericalValue(String metricName, Host host) throws GangliaException {
		String value = host.getMetricValue(metricName);
		return Double.parseDouble(value);
	}

	private List<AbstractThreshold> getAllSurpassedThresholds(Host host) throws GangliaException {

		List<AbstractThreshold> surpassedThresholds = new ArrayList<AbstractThreshold>();
		
		if(host.isDown()) {
			SingleThreshold t = new SingleThreshold("host_down", SingleThreshold.DOWN, 0);
			surpassedThresholds.add(t); return surpassedThresholds;
		}

		for (AbstractThreshold threshold : thresholds) {

			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName, host);

			if (threshold.wasSurpassed(metricValue))
				surpassedThresholds.add(threshold);
		}
		return surpassedThresholds;
	}
	
	private Map<String, List<AbstractThreshold>> getSurpassedThresholdsForAllHosts() throws GangliaException {
		Map<String, List<AbstractThreshold>> surpassedThresholds = new HashMap<String, List<AbstractThreshold>>();

		for(Host host : hostManager.getHosts()){
			List<AbstractThreshold> thresholdsOfHost = getAllSurpassedThresholds(host);
			
			if (!thresholdsOfHost.isEmpty()) {
				surpassedThresholds.put(host.getHostName(), new ArrayList<AbstractThreshold>());
			
				for(AbstractThreshold t : thresholdsOfHost)
					surpassedThresholds.get(host.getHostName()).add(t);
			}
		}
		
		return surpassedThresholds;
				
	}

	public int getThresholdSize() {
		return thresholds.size();
	}

}
