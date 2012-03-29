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

import br.usp.ime.choreos.vv.Item;
import br.usp.ime.choreos.vv.WSClient;
import br.usp.ime.choreos.vv.exceptions.FrameworkException;
import br.usp.ime.choreos.vv.exceptions.InvalidOperationNameException;

public class ProxyTest {
    private Proxy proxy;
    private Logger logger;

    @Before
    public void setUp() {
	logger = mock(Logger.class);
	proxy = new Proxy("roleName", logger);
    }

    @Test
    public void shouldGetEmptyListAtBegining() throws Exception {
	List<WSClient> expected = new ArrayList<WSClient>();
	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldAddOneWebService() throws Exception {
	WSClient wsMock = mock(WSClient.class);

	List<WSClient> expected = new ArrayList<WSClient>();
	expected.add(wsMock);

	proxy.addService(wsMock);

	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldAddManyWebServices() throws Exception {
	WSClient wsMock1 = mock(WSClient.class);
	WSClient wsMock2 = mock(WSClient.class);

	List<WSClient> expected = new ArrayList<WSClient>();
	expected.add(wsMock1);
	expected.add(wsMock2);

	proxy.addService(wsMock1);
	proxy.addService(wsMock2);

	assertEquals(expected, proxy.getWebServiceList());
    }

    @Test
    public void shouldMakeARequestToTheOnlyAssignedWebService() throws Exception {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	proxy.request("hello");

	verify(wsMock).request("hello");
    }

    @Test(expected = NoWebServiceException.class)
    public void shouldThrowNoWebServiceExceptionWhenInvokingProxyWithoutWS() throws Exception {
	proxy.request("hello");
    }

    @Test
    public void shouldMakeARequestWithOneParameterToTheOnlyAssignedWebService() throws Exception {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	proxy.request("hello", "world");

	verify(wsMock).request("hello", "world");
    }

    @Test
    public void shouldMakeARequestWithTwoParametersToTheOnlyAssignedWebService() throws Exception {
	WSClient wsMock = mock(WSClient.class);
	proxy.addService(wsMock);

	proxy.request("hello", "world", "!");

	verify(wsMock).request("hello", "world", "!");
    }

    @Test
    public void shouldReturnExpectedResponse() throws Exception {
	WSClient wsMock = mock(WSClient.class);
	Item itemMock = mock(Item.class);
	when(wsMock.request("hello")).thenReturn(itemMock);
	
	proxy.addService(wsMock);
	
	Item actual = proxy.request("hello");
	
	assertEquals(itemMock, actual);
    }

    @Test(expected = InvalidOperationNameException.class)
    public void shouldThrowInvalidOperationName() throws Exception {
	WSClient wsMock = mock(WSClient.class);
	when(wsMock.request("foo")).thenThrow(new InvalidOperationNameException());

	proxy.addService(wsMock);

	proxy.request("foo");
    }

    @Test(expected = NoWebServiceException.class)
    public void shouldThrowNoWebServiceExceptionWhenAllWSClientFails() throws Exception {
	WSClient wsClientMock1 = mock(WSClient.class);
	WSClient wsClientMock2 = mock(WSClient.class);
	when(wsClientMock1.request("foo")).thenThrow(new FrameworkException(new Exception()));
	when(wsClientMock2.request("foo")).thenThrow(new FrameworkException(new Exception()));

	proxy.addService(wsClientMock1);
	proxy.addService(wsClientMock2);

	proxy.request("foo");
    }

    @Test
    public void shouldUseASecondWebServiceWhenTheFirstFails() throws Exception {
	WSClient wsClientMock1 = mock(WSClient.class);
	WSClient wsClientMock2 = mock(WSClient.class);
	when(wsClientMock1.request("foo")).thenThrow(new FrameworkException(new Exception()));

	proxy.addService(wsClientMock1);
	proxy.addService(wsClientMock2);

	proxy.request("foo");
	verify(wsClientMock2).request("foo");
    }

    @Test
    public void shouldLogTheRequestInTheCorrectFormat() throws Exception {
	String roleName = "roleName";
	String receiverEndpoint = "receiverEndpoint";
	String operationName = "operationName";
	String content = "(parameter1; parameter2)";
	
	WSClient wsMock = mock(WSClient.class);
	when(wsMock.getWsdl()).thenReturn(receiverEndpoint);
	proxy.addService(wsMock);

	proxy.request(operationName, "parameter1", "parameter2");

	verify(logger).info(roleName + ", " + receiverEndpoint + ", " + operationName + ", " + content);
    }

}
