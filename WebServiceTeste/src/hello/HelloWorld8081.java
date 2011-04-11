package hello;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService
public class HelloWorld8081 {

	private String instanceName;
	
	public HelloWorld8081(String name) {
		// TODO Auto-generated constructor stub
		this.instanceName = name;
	}
	
	@WebMethod
	public String sayHello(@WebParam(name="yourName") String param) {
		return "Hello from " + instanceName + " " + param; 
	}
	
	public static void main(String[] args) {
		HelloWorld8081[] service = new HelloWorld8081[4];
		
		for(int i = 8081; i<=8084; i++){
	        service[i-8081] = new HelloWorld8081(i+"");
	        
	        Endpoint endpoint = Endpoint.create(service[i-8081]);
	        
	        endpoint.publish("http://localhost:"+i+"/hello");
	        System.out.println("Serviço disponibilizado na porta "+i);
		}
		
	}
}
