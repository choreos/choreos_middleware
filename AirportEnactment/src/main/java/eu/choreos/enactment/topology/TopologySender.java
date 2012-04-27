package eu.choreos.enactment.topology;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import eu.choreos.enactment.Configuration;
import eu.choreos.enactment.utils.CommandLine;

class TopologySender {

	private static final String scpCommand = 
			"scp -i $key $file ubuntu@$host:$path";
	private static final String topologyFolder = 
			"/opt/dsb-fulldistribution/dsb-distribution-1.0-SNAPSHOT/conf/";
	
	public void send(File topology, String hostname) {
		
		String key = getKey();
		
		String command = getCommand(topology.getAbsolutePath(), key);
		CommandLine.runLocalCommand(command);
	}
	
	private String getCommand(String topology, String key) {
		
		String command = scpCommand;
		command.replace("$key", key);
		command.replace("$file", topology);
		command.replace("$path", topologyFolder);
		return command;
	}

	private String getKey() {
		
		String keyFileName = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
		URL keyFile = ClassLoader.getSystemResource(keyFileName);
		try {
			return FileUtils.readFileToString(new File(keyFile.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Not possible retrieve SSH key");
		}		
		
		return null;
	}
}
