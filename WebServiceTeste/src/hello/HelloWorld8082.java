package hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class HelloWorld8082 {

	@WebMethod
	public String sayHello(@WebParam(name="yourName") String param) {
		return "Hello from 8082 " + param; 
	}
	
	public static void main(String[] args) {
        HelloWorld8082 service = new HelloWorld8082();
        Endpoint endpoint = Endpoint.create(service);
        endpoint.publish("http://localhost:8082/hello");
	}
}
