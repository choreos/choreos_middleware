/**
 * 
 */
package ime.usp.br.proxy;

import static org.junit.Assert.*;
import ime.usp.br.proxy.support.webservice.HelloWorld8081;

import javax.xml.ws.Endpoint;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestProxyController {

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
	HelloWorld8081 wsProvider1 = new HelloWorld8081("service1");

	ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
	serverFactoryBean.setServiceClass(HelloWorld8081.class);
	serverFactoryBean.setAddress("http://localhost:8085/hello");
	serverFactoryBean.setServiceBean(wsProvider1);

	service1 = serverFactoryBean.create();

	serverFactoryBean = new ServerFactoryBean();
	serverFactoryBean.setServiceClass(HelloWorld8081.class);
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
	ProxyController proxy = new ProxyController(TestProxyController.service1);
	proxy.addNewServer(TestProxyController.service2);
	assertTrue(proxy.knownWebServices.contains(TestProxyController.service2));
    }

    @Test
    public void testGetServerList() throws Exception {
	ProxyController proxy = new ProxyController(TestProxyController.service1);
	assertEquals(proxy.getServerList(), proxy.knownWebServices);
    }

    /**
     * Test method for
     * {@link ime.usp.br.proxy.ProxyController#switchWSImplementation(org.apache.cxf.endpoint.Server)}
     * .
     */
    @Test
    public final void testSwitchWSImplementation() {
	ProxyController proxy = new ProxyController(TestProxyController.service1);
	proxy.switchWSImplementation(TestProxyController.service2);
	assertEquals(TestProxyController.service2, proxy.currentServer);
    }
}
