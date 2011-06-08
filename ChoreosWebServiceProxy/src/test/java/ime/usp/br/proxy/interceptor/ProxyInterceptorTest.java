package ime.usp.br.proxy.interceptor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ProxyInterceptorTest {

    private WSInterceptorTest service1;
    private WSInterceptorTest service2;
    private Server server1;
    private Server server2;
    private ProxyInterceptor tie;

    @Before
    public void setUp() throws Exception {
	service1 = new WSInterceptorTest("8081");
	service2 = new WSInterceptorTest("8082");

	ServerFactoryBean serverFactoryBean1 = new ServerFactoryBean();
	serverFactoryBean1.setServiceClass(WSInterceptorTest.class);
	serverFactoryBean1.setAddress(service1.getAddress());
	serverFactoryBean1.setServiceBean(service1);

	server1 = serverFactoryBean1.create();

	ServerFactoryBean serverFactoryBean2 = new ServerFactoryBean();
	serverFactoryBean2.setServiceClass(WSInterceptorTest.class);
	serverFactoryBean2.setAddress(service2.getAddress());
	serverFactoryBean2.setServiceBean(service2);

	server2 = serverFactoryBean2.create();

	tie = new ProxyInterceptor(service2.getAddress());
	server1.getEndpoint().getInInterceptors().add(tie);
    }

    @After
    public void tearDown() throws Exception {
	server1.stop();
	server1.destroy();
	server2.stop();
	server2.destroy();
    }

    @Test
    public void shouldChangeEndpoint() throws Exception {
	// TODO
    }
}

@WebService
class WSInterceptorTest {
    private String instancePort;

    public WSInterceptorTest(String port) {
	this.instancePort = port;
    }

    @WebMethod
    public String sendMessage(@WebParam(name = "message") String message) {
	return instancePort;
    }

    public String getAddress() {
	return "http://localhost:" + instancePort + "/hello";
    }
}
