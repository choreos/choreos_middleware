package eu.choreos.monitoring.daemon;

import java.util.ArrayList;
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

	public List<Threshold> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<Threshold> thresholds) {
		this.thresholds = thresholds;
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

	public boolean wasSurpassed(Threshold threshold) {
		return threshold.wasSurpassed(getMetricNumericalValue(threshold
				.getName()));
	}

	private double getMetricNumericalValue(String metricName) {
		metricsMap = dataReader.getAllMetrics();

		if (metricsMap == null) {
			System.out.println("ERROR: Could not get metrics");
			System.exit(1);
		}
		return Double.parseDouble(metricsMap.get(metricName).getValue());
	}

	public List<Threshold> getAllSurpassedThresholds() {

		List<Threshold> surpassedThresholds = new ArrayList<Threshold>();

		for (Threshold threshold : thresholds) {

			String thresholdName = threshold.getName();
			Double metricValue = getMetricNumericalValue(thresholdName);

			if (threshold.wasSurpassed(metricValue))
				surpassedThresholds.add(threshold);
		}
		return surpassedThresholds;
	}
}
