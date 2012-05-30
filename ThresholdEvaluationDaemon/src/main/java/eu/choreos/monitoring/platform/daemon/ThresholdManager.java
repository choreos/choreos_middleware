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

	public ThresholdManager(HostManager hostManager) {
		this.hostManager = hostManager;
		thresholds = new HashSet<Threshold>();
	}

	public void addThreshold(Threshold threshold) {
			thresholds.add(threshold);
		return;
	}

	public void addMultipleThresholds(List<Threshold> thresholdList) {
		thresholds.addAll(thresholdList);
	}

	private double getMetricNumericalValue(String metricName, Host host) throws GangliaException {
		String value = host.getMetricValue(metricName);
		return Double.parseDouble(value);
	}

	public List<Threshold> getAllSurpassedThresholds(Host host) throws GangliaException {

		List<Threshold> surpassedThresholds = new ArrayList<Threshold>();

		for (Threshold threshold : thresholds) {

			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName, host);

			if (threshold.wasSurpassed(metricValue))
				surpassedThresholds.add(threshold);
		}
		return surpassedThresholds;
	}
	
	public Map<String, List<Threshold>> getSurpassedThresholdsForAllHosts() throws GangliaException {
		Map<String, List<Threshold>> surpassedThresholds = new HashMap<String, List<Threshold>>();

		for(Host host : hostManager.getRegisteredHosts()){
			surpassedThresholds.put(host.getHostName(), new ArrayList<Threshold>());
			
			for(Threshold threshold : getAllSurpassedThresholds(host))
				surpassedThresholds.get(host.getHostName()).add(threshold);
		
		}
		
		return surpassedThresholds;
				
	}

	public int getThresholdSize() {
		return thresholds.size();
	}

}
