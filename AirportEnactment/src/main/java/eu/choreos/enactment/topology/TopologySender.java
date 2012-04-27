package eu.choreos.enactment.topology;

import java.io.File;

import eu.choreos.enactment.Configuration;
import eu.choreos.enactment.utils.CommandLine;
import eu.choreos.enactment.utils.SshUtil;

class TopologySender {

	private static final String SCP_COMMAND = 
			"scp -i $key $file ubuntu@$host:";
	private static final String TOPOLOGY_FOLDER = 
			"/opt/dsb-fulldistribution/dsb-distribution-1.0-SNAPSHOT/conf/";
	private static final String USER = "ubuntu";
	
	public void send(File topology, String hostname) {
		
		String key = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
		
		String up = getUpCommand(topology.getAbsolutePath(), hostname, key);
		System.out.println(up);
		CommandLine.runLocalCommand(up);

		String mv = "sudo mv topology.xml " + TOPOLOGY_FOLDER;
		System.out.println(mv);
		SshUtil ssh = new SshUtil(hostname, USER, key);
		try {
			System.out.println(ssh.runCommand(mv));
		} catch (Exception e) {
			System.out.println("Could not copy topology to " + hostname);
			e.printStackTrace();
		}
	}
	
	private String getUpCommand(String topology, String host, String key) {
		
		String command = SCP_COMMAND;
		command = command.replace("$key", key);
		command = command.replace("$file", topology);
		command = command.replace("$path", TOPOLOGY_FOLDER);
		command = command.replace("$host", host);		
		return command;
	}

}
