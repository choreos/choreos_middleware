package eu.choreos.monitoring.platform.daemon.notifier;

import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import eu.choreos.monitoring.platform.utils.HostnameHandler;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

public class GlimpseMessageHandler extends GlimpseAbstractProbe {

	public GlimpseMessageHandler(Properties settings) {
		super(settings);
	}

	public GlimpseBaseEvent<String> sendMessage(GlimpseBaseEvent<String> event) {
		event.setNetworkedSystemSource(HostnameHandler.getHostName());
		try {
			this.sendEventMessage(event, false);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return event;
	}

	@Override
	public void sendMessage(GlimpseBaseEvent<?> message, boolean debug) {

	}

}
