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

	/* Could return a List cause here all surpassed threshold differs only host name */
	private List<AbstractThreshold> getAllSurpassedThresholds(Host host) throws GangliaException {

		List<AbstractThreshold> tempSurpassedThresholds = new ArrayList<AbstractThreshold>();
		
		if(host.isDown()) {
			SingleThreshold t = new SingleThreshold("host_down", SingleThreshold.DOWN, 0);
			tempSurpassedThresholds.add(t); 
			
			/* remove this return statement to continue send last measured threshold. Can be used to
			 * get information about cause of host is down */
			return tempSurpassedThresholds;
		}
		
		if(host.getInstanceType().equalsIgnoreCase("small")) {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));
			
		} else if(host.getInstanceType() == "medium") {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));
		
		} else if(host.getInstanceType() == "large") {
			
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));
		
		} else if(host.getInstanceType() == "extralarge") {
		
			tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));
		
		}
		
		/* apply default thresholds. */
		tempSurpassedThresholds.addAll(this.getSurpassedThresholdByInstanceType("default", host));

		return tempSurpassedThresholds;
	}
	
	private List<AbstractThreshold> getSurpassedThresholdByInstanceType(String instancetype, Host host) throws GangliaException {

		List<AbstractThreshold> surpassed = new ArrayList<AbstractThreshold>();

		if(!expectedThresholdsSpecs.keySet().contains(instancetype)) return surpassed;
			
		for (AbstractThreshold threshold : expectedThresholdsSpecs.get(instancetype)) {
			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName, host);

			if (threshold.wasSurpassed(metricValue))
				surpassed.add(threshold);
		}
		
		return surpassed;
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
		return expectedThresholdsSpecs.size();
	}

	public void addAllThreshold(
			Map<String, List<AbstractThreshold>> thresholdConfig) {

		this.expectedThresholdsSpecs = thresholdConfig;
	}

}
