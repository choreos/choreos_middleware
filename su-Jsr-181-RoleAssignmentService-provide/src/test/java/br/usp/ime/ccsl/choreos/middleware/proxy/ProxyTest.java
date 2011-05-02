package br.usp.ime.ccsl.choreos.middleware.proxy;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.ccsl.choreos.middleware.exceptions.InvalidOperationName;

public class ProxyTest {
    private Proxy proxy;
    private Logger logger;

    @Before
    public void setUp() {
	logger = Logger.getLogger(Proxy.class);
	proxy = new Proxy(logger);
    }

    @Test
    public void shouldGetEmptyListAtBegining() {
	List<WSClient> expected = new ArrayList<WSClient>();
	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldAddOneWebService() {
	List<WSClient> expected = new ArrayList<WSClient>();
	WSClient wsMock = mock(WSClient.class);

	expected.add(wsMock);
	proxy.addService(wsMock);

	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldAddManyWebServices() {

	List<WSClient> expected = new ArrayList<WSClient>();
	WSClient wsMock1 = mock(WSClient.class);
	WSClient wsMock2 = mock(WSClient.class);
	WSClient wsMock3 = mock(WSClient.class);

	expected.add(wsMock1);
	expected.add(wsMock2);
	expected.add(wsMock3);
	proxy.addService(wsMock1);
	proxy.addService(wsMock2);
	proxy.addService(wsMock3);

	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldMakeARequestToTheOnlyAssignedWebService() {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	try {
	    proxy.request("hello");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}

	try {
	    verify(wsMock).request("hello");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (FrameworkException e) {
	    e.printStackTrace();
	}
    }

    @Test(expected = NoWebServiceException.class)
    public void shouldThrowNoWebServiceExceptionWhenInvokingProxyWithoutWS()
    throws NoWebServiceException {
	try {
	    proxy.request("hello");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldMakeARequestWithOneParameterToTheOnlyAssignedWebService() {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	try {
	    proxy.request("hello", "world");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}

	try {
	    verify(wsMock).request("hello", "world");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (FrameworkException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldMakeARequestWithTwoParametersToTheOnlyAssignedWebService() {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	try {
	    proxy.request("hello", "world", "!");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}

	try {
	    verify(wsMock).request("hello", "world", "!");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (FrameworkException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldReturnExpectedResponse() {
	final String expected = "Hello test!";
	WSClient wsMock = mock(WSClient.class);
	try {
	    when(wsMock.request("hello")).thenReturn(expected);
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (FrameworkException e) {
	    e.printStackTrace();
	}

	proxy.addService(wsMock);
	
	try {
	    String actual = proxy.request("hello");
	    assertEquals(expected, actual);
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}
    }

    @Test(expected = InvalidOperationName.class)
    public void shouldThrowInvalidOperationName() throws InvalidOperationName {
	WSClient wsMock = mock(WSClient.class);
	try {
	    when(wsMock.request("foo")).thenThrow(new InvalidOperationName());
	} catch (FrameworkException e) {
	    e.printStackTrace();
	}
	
	proxy.addService(wsMock);
	
	try {
	    proxy.request("foo");
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}
    }
    
    @Test
    public void shouldLogOperationNameOfTheRequest() {
	TestingAppender testingAppender = new TestingAppender();
	logger.addAppender(testingAppender);
	
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);
	
	String operation = "foo";
	
	try {
	    proxy.request(operation);
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	} catch (NoWebServiceException e) {
	    e.printStackTrace();
	}
	
	List<String> messages = TestingAppender.getMessages();
	assertEquals("Request received: " + operation + "; no parameters.", messages.get(messages.size() - 1));
    }

}
