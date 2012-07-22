package eu.choreos.monitoring.platform.daemon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.choreos.monitoring.platform.utils.YamlParser;

public class Config {
	
	/* types of instances based on EC2 instances. 
	 * A default file is needed to start daemon*/
	private static final String[] files = {"small","medium","large","extralarge"};
	
	/* File name to file that contains threshold definitions */
	private static String thresholdConfigFile ;
	/* A map of threshold for this host: map<name_of_config, List<threshold>> */
	private Map<String, List<AbstractThreshold>> thresholds ;
	
	private Config ( String file ) {		
		
		this.thresholds = new HashMap<String, List<AbstractThreshold>>();
		thresholdConfigFile = file;

		this.setConfig();
	}
	
	public Map<String, List<AbstractThreshold>> getThresholdConfig () {
		return thresholds;
	}

	private void setConfig() {
			
		String fileName;
		
		/* Set the name of file that have threshold declarations. If file name
		 * is null or empty a default file is used. It is important that have a default
		 * file in resources directory */
		if(thresholdConfigFile == null || thresholdConfigFile.isEmpty())
			fileName = ClassLoader.getSystemResource("default.yml").getFile();	
		else
			fileName = ClassLoader.getSystemResource(thresholdConfigFile).getFile();
		
		try {
			/* Default thresholds */
			List<AbstractThreshold> defaultThresholds = new ArrayList<AbstractThreshold>();
			
			defaultThresholds = YamlParser.getThresholdsFromFile(fileName);
			this.thresholds.put("default", defaultThresholds);
			
			/* Per instance type thresholds */
			for (String it : files) {
				System.out.println(ClassLoader.getSystemResource(it + ".yml").getFile());
				
				List<AbstractThreshold> thresholds = YamlParser.getThresholdsFromFile(
						ClassLoader.getSystemResource(it + ".yml").getFile()) ;
				
				if(thresholds.isEmpty() || thresholds == null) continue;
								
				this.thresholds.put(it, thresholds);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private static volatile  Config instance = null;

	public static Config getInstance(String thresholdListFileName) {
		if ( instance == null || thresholdConfigFile != thresholdListFileName) {
			thresholdConfigFile = thresholdListFileName;
			return new Config(thresholdConfigFile);
		}
		return instance;
	}
}
