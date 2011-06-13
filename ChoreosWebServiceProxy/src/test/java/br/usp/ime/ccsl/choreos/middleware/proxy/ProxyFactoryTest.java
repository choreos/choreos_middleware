/**
 * 
 */
package br.usp.ime.ccsl.choreos.middleware.proxy;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.middleware.proxy.ProxyFactory;
import br.usp.ime.ccsl.choreos.middleware.proxy.codegenerator.CodeGeneratorHelper;
import br.usp.ime.ccsl.choreos.middleware.proxy.support.webservice.*;

public class ProxyFactoryTest {

    ProxyFactory factory = new ProxyFactory();

    @Test
    public void testGenerateProxy() throws MalformedURLException {
	URL wsdlLocation = Object.class.getResource("/role.wsdl");
	Object proxy = factory.generateProxyImplementor(wsdlLocation);
	List<String> methods = new ArrayList<String>();

	for (int i = 0; i < proxy.getClass().getMethods().length; i++) {
	    methods.add(proxy.getClass().getMethods()[i].getName());
	}

	// for (int i = 0; i < HelloWorld8081.class.getMethods().length; i++) {
	// assertTrue(methods.contains(HelloWorld8081.class.getMethods()[i].getName()));
	//	    
	// }
    }

    @Test
    public void shouldProvideAProxyInstanceForAGivenWSDL() throws MalformedURLException, InterruptedException {
	URL wsdlLocation = Object.class.getResource("/role.wsdl");

	String address = "http://localhost:9124/" + ProxyFactory.getPortName(wsdlLocation);

	ProxyFactory factory = new ProxyFactory();
	Object proxyInstance = factory.generateProxyImplementor(wsdlLocation);

	ServerFactoryBean serverFactoryBean = raiseWebServer(address, proxyInstance);

	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	assertEquals(cgh.getNamespace(wsdlLocation), cgh.getNamespace(new URL(address + "?wsdl")));
	serverFactoryBean.destroy();

    }

    private ServerFactoryBean raiseWebServer(String address, Object proxyInstance) {
	ServerFactoryBean serverFactoryBean = new ServerFactoryBean();

	serverFactoryBean.setAddress(address);
	serverFactoryBean.setServiceBean(proxyInstance);

	serverFactoryBean.create();

	return serverFactoryBean;
    }

    @Test
    public void shouldGiveTheCorrectPortName() throws Exception {
	String portName = ProxyFactory.getPortName(Object.class.getResource("/role.wsdl"));
	assertEquals("HelloWorld8081", portName);
    }

}
