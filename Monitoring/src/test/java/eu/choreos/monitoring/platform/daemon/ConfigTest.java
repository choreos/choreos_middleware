package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;

public class ConfigTest {

	private Config configNullFile;
	private Config configWithFile;
	private String thresholdListFileName;

	@Before
	public void setUp() throws Exception {
		configNullFile = null;
		thresholdListFileName = "myconfigfile.yml";
		configWithFile = null;
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void loadDefaultConfig() {
		
		configNullFile = Config.getInstance(null);
		//System.out.println(configNullFile.getThresholdConfig().toString());
		
		assertEquals("{default=[]," + 
					 " small=[Triggered: mem_free >= 512.0. Measured: 0.0]," + 
				     " extralarge=[Triggered: mem_free >= 4096.0. Measured: 0.0]," + 
					 " large=[Triggered: mem_free >= 2048.0. Measured: 0.0]," + 
				     " medium=[Triggered: mem_free >= 1024.0. Measured: 0.0]}", 
				     configNullFile.getThresholdsConfig().toString());
	}
	
	//@Test
	public void loadNotDefaultConfig() {

		configWithFile = Config.getInstance(thresholdListFileName);
		//System.out.println(configWithFile.getThresholdConfig().toString());
		
		assertEquals("{default=[Triggered: load_one <= 1.0. Measured: 0.0]," +
		             " small=[Triggered: mem_free >= 512.0. Measured: 0.0]," +
				     " extralarge=[Triggered: mem_free >= 4096.0. Measured: 0.0]," +
		             " large=[Triggered: mem_free >= 2048.0. Measured: 0.0]," +
				     " medium=[Triggered: mem_free >= 1024.0. Measured: 0.0]}", 
				     configWithFile.getThresholdsConfig().toString());
	}

}
