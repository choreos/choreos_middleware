package eu.choreos.monitoring.platform.daemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdManager {
	
	private HostManager hostManager;
	
	/* Map of thresholds to be analysed */
	Map<String, List<AbstractThreshold>> expectedThresholdsSpecs;
	
	/* Map for put surpassed thresholds separated per host */
	Map<String, List<AbstractThreshold>> surpassedThresholds;

	public ThresholdManager(HostManager hostManager) {
		this.hostManager = hostManager;
		expectedThresholdsSpecs = new HashMap<String, List<AbstractThreshold>>();
		surpassedThresholds = new HashMap<String, List<AbstractThreshold>>();
	}

	/* add a threshold to a list of threshold for instances of type instanceType */
	public void addThreshold(String instanceType, AbstractThreshold threshold) {
		if(expectedThresholdsSpecs.get(instanceType) == null)
			expectedThresholdsSpecs.put(instanceType, new ArrayList<AbstractThreshold>());
		expectedThresholdsSpecs.get(instanceType).add(threshold);
		return;
	}

	/* add a list of threshold to a list of threshold for instance of type instanceType */
	public void addMultipleThresholds(String instanceType, List<AbstractThreshold> thresholdList) {
		if(expectedThresholdsSpecs.get(instanceType) == null)
			expectedThresholdsSpecs.put(instanceType, new ArrayList<AbstractThreshold>());
		expectedThresholdsSpecs.get(instanceType).addAll(thresholdList);
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

		List<AbstractThreshold> tempSurpassedThresholds = new ArrayList<AbstractThreshold>();
		
		if(host.isDown()) {

			SingleThreshold t = new SingleThreshold("host_down", SingleThreshold.DOWN, 0);
			t.setTimestampOccur(host.getLastMeasurementTimestamp());
			tempSurpassedThresholds.add(t); 
			
			return tempSurpassedThresholds;
		}
		
		if(host.getInstanceType().compareToIgnoreCase("small") == 0) {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("small", host));
			
		} else if(host.getInstanceType().compareToIgnoreCase("medium") == 0) {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("medium", host));
		
		} else if(host.getInstanceType().compareToIgnoreCase("large") == 0) {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("large", host));
		
		} else if(host.getInstanceType().compareToIgnoreCase("extralarge") == 0) {
		
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("extralarge", host));
		}
		
		/* apply default thresholds. */
		tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));

		return tempSurpassedThresholds;
	}
	
	private List<AbstractThreshold> getSurpassedThresholdByInstanceType(String instancetype, Host host) throws GangliaException {

		List<AbstractThreshold> surpassed = new ArrayList<AbstractThreshold>();

		if(!expectedThresholdsSpecs.keySet().contains(instancetype)) 
			return surpassed;
			
		for (AbstractThreshold threshold : expectedThresholdsSpecs.get(instancetype)) {
			
			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName, host);

			if (threshold.wasSurpassed(metricValue)) {
				
				AbstractThreshold at = null;
				try {
					at = (AbstractThreshold) threshold.clone();
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
				
				at.setTimestampOccur(host.getLastMeasurementTimestamp());
				at.setlastMeasurement(metricValue);
				
				surpassed.add(at);
			}
		}
		
		return surpassed;
	}
	
	private Map<String, List<AbstractThreshold>> getSurpassedThresholdsForAllHosts() throws GangliaException {
		
		Map<String, List<AbstractThreshold>> surpassedThresholds = new HashMap<String, List<AbstractThreshold>>();

		for(Host host : hostManager.getHosts()){
			List<AbstractThreshold> thresholdsOfHost = getAllSurpassedThresholds(host);
			
			if (!thresholdsOfHost.isEmpty()) {
				surpassedThresholds.put(host.getIp(), new ArrayList<AbstractThreshold>());
			
				for(AbstractThreshold t : thresholdsOfHost)
					surpassedThresholds.get(host.getIp()).add(t);
			}
		}
		
		return surpassedThresholds;
				
	}

	public int getThresholdSize() {
		return expectedThresholdsSpecs.size();
	}

	public void addAllThreshold(
			Map<String, List<AbstractThreshold>> thresholdConfig) {

		this.expectedThresholdsSpecs = thresholdConfig;
	}

}
