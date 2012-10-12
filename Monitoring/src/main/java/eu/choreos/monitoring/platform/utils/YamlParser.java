package eu.choreos.monitoring.platform.utils;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;

import org.apache.commons.io.FileUtils;
import org.yaml.snakeyaml.Yaml;

import eu.choreos.monitoring.platform.daemon.AbstractThreshold;
import eu.choreos.monitoring.platform.daemon.datatypes.AbstractThresholdSpec;
import eu.choreos.monitoring.platform.daemon.datatypes.DoubleThresholdSpec;
import eu.choreos.monitoring.platform.daemon.datatypes.SingleThresholdSpec;

public class YamlParser {

	private static List<AbstractThreshold> getThresholdsFromString(String content) {

		Yaml yaml = new Yaml();

		List<AbstractThreshold> thresholds = new ArrayList<AbstractThreshold>();

		for (Object data : yaml.loadAll(content)) {
			AbstractThresholdSpec readThreshold = null;
			
			if(data instanceof SingleThresholdSpec) {
				readThreshold = (SingleThresholdSpec) data;
			} else
				if(data instanceof DoubleThresholdSpec) {
					readThreshold = (DoubleThresholdSpec) data;
				}
			
			if(readThreshold != null)
				thresholds.add(readThreshold.toThreshold());
		}

		return thresholds;

	}

	public static List<AbstractThreshold> getThresholdsFromFile(String fileName)
			throws IOException {

		File file = new File(fileName);
		String fileContents = FileUtils.readFileToString(file);

		List<AbstractThreshold> returnValue = getThresholdsFromString(fileContents);

		return returnValue;
	}

	public static List<AbstractThreshold> getThresholdsFromStream(InputStream data) {

        String s;
            try {
            s = new java.util.Scanner(data).useDelimiter("\\A").next();
            } catch (java.util.NoSuchElementException e) {
                        s = "";
            }

		return getThresholdsFromString(s);
	}

}
