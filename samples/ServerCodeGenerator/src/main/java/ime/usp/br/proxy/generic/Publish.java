package ime.usp.br.proxy.generic;

import javax.xml.ws.Endpoint;

public class Publish {
	
	public static void main(String[] args) {
		GenericImpl proxy = new GenericImpl();
		Endpoint endpoint = Endpoint.create(proxy);
		endpoint.publish("http://localhost:8085/hello");
		
	}

}
