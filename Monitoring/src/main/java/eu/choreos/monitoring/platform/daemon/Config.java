package eu.choreos.monitoring.platform.daemon;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.choreos.monitoring.platform.utils.YamlParser;

public class Config {
	
	private static final String THRESHOLD_SPECS_FOLDER = "threshold_specs/";

	/* types of instances based on EC2 instances. 
	 * A default file is needed to start daemon
	 * */
	private static final String[] instanceTypedSpecsFiles = {"small","medium","large","extralarge"};

	/* 
	 * File name to file that contains threshold definitions ]
	 * */
	private static String thresholdConfigFile = null;

	/* 
	 * A map of threshold for this host: map<name_of_config, List<threshold>> 
	 * */
	private Map<String, List<AbstractThreshold>> thresholds ;

	private Config ( String file ) {		

		this.thresholds = new HashMap<String, List<AbstractThreshold>>();
		thresholdConfigFile = file;

		this.setConfig();
	}

	public Map<String, List<AbstractThreshold>> getThresholdsConfig () {
		return thresholds;
	}

	private void setConfig() {

        InputStream data;

		if(thresholdConfigFile != null)
            data = this.getClass().getClassLoader().getResourceAsStream(THRESHOLD_SPECS_FOLDER+thresholdConfigFile);
        else
            data = this.getClass().getClassLoader().getResourceAsStream(THRESHOLD_SPECS_FOLDER+"default.yml");

        List<AbstractThreshold> defaultThresholds = new ArrayList<AbstractThreshold>();

        defaultThresholds = YamlParser.getThresholdsFromStream(data);
        this.thresholds.put("default", defaultThresholds);

        for (String it : instanceTypedSpecsFiles) {

            List<AbstractThreshold> thresholds = YamlParser.getThresholdsFromStream(
                    this.getClass().getClassLoader().getResourceAsStream(THRESHOLD_SPECS_FOLDER+it + ".yml")) ;

            if(thresholds.isEmpty() || thresholds == null) 
            	continue;

            this.thresholds.put(it, thresholds);
        }
	}

	private static volatile Config instance = null;

	public static Config getInstance(String thresholdListFileName) {

		if(thresholdConfigFile == null) 
			thresholdConfigFile = thresholdListFileName;
		
		if(thresholdListFileName != null)
			if(thresholdConfigFile.compareTo(thresholdListFileName) != 0) 
				thresholdConfigFile = thresholdListFileName;

		if (instance == null) 
			instance = new Config(thresholdConfigFile);
		else
			instance.setConfig();

		return instance;
	}
}
