package eu.choreos.gmond.reloader;
import javax.xml.ws.Endpoint;



public class StartStopGmondService {

	
	public static void main(String[] args) {
		String port = "12345";
		String host = "http://localhost";
		String path = "GmondReloader";
		Endpoint.publish(host+":"+port+"/"+path,new GmondReloader());
	}
}
