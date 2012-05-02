package eu.choreos.monitoring.platform.notifier;

import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

public class GlimpseMessageHandler extends GlimpseAbstractProbe {

	public GlimpseMessageHandler(Properties settings) {
		super(settings);
	}

	public void sendMessage(GlimpseBaseEvent<String> event) {
		try {
			this.sendEventMessage(event, false);
		} catch (JMSException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(GlimpseBaseEvent<?> message, boolean debug) {

	}

}
