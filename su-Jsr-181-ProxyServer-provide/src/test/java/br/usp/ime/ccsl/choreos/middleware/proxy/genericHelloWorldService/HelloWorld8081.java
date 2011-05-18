package br.usp.ime.ccsl.choreos.middleware.proxy.genericHelloWorldService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class HelloWorld8081 {

    private String instanceName;
    private int reqCount = 0;

    public HelloWorld8081(String name) {
	this.instanceName = name;
    }

    @WebMethod
    @WebResult(name="greeting")
    public String sayHello(@WebParam(name = "yourName") String param) {
	System.out.println(this.instanceName + ": Requisition number: " + reqCount++);
	
	return "Hello from " + instanceName + " " + param;
    }

    public static void publishService(int port) {
	HelloWorld8081 service = new HelloWorld8081("" + port);
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:" + port + "/hello");
    }

    public static void main(String[] args) {
	HelloWorld8081[] service = new HelloWorld8081[4];

	for (int i = 8081; i <= 8084; i++) {
	    service[i - 8081] = new HelloWorld8081(i + "");

	    Endpoint endpoint = Endpoint.create(service[i - 8081]);

	    endpoint.publish("http://localhost:" + i + "/hello");
	    System.out.println("Serviço disponibilizado na porta " + i);
	}

    }
}
