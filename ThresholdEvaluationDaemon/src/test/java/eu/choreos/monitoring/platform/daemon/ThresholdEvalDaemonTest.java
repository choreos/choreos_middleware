package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.Threshold;
import eu.choreos.monitoring.platform.daemon.ThresholdEvalDaemon;
import eu.choreos.monitoring.platform.datatypes.Gmetric;
import eu.choreos.monitoring.platform.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class ThresholdEvalDaemonTest {

	private ThresholdEvalDaemon daemon;
	private GlimpseBaseEventImpl<String> message;
	private GlimpseMessageHandler msgHandler;
	private GmondDataReader dataReader;
	private String javaNamingProviderUrl;

	@Before
	public void setUp() throws Exception {
		javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";

		message = getDefaultMessage();

		msgHandler = mock(GlimpseMessageHandler.class);
		dataReader = mock(GmondDataReader.class);

		Map<String, Gmetric> metricsMap = new HashMap<String, Gmetric>();
		
		metricsMap.put("load_one", new Gmetric("load_one", "2"));
		metricsMap.put("load_five", new Gmetric("load_five", "0.8"));
		metricsMap.put("ram", new Gmetric("ram", "512"));
		
		when(dataReader.getAllMetrics()).thenReturn(metricsMap);
		
		daemon = new ThresholdEvalDaemon(getProperties(), "localhost", 8649,
				msgHandler, dataReader);

	}

	@Test
	public void shouldSendAliveMessage() {
		daemon.sendHeartbeat(message);
		verify(msgHandler, times(1)).sendMessage(
				any(GlimpseBaseEventImpl.class));
	}

	@Test
	public void shouldCheckIfThereAreSurpassedThresholds() {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholds() {
		System.out.println("PAU VV");
		daemon.addThreshold(new Threshold("load_one", Threshold.MIN, 1.0));
		assertEquals(false, daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreSurpassedThresholdsAmongMany() {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MIN, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}
	
	@Test
	public void shouldCheckIfThereAreSurpassedThresholdsAmongManyNotAllTrue() {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MAX, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholdsAmongMany() {
		daemon.addThreshold(new Threshold("load_one", Threshold.MIN, 1.0));
		daemon.addThreshold(new Threshold("ram", Threshold.MIN, 500.0));
		assertFalse(daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldSendAllThresholdsMessage() {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MIN, 1.0));
		
		daemon.evaluateThresholdsSendMessagesAndSleep(message, 0);
		
		verify(msgHandler, times(2)).sendMessage(
				any(GlimpseBaseEventImpl.class));
	}

	private GlimpseBaseEventImpl<String> getDefaultMessage() {
		return new GlimpseBaseEventImpl<String>("thresholdAlarm", "connector1",
				"connInstance1", "connExecution1", 1, 2,
				System.currentTimeMillis(), "NS1", false);
	}
	
	private Properties getProperties() {
		Properties createProbeSettingsPropertiesObject = Manager
				.createProbeSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						javaNamingProviderUrl, "system", "manager",
						"GangliaFactory", "jms.probeTopic", true, "probeName",
						"probeTopic");
		return createProbeSettingsPropertiesObject;
	}
}
