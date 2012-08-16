package eu.choreos.monitoring.platform.glimpse.consumer;

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.utils.Manager;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

// TODO: try to user java.util.Observer. The Source of events needs to
//       extends Observable abstract class, but already extends
//       GlimpseAbstractConsumer
public class GlimpseConsumer extends GlimpseAbstractConsumer {
	
	// listener for message received event
	@SuppressWarnings("rawtypes")
	private List _listeners = new ArrayList();
	
	public GlimpseConsumer(Properties settings, String plainTextRule) {
		super(settings, plainTextRule);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void addEventListener( IListener l ) {
		_listeners.add(l);
	}
	
	public synchronized void removeEventListener( IListener l ) {
		_listeners.remove(l);
	}
	
	private synchronized void triggerMessageEvent() {
		GlimpseMessageEvent m = new GlimpseMessageEvent(this);
		
		@SuppressWarnings("rawtypes")
		Iterator i = _listeners.iterator();
		
		while(i.hasNext()) {
			((IListener) i.next()).handleEvent(m);
		}
	}

	@Override
	public void messageReceived(Message message) throws JMSException {
		try {
			ObjectMessage responseFromMonitoring = (ObjectMessage) message;	
			if (responseFromMonitoring.getObject() instanceof ComplexEventException) {
				ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring.getObject();
				System.out.println("Exception ClassName: " + exceptionReceived.getClassName() + "\n");
				// TODO: verify exception cause
			}
			else {
				ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring.getObject();
				System.out.println("Response value: " + resp.getResponseValue());

				/*
				 * sends event when a message is received
				 */
				this.triggerMessageEvent();
			}
		}
		catch(ClassCastException asd) {
		}
	}

	public static GlimpseConsumer getConsumer() {
		return new GlimpseConsumer(
				Manager.createConsumerSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						"tcp://dsbchoreos.petalslink.org:61616",
					//	"tcp://192.168.122.69:61616",
						"system",
						"manager",
						"TopicCF",
						"jms.serviceTopic",
						false,
						"consumerTest"
				),
				Manager.ReadTextFromFile(
						//System.getProperty("user.dir") + "/bin/it/cnr/isti/labse/glimpse/example/exampleRule.xml"
						ClassLoader.getSystemResource("default.xml").getFile()
				)
		);
	}
}