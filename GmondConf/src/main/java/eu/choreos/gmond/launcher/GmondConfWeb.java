package eu.choreos.gmond.launcher;

import javax.xml.ws.Endpoint;

import eu.choreos.gmond.GmondConf;
import eu.choreos.gmond.IGmondConf;

public class GmondConfWeb {
	
	public static void main(String[] args) {
		IGmondConf gmondconf = new GmondConf();
		Endpoint.publish("http://localhost:1234/gmond", gmondconf);
		
	}

}
