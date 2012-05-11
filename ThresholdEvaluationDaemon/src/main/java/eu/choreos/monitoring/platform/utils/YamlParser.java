package eu.choreos.monitoring.platform.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.*;
import org.yaml.snakeyaml.constructor.Constructor;

import eu.choreos.monitoring.platform.daemon.Threshold;
import eu.choreos.monitoring.platform.daemon.datatypes.ThresholdSpec;

public class YamlParser {

	private static List<Threshold> getThresholdsFromString(String content) {

		Constructor skeleton = new Constructor(ThresholdSpec.class);
		Yaml yaml = new Yaml(skeleton);

		List<Threshold> thresholds = new ArrayList<Threshold>();

		for (Object data : yaml.loadAll(content)) {
			Threshold readThreshold = ((ThresholdSpec) data).toThreshold();
			thresholds.add(readThreshold);
		}

		return thresholds;

	}

	public static List<Threshold> getThresholdsFromFile(String fileName)
			throws IOException {

		File file = new File(fileName);
		String fileContents = FileUtils.readFileToString(file);

		List<Threshold> returnValue = getThresholdsFromString(fileContents);
		
		return returnValue;
	}

}
