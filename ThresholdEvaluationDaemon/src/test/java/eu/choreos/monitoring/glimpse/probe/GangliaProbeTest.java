package eu.choreos.monitoring.glimpse.probe;

import static org.junit.Assert.assertEquals;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.daemon.ThresholdEvalDaemon;
import eu.choreos.monitoring.glimpse.notifier.GlimpseNotifier;
import eu.choreos.monitoring.utils.ShellHandler;

public class GangliaProbeTest {
	
	GlimpseNotifier glimpseNotifier;
	ThresholdEvalDaemon daemon;
	String hostname = ShellHandler.runLocalCommand("/bin/bash "+ "/tmp/hostname.sh").replace("\n", "");

	@Before
	public void setUp() throws Exception {
		String javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";
		Properties createProbeSettingsPropertiesObject = Manager.createProbeSettingsPropertiesObject(
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
				javaNamingProviderUrl, 
				"system", 
				"manager", 
				"GangliaFactory", 
				"jms.probeTopic", 
				true,
				"probeName", 
				"probeTopic");
		
		glimpseNotifier = new GlimpseNotifier(createProbeSettingsPropertiesObject);

	}

	@Test
	public void testGetScriptCommand() {
		assertEquals("/bin/bash /tmp/hostname.sh", glimpseNotifier.getScriptCommand());
	}
	
	@Test
	public void shouldGetHostname() throws Exception {
		assertEquals(hostname, glimpseNotifier.getHostName());
	}

}
