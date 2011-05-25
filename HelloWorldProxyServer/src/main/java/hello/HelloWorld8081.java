package hello;

import interceptor.ProxyInterceptor;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

//import org.apache.cxf.endpoint.*;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.frontend.ServerFactoryBean;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.service.factory.AbstractServiceConfiguration;
import org.apache.cxf.service.factory.ReflectionServiceFactoryBean;
import org.apache.cxf.ws.addressing.AttributedURIType;
import org.apache.cxf.ws.addressing.EndpointReferenceType;

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
		HelloWorld8081[] service = new HelloWorld8081[4];

		for (int i = 8081; i <= 8082; i++) {
			service[i - 8081] = new HelloWorld8081(i + "");

			ProxyInterceptor tie = new ProxyInterceptor();

			ServerFactoryBean serverFactoryBean = new ServerFactoryBean();
			serverFactoryBean.setServiceClass(HelloWorld8081.class);
			serverFactoryBean.setAddress("http://localhost:" + i + "/hello");
			serverFactoryBean.setServiceBean(service[i - 8081]);

			Server server = serverFactoryBean.create();

			server.getEndpoint().getInInterceptors().add(tie);

			System.out.println("Serviço disponibilizado na porta " + i);

		}

	}
}
