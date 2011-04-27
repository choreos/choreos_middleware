package br.usp.ime.ccsl.choreos.middleware.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.ccsl.choreos.middleware.exceptions.InvalidOperationName;
import br.usp.ime.ccsl.choreos.middleware.proxy.genericHelloWorldService.HelloWorld8081;

public class ProxyTest {
    Proxy proxy;

    @Before
    public void setUp() {
	proxy = new Proxy();
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
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	try {
	    verify(wsMock).request("hello");
	} catch (InvalidOperationName e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (FrameworkException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    @Test(expected = NoWebServiceException.class)
    public void shouldThrowNoWebServiceExceptionWhenInvokingProxyWithoutWS() throws NoWebServiceException {
	try {
	    proxy.request("hello");
	} catch (InvalidOperationName e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void shouldAddOneRealWebService() {
	HelloWorld8081.publishService(8081);
	System.out.println("Serviço disponibilizado na porta 8081");

	List<WSClient> expected = new ArrayList<WSClient>();

	try {
	    WSClient ws;
	    ws = new WSClient("http://localhost:8081/hello?wsdl");

	    expected.add(ws);
	    proxy.addService(ws);

	    assertEquals(expected, proxy.getWebServiceList());
	} catch (Exception e) {
	    // e.printStackTrace();
	    fail(e.getMessage());
	}
    }
}
