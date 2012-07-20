package eu.choreos.monitoring.platform.daemon;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ConfigTest {

	private Config configNullFile;
	private Config configWithFile;

	@Before
	public void setUp() throws Exception {
		configNullFile = Config.getInstance(null);
		configWithFile = Config.getInstance("myconfigfile.yml");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void loadDefaultConfig() {
		assertEquals("{default=[]," + 
					 " small=[Triggered: mem_free >= 512.0. Measured: 0.0]," + 
				     " extralarge=[Triggered: mem_free >= 4096.0. Measured: 0.0]," + 
					 " large=[Triggered: mem_free >= 2048.0. Measured: 0.0]," + 
				     " medium=[Triggered: mem_free >= 1024.0. Measured: 0.0]}", 
				     configNullFile.getThresholdConfig().toString());
	}
	
	@Test
	public void loadNotDefaultConfig() {
		
		System.out.println(configWithFile.getThresholdConfig().toString());
		
		assertEquals("{default=[Triggered: load_one <= 1.0. Measured: 0.0]," +
		             " small=[Triggered: mem_free >= 512.0. Measured: 0.0]," +
				     " extralarge=[Triggered: mem_free >= 4096.0. Measured: 0.0]," +
		             " large=[Triggered: mem_free >= 2048.0. Measured: 0.0]," +
				     " medium=[Triggered: mem_free >= 1024.0. Measured: 0.0]}", 
				     configWithFile.getThresholdConfig().toString());
	}

}
