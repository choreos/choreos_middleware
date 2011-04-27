package org.ow2.petals.bc.loggedsoap.listeners;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.jbi.messaging.DeliveryChannel;
import javax.jbi.messaging.MessageExchange;
import javax.jbi.messaging.MessageExchangeFactory;
import javax.jbi.messaging.MessagingException;
import javax.jbi.servicedesc.ServiceEndpoint;
import javax.xml.namespace.QName;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.petals.component.framework.AbstractComponent;
import org.ow2.petals.component.framework.api.message.Exchange;
import org.ow2.petals.component.framework.su.AbstractServiceUnitManager;


import org.jmock.Mockery;
import org.jmock.Expectations;

import org.ow2.petals.bc.loggedsoap.listeners.JuliToLog4jHandler;

public class JBIListenerTest {

    private static final class VoidComponent extends AbstractComponent {

	private class StubChannel implements DeliveryChannel {

	    @Override
	    public MessageExchange accept() throws MessagingException {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public MessageExchange accept(long timeoutMS) throws MessagingException, IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public void close() throws MessagingException {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public MessageExchangeFactory createExchangeFactory() {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public MessageExchangeFactory createExchangeFactory(QName arg0) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public MessageExchangeFactory createExchangeFactory(ServiceEndpoint endpoint) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public MessageExchangeFactory createExchangeFactoryForService(QName serviceName) {
		// TODO Auto-generated method stub
		return null;
	    }

	    @Override
	    public void send(MessageExchange exchange) throws MessagingException {
		

	    }

	    @Override
	    public boolean sendSync(MessageExchange exchange) throws MessagingException {
		// TODO Auto-generated method stub
		return false;
	    }

	    @Override
	    public boolean sendSync(MessageExchange exchange, long timeoutMS) throws MessagingException {
		// TODO Auto-generated method stub
		return false;
	    }
	}

	private static final Logger log = Logger.getLogger(JBIListener.class.getName());

	private StubChannel channel = new StubChannel();

	@Override
	public Logger getLogger() {
	    Handler handler = new JuliToLog4jHandler();

	    log.addHandler(handler);
	    return log;
	}

	@Override
	protected AbstractServiceUnitManager createServiceUnitManager() {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public DeliveryChannel getChannel() {

	    return channel;
	}
    }

    private static final String logName = "/tmp/log4jay.log";

    private static final Logger log = Logger.getLogger(JBIListenerTest.class.getName());

    private static AbstractComponent component;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	FileWriter fw = new FileWriter(logName);
	fw.close();

	component = new VoidComponent();
	component.getLogger();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testOnJBIMessageExchange() throws IOException, MessagingException {
	JBIListener jbi = new JBIListener();
	jbi.init(new VoidComponent());
	Mockery context = new Mockery();
	
	final Exchange message = context.mock(Exchange.class);
	final Exception bla = new Exception();
	
	context.checking(new Expectations() {{
	    allowing (message).getOperation();will(returnValue(null));
	    allowing (message).getOperationName();will(returnValue(null));
	    allowing (message).getEndpointName();will(returnValue(null));
	    allowing (message).getFault();will(returnValue(null));
	    allowing (message).isInOutPattern();will(returnValue(null));
	    allowing (message).getOutMessage().setContent(null);will(returnValue(null));
	    allowing (message).setError(bla); will(returnValue(null));
	    allowing (message).getFault().getContent();will(returnValue("MOCK-A"));
	}});
	
	jbi.onJBIMessage(message);

	String lastLine = lastLogLine();

	assertNotNull("Empty log file", lastLine);
	assertTrue("Log Generated Successfully", lastLine.endsWith("Log generated successfully"));

    }

    private synchronized String lastLogLine() throws FileNotFoundException, IOException {

	String lastLine = null, nextLine = null;

	FileReader fileReader = null;

	fileReader = new FileReader(logName);

	if (!fileReader.ready()) {
	    return null;
	}

	BufferedReader br = new BufferedReader(fileReader);

	while ((nextLine = br.readLine()) != null) {
	    lastLine = nextLine;
	}

	br.close();
	return lastLine;
    }

}
