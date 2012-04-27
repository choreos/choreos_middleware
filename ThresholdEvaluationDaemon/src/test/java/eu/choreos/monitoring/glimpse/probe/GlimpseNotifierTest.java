package eu.choreos.monitoring.glimpse.probe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.daemon.Threshold;
import eu.choreos.monitoring.daemon.ThresholdEvalDaemon;
import eu.choreos.monitoring.glimpse.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.glimpse.notifier.GlimpseNotifier;
import eu.choreos.monitoring.utils.ShellHandler;

public class GlimpseNotifierTest {
	
	private GlimpseNotifier glimpseNotifier;
	private ThresholdEvalDaemon daemon;
	private String hostname = ShellHandler.runLocalCommand("/bin/bash "+ "/tmp/hostname.sh").replace("\n", "");
	private GlimpseBaseEventImpl<String> message;
	private GlimpseMessageHandler msgHandler;

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
		
		message = new GlimpseBaseEventImpl<String>(
				"thresholdAlarm", "connector1", "connInstance1",
				"connExecution1", 1, 2, System.currentTimeMillis(), "NS1",
				false);

		msgHandler = mock(GlimpseMessageHandler.class);
		daemon = mock(ThresholdEvalDaemon.class);

		List<Threshold> surpassedThresholds = new ArrayList<Threshold>();
		
		surpassedThresholds.add(new Threshold("testMAX", Threshold.MAX, 3.0));
		surpassedThresholds.add(new Threshold("testMIN", Threshold.MIN, 3.0));
		
		when(daemon.evaluateThresholds()).thenReturn(surpassedThresholds);
		
		glimpseNotifier = new GlimpseNotifier(createProbeSettingsPropertiesObject, "tcp://dsbchoreos.petalslink.org", 61616, msgHandler, daemon);

	}

	@Test
	public void testGetScriptCommand() {
		assertEquals("/bin/bash /tmp/hostname.sh", glimpseNotifier.getScriptCommand());
	}
	
	@Test
	public void shouldGetHostname() throws Exception {
		assertEquals(hostname, glimpseNotifier.getHostName());
	}

	@Test
	public void shouldSendAliveMessage(){
		glimpseNotifier.sendAliveMessage(message);
		verify(msgHandler, times(1)).sendMessage(any(GlimpseBaseEventImpl.class));
	}
	
	@Test
	public void shouldCheckIfThereAreSurpassedThresholds(){
		assertTrue(glimpseNotifier.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholds(){
		when(daemon.evaluateThresholds()).thenReturn(new ArrayList<Threshold>());
		assertFalse(glimpseNotifier.thereAreSurpassedThresholds());
	}
	
	@Test
	public void shouldSendAllThresholdsMessage(){
		glimpseNotifier.sendAllSurpassedThresholdMessages(message);
		verify(msgHandler, times(2)).sendMessage(any(GlimpseBaseEventImpl.class));
	}
}
