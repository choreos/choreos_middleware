package hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class HelloWorld {

	@WebMethod
	public String sayHello(@WebParam(name="yourName") String param) {
		return "Hello " + param; 
	}
	
	public static void main(String[] args) {
        HelloWorld service = new HelloWorld();
        Endpoint endpoint = Endpoint.create(service);
        endpoint.publish("http://localhost:8080/hello");
	}
}
