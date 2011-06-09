/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ime.usp.br.proxy.codegenerator.CodeGeneratorHelper;
import ime.usp.br.proxy.support.webservice.HelloWorldService;

import java.net.URL;
import java.util.ArrayList;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProxyControllerTest {

    ProxyController proxy = null;
    private static int proxyPort;
    private static String url1;
    static Server service1, service2;
    private static String url2;

    /**
     * Creates and sets up two different WS providers for the HelloWorld8081
     * test service.
     * 
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	HelloWorldService wsProvider1 = new HelloWorldService("service1");

	ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
	serverFactoryBean.setServiceClass(HelloWorldService.class);
	serverFactoryBean.setAddress("http://localhost:8085/hello");
	serverFactoryBean.setServiceBean(wsProvider1);

	service1 = serverFactoryBean.create();

	serverFactoryBean = new ServerFactoryBean();
	serverFactoryBean.setServiceClass(HelloWorldService.class);
	serverFactoryBean.setAddress("http://localhost:8086/hello");
	serverFactoryBean.setServiceBean(service2);

	service2 = serverFactoryBean.create();

	url1 = service1.getEndpoint().getEndpointInfo().getAddress();
	url2 = service2.getEndpoint().getEndpointInfo().getAddress();

	proxyPort = 9123;
    }

    @Test
    public void shouldInstantiateAndRaiseAProxyWS() throws Exception {
	ProxyController controller = new ProxyController();
	URL wsdl = Object.class.getResource("/role.wsdl");

	URL serviceURL = controller.instantiateProxy(wsdl, proxyPort);

	CodeGeneratorHelper cgh = new CodeGeneratorHelper();

	assertEquals(cgh.getNamespace(wsdl), cgh.getNamespace(new URL(serviceURL
		.toExternalForm()
		+ "?wsdl")));

    }

    @Test
    public void testAddNewServer() throws Exception {

	ProxyController proxy = new ProxyController();
	proxy.addNewWebService(url1);
	assertTrue(proxy.getServerList().contains(url1));
    }

    @Test
    public void testGetServerList() throws Exception {
	ProxyController proxy = new ProxyController();
	assertEquals(proxy.getServerList(), new ArrayList<String>(proxy.knownWebServices.keySet()));
    }

    @Test
    public void shouldSetCurrentWebServiceUrlAfterAddingFirstWS() throws Exception {
	ProxyController proxy = new ProxyController();

	assertEquals(null, proxy.currentInterceptorURL);
	proxy.addNewWebService(url1);
	assertEquals(url1, proxy.currentInterceptorURL);

	proxy.addNewWebService(url2);
	assertEquals(url1, proxy.currentInterceptorURL);
    }

    @Test
    public final void testSwitchWSImplementation() throws Exception {
	ProxyController controller = new ProxyController();
	
	controller.instantiateProxy(Object.class.getResource("/role.wsdl"), 5558);

	controller.addNewWebService(url1);
	controller.addNewWebService(url2);

	controller.switchWSImplementation(url2);
	assertEquals(url2, controller.currentInterceptorURL);
    }
}
