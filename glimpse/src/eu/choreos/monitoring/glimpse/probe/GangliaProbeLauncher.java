package eu.choreos.monitoring.glimpse.probe;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.util.Properties;

public class GangliaProbeLauncher {
	public static void main(String[] args) {
		
		Properties createProbeSettingsPropertiesObject = Manager.createProbeSettingsPropertiesObject(
				"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
				"tcp://localhost:61616", 
				"system", 
				"manager", 
				"GangliaFactory", 
				"jms.probeTopic", 
				true,
				"probeName", 
				"probeTopic");
		
		GangliaProbe gangliaProbe = new GangliaProbe(createProbeSettingsPropertiesObject);
		
		GlimpseBaseEvent<String> message = new GlimpseBaseEventImpl<String>("thresholdAlarm",
				"connector1","connInstance1",
				"connExecution1",
				1,
				2,
				System.currentTimeMillis(),
				"NS1",
				false);
		
		for(;;){
			gangliaProbe.notifyMessages(gangliaProbe, message);
		}
		
	}
}
