package eu.choreos.monitoring.daemon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import eu.choreos.monitoring.*;

public class AnomalyNotifier {

    private GmondDataReader dataReader;
    private List<Threshold> thresholds;
    private Map<String, Gmetric> metricsMap;
	

    public AnomalyNotifier(GmondDataReader dataReader) {
	this.dataReader = dataReader;
	thresholds = new ArrayList<Threshold>();
    }

    public Map<String, Gmetric> getMetricsMap() {
	return metricsMap;
    }

    public int addThreshold(Threshold threshold) {
	thresholds.add(threshold);
	return thresholds.indexOf(threshold);

    }

    public void removeThreshold(Threshold threshold) {
	thresholds.remove(threshold);
	return;

    }

    public void removeThreshold(int index) {
	thresholds.remove(thresholds.get(index));
	return;

    }

    public boolean evaluateSingleThreshold(int index) {
	Threshold evaluatedThreshold = thresholds.get(index);
	return evaluatedThreshold.evaluate(getMetricNumericalValue(evaluatedThreshold.getName()));
    }

    private double getMetricNumericalValue(String metricName) {
	metricsMap = dataReader.getAllMetrics();

	if (metricsMap == null) {
	    System.out.println("ERROR: Could not get metrics");
	    System.exit(1);
	}
	return Double.parseDouble(metricsMap.get(metricName).getValue());
    }

    public Map<Integer, Boolean> evaluateAllThresholds() {
	    
	    HashMap<Integer, Boolean> thresholdEvaluationMap = new HashMap<Integer, Boolean>();

	    for (Threshold threshold : thresholds){

		String thresholdName = threshold.getName();
		Double metricValue = getMetricNumericalValue(thresholdName);
		
		thresholdEvaluationMap.put(thresholds.indexOf(threshold), threshold.evaluate(metricValue));
	    }
	    return thresholdEvaluationMap;
	}
}
