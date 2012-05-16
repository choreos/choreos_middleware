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

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import eu.choreos.monitoring.platform.daemon.datatypes.Gmetric;
import eu.choreos.monitoring.platform.daemon.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.daemon.notifier.MessageHandlingFault;
import eu.choreos.monitoring.platform.exception.GangliaException;
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

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendAliveMessage() throws MessageHandlingFault {
		daemon.sendHeartbeat(message);
		verify(msgHandler, times(1)).sendMessage(
				any(GlimpseBaseEventImpl.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendOneHeartBeatMessage() throws GangliaException, MessageHandlingFault {
		prepareSendMessageToCheckAlive();

		for(int i = 0; i < 20; i++) {
			 daemon.evaluateThresholdsSendMessagesAndSleep(message, 0);
		}
		verify(msgHandler, times(1)).sendMessage(any(GlimpseBaseEventImpl.class));
	}
	

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendTwoHeartBeatMessage() throws GangliaException, MessageHandlingFault {
		prepareSendMessageToCheckAlive();
		
		for(int i = 0; i < 40; i++)
			daemon.evaluateThresholdsSendMessagesAndSleep(message, 0);
		verify(msgHandler, times(2)).sendMessage(any(GlimpseBaseEventImpl.class));
	}

	@Test
	public void shouldCheckIfThereAreSurpassedThresholds() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreNoneSurpassedThresholds() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MIN, 1.0));
		assertEquals(false, daemon.thereAreSurpassedThresholds());
	}

	@Test
	public void shouldCheckIfThereAreSurpassedThresholdsAmongMany() throws GangliaException {
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1.0));
		daemon.addThreshold(new Threshold("load_five", Threshold.MIN, 1.0));
		assertTrue(daemon.thereAreSurpassedThresholds());
	}
	
	@Test
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

	@SuppressWarnings("unchecked")
	@Test
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void prepareSendMessageToCheckAlive() throws MessageHandlingFault {
		stub(msgHandler.sendMessage(any(GlimpseBaseEventImpl.class))).toAnswer(new Answer() {
			public Object answer(InvocationOnMock invocation) {
				Object[] args = invocation.getArguments();
				assertEquals(((GlimpseBaseEvent<String>)args[0]).getData(),"Alive");
				return args[0];	
			}
		});
	}
}
