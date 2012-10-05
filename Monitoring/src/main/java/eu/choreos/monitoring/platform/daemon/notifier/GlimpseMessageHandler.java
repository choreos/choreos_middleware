package eu.choreos.monitoring.platform.daemon.notifier;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import eu.choreos.monitoring.platform.exception.MessageHandlingFault;

public class GlimpseMessageHandler extends GlimpseAbstractProbe {

	public GlimpseMessageHandler(Properties settings) {
		super(settings);
	}

	public GlimpseBaseEvent<String> sendMessage(GlimpseBaseEvent<String> event) throws MessageHandlingFault {
		try {
			this.sendEventMessage(event, false);
		} catch (JMSException e) {
			throw new MessageHandlingFault("JMS Exception");
		} catch (NamingException e) {
			throw new MessageHandlingFault("Naming Exception");
		}
		return event;
	}

	@Override
	public void sendMessage(GlimpseBaseEvent<?> message, boolean debug) {

	}

}
