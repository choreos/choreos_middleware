/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import ime.usp.br.proxy.support.webservice.HelloWorldService;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.BeforeClass;
import org.junit.Test;

public class ProxyControllerTest {

    ProxyController proxy = null;
    static Server service1, service2;

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
	serverFactoryBean.setAddress("http://localhost:8090/hello");
	serverFactoryBean.setServiceBean(service2);

	service2 = serverFactoryBean.create();

	System.out.println("Services available at http://localhost/hello on ports 8085 and 8086");
    }

    @Test
    public void testConstructor() throws Exception {
	this.proxy = new ProxyController(service1);
	assertTrue(proxy.knownWebServices.contains(service1));
    }

    @Test
    public void testAddNewServer() throws Exception {
	ProxyController proxy = new ProxyController(ProxyControllerTest.service1);
	proxy.addNewServer(ProxyControllerTest.service2);
	assertTrue(proxy.knownWebServices.contains(ProxyControllerTest.service2));
    }

    @Test
    public void testGetServerList() throws Exception {
	ProxyController proxy = new ProxyController(ProxyControllerTest.service1);
	assertEquals(proxy.getServerList(), proxy.knownWebServices);
    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyController#switchWSImplementation(org.apache.cxf.endpoint.Server)}
     * .
     */
    @Test
    public final void testSwitchWSImplementation() {
	ProxyController proxy = new ProxyController(ProxyControllerTest.service1);
	proxy.switchWSImplementation(ProxyControllerTest.service2);
	assertEquals(ProxyControllerTest.service2, proxy.currentServer);
    }
}
