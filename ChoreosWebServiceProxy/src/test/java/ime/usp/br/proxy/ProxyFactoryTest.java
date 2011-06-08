/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.assertEquals;
import ime.usp.br.proxy.codeGenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.support.webservice.HelloWorldService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import javax.wsdl.Definition;
import javax.wsdl.WSDLException;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.ws.Endpoint;

import org.junit.BeforeClass;
import org.junit.Test;

import com.ibm.wsdl.BindingImpl;

public class ProxyFactoryTest {

    ProxyFactory factory = new ProxyFactory();

    /**
     * Sets up a HelloWorld Web Server to run tests on.
     * 
     * A web server is raised at http://localhost:8089/hello to allow for WSDL
     * check and usage.
     * 
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	HelloWorldService service = new HelloWorldService("1");
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:8089/hello");
	System.out.println("Serviço disponibilizado na porta 8089");
    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyFactory#generateProxyImplementor(java.net.URL, int)}.
     * 
     * @throws MalformedURLException
     */
    @Test
    public void testGenerateProxy() throws MalformedURLException {
	URL wsdlLocation = new URL("http://localhost:8089/hello?wsdl");
	factory.generateProxyImplementor(wsdlLocation);
	CodeGeneratorHelper cgh = new CodeGeneratorHelper();
	assertEquals("HelloWorld8081", cgh.getPortName(wsdlLocation));
	assertEquals("http://webservice.support.proxy.br.usp.ime/", cgh.getNamespace(wsdlLocation));
    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyFactory#getProxyInstance(java.net.URL, int)}
     * .
     * 
     * @throws MalformedURLException
     * @throws InterruptedException
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testInstantiateProxy() throws MalformedURLException, InterruptedException {
	URL wsdlLocation = new URL("http://localhost:8089/hello?wsdl");

	factory.getProxyInstance(wsdlLocation);

	Definition def = null;
	try {
	    WSDLFactory factory = WSDLFactory.newInstance();
	    WSDLReader reader = factory.newWSDLReader();
	    reader.setFeature("javax.wsdl.verbose", false);
	    reader.setFeature("javax.wsdl.importDocuments", true);
	    def = reader.readWSDL(null, wsdlLocation.toExternalForm());
	} catch (WSDLException e) {
	    e.printStackTrace();
	}
	assertEquals("http://webservice.support.proxy.br.usp.ime/", def.getTargetNamespace());

	String serviceName = "";
	Collection bindingList = def.getBindings().values();
	for (Iterator bindingIterator = bindingList.iterator(); bindingIterator.hasNext();) {
	    BindingImpl bind = (BindingImpl) bindingIterator.next();
	    if (!bind.getPortType().isUndefined())
		serviceName = bind.getPortType().getQName().getLocalPart();
	}

	assertEquals("HelloWorld8081", serviceName);
    }

}
