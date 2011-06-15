package br.usp.ime.ccsl.choreos.middleware.proxy.interceptor;

import static org.junit.Assert.assertEquals;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;

public class ProxyInterceptorTest {
    private Server server1;
    private Server server2;
    private final static int server1Port = 8081;
    private final static int server2Port = 8082;

    @Before
    public void setUp() throws Exception {
	server1 = createServer(server1Port);
	server2 = createServer(server2Port);

	final String server2Address = InterceptorTestWS.getAddress(server2Port);
	final ProxyInterceptor tie = new ProxyInterceptor(server2Address);

	server1.getEndpoint().getInInterceptors().add(tie);
    }

    private Server createServer(int port) {
	final InterceptorTestWS service = new InterceptorTestWS(port);
	final ServerFactoryBean serverFactoryBean = getServerFactoryBean(service);

	return serverFactoryBean.create();
    }

    private ServerFactoryBean getServerFactoryBean(final InterceptorTestWS service) {
	final ServerFactoryBean serverFactoryBean = new ServerFactoryBean();

	serverFactoryBean.setServiceClass(InterceptorTestWS.class);
	serverFactoryBean.setAddress(service.getAddress());
	serverFactoryBean.setServiceBean(service);

	return serverFactoryBean;
    }

    @After
    public void tearDown() throws Exception {
	server1.stop();
	server1.destroy();
	server2.stop();
	server2.destroy();
    }

    @Ignore
    public void shouldChangeEndpoint() throws Exception {
	JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	String wsdlURL = InterceptorTestWS.getAddress(server1Port) + "?wsdl";
	Client client = dcf.createClient(wsdlURL);

	Object[] res = client.invoke("getPort");
	int result = (Integer) res[0];

	assertEquals(8082, result);
    }
}