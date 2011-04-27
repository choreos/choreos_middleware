package br.usp.ime.ccsl.choreos.middleware.proxy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProxyTest {

	@Test
	public void shouldHaveManyWebServices(){
		
		Proxy proxy = new Proxy("roleName");
		
		List<WSClient>  expected = new ArrayList<WSClient>();
		WSClient ws = new WSClient("http://localhost:8081/hello?wsdl");
		
		expected.add(ws);
		proxy.addService(ws);
		
		assertEquals(expected, proxy.getWebServiceList());
	}

	
}
