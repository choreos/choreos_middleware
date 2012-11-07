package eu.choreos.monitoring.platform.daemon;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventChoreos;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.exception.MessageHandlingFault;

public class ThresholdEvalDaemonTest {

	private ThresholdEvalDaemon daemon;
	private GlimpseBaseEventChoreos<String> message;
	private GlimpseMessageHandler msgHandler;
	private String javaNamingProviderUrl;
	
	
	private ThresholdManager thresholdManager;

	@Before
	public void setUp() throws Exception {
		thresholdManager = mock(ThresholdManager.class);
		when(thresholdManager.thereAreSurpassedThresholds()).thenReturn(true);
		
		List<AbstractThreshold> host1ThresholdsList = new ArrayList<AbstractThreshold>();
		host1ThresholdsList.add(new SingleThreshold("load_one", SingleThreshold.MAX, 3));
		host1ThresholdsList.add(new SingleThreshold("load_five", SingleThreshold.MAX, 3));
		
		Map<String, List<AbstractThreshold>> thresholdsMap = new HashMap<String, List<AbstractThreshold>>();
		thresholdsMap.put("host1", host1ThresholdsList);
		
		when(thresholdManager.getSurpassedThresholds()).thenReturn(thresholdsMap);
		
		
		javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";
		message = getDefaultMessage();
		msgHandler = mock(GlimpseMessageHandler.class);
		daemon = new ThresholdEvalDaemon(getProperties(), "localhost", 8649,
				msgHandler, thresholdManager);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void shouldSendAllThresholdsMessage() throws GangliaException, MessageHandlingFault {
		daemon.addThreshold("default", new SingleThreshold("load_one", SingleThreshold.MAX, 1.0));
		daemon.addThreshold("default", new SingleThreshold("load_five", SingleThreshold.MIN, 1.0));
		
		daemon.evaluateThresholdsSendMessagesAndSleep(message, 0);
		
		verify(msgHandler, times(2)).sendMessage(
				any(GlimpseBaseEventChoreos.class));
	}

	
	private GlimpseBaseEventChoreos<String> getDefaultMessage() {
		return new GlimpseBaseEventChoreos<String>(
				"thresholdAlarm", 
				System.currentTimeMillis(), 
				"", 
				false, 
				"chor", 
				"service", 
				"10.10.10.101");
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
