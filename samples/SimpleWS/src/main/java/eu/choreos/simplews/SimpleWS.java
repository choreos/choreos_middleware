package eu.choreos.simplews;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class SimpleWS extends ServerResource {
	private final static int PORT = 8042;

	public static void main(String[] args) throws Exception {
		new Server(Protocol.HTTP, PORT, SimpleWS.class).start();
	}

	@Get
	public String toString() {
		return "Hello world!";
	}
}
