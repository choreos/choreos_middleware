package eu.choreos.monitoring.platform.daemon.notifier;

import java.util.Properties;

import javax.jms.JMSException;
import javax.naming.NamingException;

import eu.choreos.monitoring.platform.utils.HostnameHandler;
import eu.choreos.platform.utils.CommandRuntimeException;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.probe.GlimpseAbstractProbe;

public class GlimpseMessageHandler extends GlimpseAbstractProbe {

	public GlimpseMessageHandler(Properties settings) {
		super(settings);
	}

	public GlimpseBaseEvent<String> sendMessage(GlimpseBaseEvent<String> event) throws MessageHandlingFault {
		String hostName;
		try {
			hostName = HostnameHandler.getHostName();
			event.setNetworkedSystemSource(hostName);
			this.sendEventMessage(event, false);

		} catch (CommandRuntimeException e1) {
			e1.handleException();
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
