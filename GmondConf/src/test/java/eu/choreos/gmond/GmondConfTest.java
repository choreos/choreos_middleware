package eu.choreos.gmond;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class GmondConfTest {

	private GmondConf gmondConf;

	@Before
	public void createGmondConf() {
		gmondConf = new GmondConf();
	}
	
	public void shouldLoadFile() throws IOException{
		gmondConf.load();
	}

	public void shouldModifyGmondFile() throws IOException{
		gmondConf.load();
		gmondConf.setSendChannelHost("localhost");
		gmondConf.setSendChannelPort("8888");
		gmondConf.save();
	}
	
	public void shouldChangeChannelHost() throws IOException {
		gmondConf.load();
		gmondConf.setSendChannelHost("localhost");
		gmondConf.save();
		
		String gmondConfString = GmondUtil.readFile("/gmond.conf");
		String sendChannelString = gmondConfString.substring(gmondConfString.indexOf("udp_send_channel {"), gmondConfString.indexOf("}",gmondConfString.indexOf("udp_send_channel {")));
		sendChannelString = sendChannelString.substring(sendChannelString.indexOf("host"),sendChannelString.indexOf("\n",sendChannelString.indexOf("host")));
		String host = sendChannelString.split("=")[1].trim();
		assertEquals(host,"localhost");
	}
	
	
	@Test
	public void shouldChangeChannelPort() {
		
	}

}
