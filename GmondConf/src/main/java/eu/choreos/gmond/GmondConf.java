package eu.choreos.gmond;

import java.io.IOException;

public class GmondConf {
	final static private String GMOND_FILE = "resources/gmond.conf";
	private String gmondString;
	
	public GmondConf(){
		
	}
	
	public void load() throws IOException{
		gmondString = GmondUtil.readFile(GMOND_FILE);
	}

	public void setSendChannelHost(String host) {
		int i = gmondString.indexOf("udp_send_channel {");
		i = gmondString.indexOf("host", i);
		i = gmondString.indexOf("=",i)+1;
		int j = gmondString.indexOf("\n",i);
		gmondString = gmondString.substring(0, i)+host+gmondString.substring(j);
	}

	public void setSendChannelPort(String string) {
		
	}

	public void save() {
		//TODO 
	}

}
