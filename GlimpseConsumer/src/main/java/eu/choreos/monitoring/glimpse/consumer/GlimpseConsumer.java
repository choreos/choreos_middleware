package eu.choreos.monitoring.glimpse.consumer;

import it.cnr.isti.labse.glimpse.consumer.GlimpseAbstractConsumer;
import it.cnr.isti.labse.glimpse.xml.complexEventException.ComplexEventException;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;

import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

public class GlimpseConsumer extends GlimpseAbstractConsumer {

	public GlimpseConsumer(Properties settings, String plainTextRule) {
		super(settings, plainTextRule);
	}

	
	@Override
	public void messageReceived(Message arg0) throws JMSException {
			ObjectMessage responseFromMonitoring = (ObjectMessage) arg0;
			if (responseFromMonitoring.getObject() instanceof ComplexEventException) {
				handleExceptionEvent(responseFromMonitoring);
			} else {
				handleEvent(responseFromMonitoring);
			}
	} 


	private void handleEvent(ObjectMessage responseFromMonitoring)
			throws JMSException {
		ComplexEventResponse resp = (ComplexEventResponse) responseFromMonitoring
				.getObject();
		System.out
				.println("Response value: " + resp.getResponseValue());
	}


	private void handleExceptionEvent(ObjectMessage responseFromMonitoring)
			throws JMSException {
		ComplexEventException exceptionReceived = (ComplexEventException) responseFromMonitoring
				.getObject();
		System.out.println("Exception ClassName: "
				+ exceptionReceived.getClassName() + "\n");
	}

}
