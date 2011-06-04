package ime.usp.br.proxy.support.webservice;

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
	// TODO Auto-generated constructor stub
	this.instanceName = name;
    }

    @WebMethod
    @WebResult(name = "greeting")
    public String sayHello(@WebParam(name = "yourName") String param) {
	System.out.println(this.instanceName + ": Requisition number: " + reqCount++);

	return "Hello from " + instanceName + " " + param;
    }
    
    public static void main(String[] args) {
	HelloWorld8081 service = new HelloWorld8081("1");
	Endpoint endpoint = Endpoint.create(service);
	endpoint.publish("http://localhost:8081/hello");
	System.out.println("Serviço disponibilizado na porta 8081");
    }
}
