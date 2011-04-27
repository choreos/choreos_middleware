package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.middleware.exceptions.FrameworkException;
import br.usp.ime.ccsl.choreos.middleware.exceptions.WSDLException;

import static org.junit.Assert.*;
import  br.usp.ime.ccsl.choreos.middleware.proxy.genericHelloWorldService.*;

public class ProxyTest {

	private HelloWorld8081 webService = new HelloWorld8081("testing server");
	
	@Before
	public void setUp() {
		Endpoint endpoint = Endpoint.create(webService);

	    endpoint.publish("http://localhost:8081/hello");

	}
	
	@Test
	public void shouldHaveManyWebServices(){
		
		Proxy proxy = new Proxy("roleName");
		
		List<WSClient>  expected = new ArrayList<WSClient>();
		WSClient ws = null;
		try {
			ws = new WSClient("http://localhost:8081/hello?wsdl");
		} catch (WSDLException e) {
			e.printStackTrace();
		} catch (XmlException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (FrameworkException e) {
			e.printStackTrace();
		}
		
		expected.add(ws);
		proxy.addService(ws);
		
		assertEquals(expected, proxy.getWebServiceList());
	}

	
}