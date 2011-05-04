package hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class HelloWorld8084 {

	@WebMethod
	public String sayHello(@WebParam(name="yourName") String param) {
		return "Hello from 8084" + param; 
	}
	
	public static void main(String[] args) {
        HelloWorld8084 service = new HelloWorld8084();
        Endpoint endpoint = Endpoint.create(service);
        endpoint.publish("http://localhost:8084/hello");
	}
}
