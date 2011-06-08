/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.assertEquals;
import ime.usp.br.proxy.codeGenerator.CodeGeneratorHelper;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.Test;

public class ProxyFactoryTest {

    ProxyFactory factory = new ProxyFactory();

    @Test
    public void testGenerateProxy() throws MalformedURLException {
	URL wsdlLocation = Object.class.getResource("/role.wsdl");
	factory.generateProxyImplementor(wsdlLocation);
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	assertEquals("HelloWorld8081", cgh.getPortName(wsdlLocation));
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cgh.getNamespace(wsdlLocation));
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
