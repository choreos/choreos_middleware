package hello;

import interceptor.ProxyInterceptor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;

@WebService
public class HelloWorld8081 {

	private String instanceName;
	private int reqCount = 0;

	public HelloWorld8081(String name) {
		// TODO Auto-generated constructor stub
		this.instanceName = name;
	}

	@WebMethod
	@WebResult(name = "greeting")
	public String sayHello(@WebParam(name = "yourName") String param) {
		System.out.println(this.instanceName + ": Requisition number: "
				+ reqCount++);

		return "Hello from " + instanceName + " " + param;
	}

	public static void main(String[] args) {
		
		final int initialPort = 8085;
		final int servers = 4;
		
		HelloWorld8081[] service = new HelloWorld8081[servers];
		
		for (int i = 0; i < servers; i++) {
			int currentPort = initialPort + i;
			service[i] = new HelloWorld8081(currentPort + "");
			

			ProxyInterceptor tie = new ProxyInterceptor();

			ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
			serverFactoryBean.setServiceClass(HelloWorld8081.class);
			serverFactoryBean.setAddress("http://localhost:" + currentPort + "/hello");
			serverFactoryBean.setServiceBean(service[i]);

			Server server = serverFactoryBean.create();

			server.getEndpoint().getInInterceptors().add(tie);

			System.out.println("Serviço disponibilizado na porta " + currentPort);

		}

	}
}
