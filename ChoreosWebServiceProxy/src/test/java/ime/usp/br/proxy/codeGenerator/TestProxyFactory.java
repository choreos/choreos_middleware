/**
 * 
 */
package ime.usp.br.proxy.codeGenerator;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import ime.usp.br.proxy.ProxyFactory;
import ime.usp.br.proxy.support.webservice.HelloWorld8081;

import javax.xml.ws.Endpoint;

import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Short Description
 * 
 * Optional: Longer description
 * 
 * @author (classes and interfaces only, required; Multiple authors must be
 *         listed as new @author tag)
 * @version (classes and interfaces only, required)
 * @param (methods and constructors only)
 * @return (methods only)
 * @throws
 * @see
 * @since
 */
public class TestProxyFactory {

    ProxyFactory factory = new ProxyFactory();
    
    /**
     * Sets up a HelloWorld Web Server to run tests on.
     * 
     * A web server is raised at http://localhost:8085/hello to allow for WSDL
     * check and usage.
     * 
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	HelloWorld8081 service = new HelloWorld8081("1");
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:8085/hello");
	System.out.println("Serviço disponibilizado na porta 8085");

    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyFactory#generateProxy(java.net.URL)}.
     * @throws MalformedURLException 
     */
    @Test
    public void testGenerateProxy() throws MalformedURLException {
	URL wsdlLocation = new URL("http://localhost:8085/hello?wsdl");
	factory.generateProxy(wsdlLocation);
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	//assertEquals("HelloWorld8081", cgh.getPortName(wsdlLocation));
	//assertEquals("http://webservice.support.proxy.br.usp.ime/", cgh.getNamespace(wsdlLocation));
    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyFactory#instantiateProxy(java.net.URL, int)}
     * .
     */
    @Test
    public void testInstantiateProxy() {
	fail("Not yet implemented");
    }

}
