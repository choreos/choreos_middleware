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
	private Set<Threshold> thresholds;
	Map<String, List<Threshold>> surpassedThresholds;

	public ThresholdManager(HostManager hostManager) {
		this.hostManager = hostManager;
		thresholds = new HashSet<Threshold>();
		surpassedThresholds = new HashMap<String, List<Threshold>>();
	}

	public void addThreshold(Threshold threshold) {
			thresholds.add(threshold);
		return;
	}

	public void addMultipleThresholds(List<Threshold> thresholdList) {
		thresholds.addAll(thresholdList);
	}
	
	public void updateThresholdsInfo() throws GangliaException {
		surpassedThresholds.clear();
		hostManager.updateHostsInfo();
		surpassedThresholds = getSurpassedThresholdsForAllHosts();
	}
	
	public boolean thereAreSurpassedThresholds() {
		return !surpassedThresholds.isEmpty();
	}
	
	public Map<String, List<Threshold>> getSurpassedThresholds() {
		return surpassedThresholds;
	}

	private double getMetricNumericalValue(String metricName, Host host) throws GangliaException {
		String value = host.getMetricValue(metricName);
		return Double.parseDouble(value);
	}

	private List<Threshold> getAllSurpassedThresholds(Host host) throws GangliaException {

		List<Threshold> surpassedThresholds = new ArrayList<Threshold>();

		for (Threshold threshold : thresholds) {

			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName, host);

			if (threshold.wasSurpassed(metricValue))
				surpassedThresholds.add(threshold);
		}
		return surpassedThresholds;
	}
	
	private Map<String, List<Threshold>> getSurpassedThresholdsForAllHosts() throws GangliaException {
		Map<String, List<Threshold>> surpassedThresholds = new HashMap<String, List<Threshold>>();

		for(Host host : hostManager.getRegisteredHosts()){
			List<Threshold> thresolds = getAllSurpassedThresholds(host);
			if (!thresolds.isEmpty()) {
				surpassedThresholds.put(host.getHostName(), new ArrayList<Threshold>());
			
				for(Threshold threshold : thresholds)
					surpassedThresholds.get(host.getHostName()).add(threshold);
			}
		}
		
		return surpassedThresholds;
				
	}

	public int getThresholdSize() {
		return thresholds.size();
	}

}
