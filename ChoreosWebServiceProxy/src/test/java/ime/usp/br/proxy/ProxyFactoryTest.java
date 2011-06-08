/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ime.usp.br.proxy.codegenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.support.webservice.HelloWorld8081;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.Test;

public class ProxyFactoryTest {

    ProxyFactory factory = new ProxyFactory();

    @Test
    public void testGenerateProxy() throws MalformedURLException {
	URL wsdlLocation = Object.class.getResource("/role.wsdl");
	Object proxy = factory.generateProxyImplementor(wsdlLocation);
	List<String> methods = new ArrayList<String>();
	
	for (int i = 0; i < proxy.getClass().getMethods().length; i++) {
	    methods.add(proxy.getClass().getMethods()[i].getName());
	    System.out.println(proxy.getClass().getMethods()[i].getName());
	}
	
	for (int i = 0; i < HelloWorld8081.class.getMethods().length; i++) {
	    
	    System.out.println(HelloWorld8081.class.getMethods()[i].getName());
	    System.out.println(methods.contains(HelloWorld8081.class.getMethods()[i].getName()));
	    
	    assertTrue(methods.contains(HelloWorld8081.class.getMethods()[i].getName()));
	    
	}
    }

    @Test
    public void shouldProvideAProxyInstanceForAGivenWSDL() throws MalformedURLException, InterruptedException {
	URL wsdlLocation = Object.class.getResource("/role.wsdl");

	String address = "http://localhost:9124/" + ProxyFactory.getPortName(wsdlLocation);

	ProxyFactory factory = new ProxyFactory();
	Object proxyInstance = factory.generateProxyImplementor(wsdlLocation);

	for (int i = 0; i < proxyInstance.getClass().getDeclaredMethods().length; i++) {
	    System.out.println(proxyInstance.getClass().getDeclaredMethods()[i]);
	}
	
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
