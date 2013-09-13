package org.ow2.choreos.integration.chors.reconfiguration;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponse;
import it.cnr.isti.labse.glimpse.xml.complexEventResponse.ComplexEventResponseListDocument;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.reconfiguration.ComplexEventHandler;
import org.ow2.choreos.chors.reconfiguration.ComplexEventResponseHandler;
import org.ow2.choreos.chors.reconfiguration.HandlingEvent;

public class ComplexEventResponseHandlerTest {

    ComplexEventHandler mockedResponseHandler;
    ComplexEventResponseHandler responsehandlerPreparer;

    @Before
    public void setUp() {
	mockedResponseHandler = mock(ComplexEventHandler.class);
	responsehandlerPreparer = new ComplexEventResponseHandler(mockedResponseHandler);
	doNothing().when(mockedResponseHandler).handleEvent(any(String.class), any(HandlingEvent.class));
    }

    @Test
    public void testHandlingEventMap() throws JMSException {
	ComplexEventResponseListDocument rsp;
	rsp = ComplexEventResponseListDocument.Factory.newInstance();
	ComplexEventResponse response1 = rsp.addNewComplexEventResponseList();
	response1.setRuleName("rule");
	response1.setResponseKey("key");
	response1.setResponseValue("value : 10.10.10.10");

	ObjectMessage message1 = new ActiveMQObjectMessage();
	message1.setObject((Serializable) response1);

	ComplexEventResponse response1copy = rsp.addNewComplexEventResponseList();
	response1copy.setRuleName("rule");
	response1copy.setResponseKey("key");
	response1copy.setResponseValue("value : 10.10.10.10");
	ObjectMessage message1copy = new ActiveMQObjectMessage();
	message1copy.setObject((Serializable) response1copy);

	ComplexEventResponse response2 = rsp.addNewComplexEventResponseList();
	response2.setRuleName("rule2");
	response2.setResponseKey("key2");
	response2.setResponseValue("value2 : 10.10.10.2");
	ObjectMessage message2 = new ActiveMQObjectMessage();
	message2.setObject((Serializable) response2);

	responsehandlerPreparer.handle(message1);
	responsehandlerPreparer.handle(message1copy);
	responsehandlerPreparer.handle(message2);

	verify(mockedResponseHandler, times(3)).handleEvent(any(String.class), any(HandlingEvent.class));
    }
}
