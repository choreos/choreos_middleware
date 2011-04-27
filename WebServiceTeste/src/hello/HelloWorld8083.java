package hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class HelloWorld8083 {

	@WebMethod
	public String sayHello(@WebParam(name="yourName") String param) {
		return "Hello from 8083" + param; 
	}
	
	public static void main(String[] args) {
        HelloWorld8083 service = new HelloWorld8083();
        Endpoint endpoint = Endpoint.create(service);
        endpoint.publish("http://localhost:8083/hello");
	}
}
