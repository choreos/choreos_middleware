package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.stub;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.daemon.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.daemon.notifier.MessageHandlingFault;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class ThresholdEvalDaemonTest {

	private ThresholdEvalDaemon daemon;
	private GlimpseBaseEventImpl<String> message;
	private GlimpseMessageHandler msgHandler;
	private String javaNamingProviderUrl;
	private GmondDataReader dataReader;
	private Host aHost;

	@Before
	public void setUp() throws Exception {
		javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";

		message = getDefaultMessage();

		msgHandler = mock(GlimpseMessageHandler.class);
		dataReader = mock(GmondDataReader.class);
		

		Map<String, Metric> metricsMap = new HashMap<String, Metric>();
		
		metricsMap.put("load_one", new Metric("load_one", "2"));
		metricsMap.put("load_five", new Metric("load_five", "0.8"));
		metricsMap.put("ram", new Metric("ram", "512"));
		
		aHost = new Host("cluster", "hostname", "10.0.0.01", metricsMap);
		
		ArrayList<Host> hostList = new ArrayList<Host>();
		hostList.add(aHost);
		
		when(dataReader.getUpToDateHostsInfo()).thenReturn(hostList);
		
		daemon = new ThresholdEvalDaemon(getProperties(), "localhost", 8649,
				msgHandler, dataReader);

	}

//	@Test
	public void shouldCheckIfThereAreSurpassedThresholds() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}
	

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholds() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MIN, 1.0));
		assertEquals(false, daemon.thereAreSurpassedThresholds());
	}

//	@Test
	public void shouldCheckIfThereAreSurpassedThresholdsAmongMany() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MIN, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}
	
//	@Test
	public void shouldCheckIfThereAreSurpassedThresholdsAmongManyNotAllTrue() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MAX, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholdsAmongMany() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MIN, 1.0));
		daemon.addThreshold(new Threshold("ram", Threshold.MIN, 500.0));
		assertFalse(daemon.thereAreSurpassedThresholds());
	}

//	@SuppressWarnings("unchecked")
//	@Test
	public void shouldSendAllThresholdsMessage() throws GangliaException, MessageHandlingFault {
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
