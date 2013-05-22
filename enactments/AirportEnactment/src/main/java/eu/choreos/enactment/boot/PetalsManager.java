package eu.choreos.enactment.boot;

import eu.choreos.enactment.CloudNode;
import eu.choreos.enactment.Configuration;
import eu.choreos.enactment.utils.CommandLine;
import eu.choreos.enactment.utils.SshUtil;

public class PetalsManager {

	private static final String CHEF_ADD_PETALS = 
			"knife node run_list add $nodeName petals::demo -c $knifeFile";
	private static final String USER = "ubuntu";
	private static final String PETALS_HOME = 
			"/opt/dsb-fulldistribution/dsb-distribution-1.0-SNAPSHOT";
	private static final String COMPONENTS_URL = 
			"http://valinhos.ime.usp.br:54080/demo2/components.tar.gz"; 
	private static final String COMPONENTS_ZIP = "components.tar.gz"; 
	
	
	private String knifeFile = Configuration.get("CHEF_CONFIG_FILE");
	private String key = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
	private String ip;
	
	public PetalsManager(String ip) {
		this.ip = ip;
	}
	
	public void install() {

		System.out.println("Installing petals on " + ip);
		
		CloudNode node = new CloudNode(ip);
		String chefName = node.getPrivateDNS();
		
		String command = CHEF_ADD_PETALS;
		command = command.replace("$nodeName", chefName);
		command = command.replace("$knifeFile", knifeFile);
		
		CommandLine.runLocalCommand(command);
		
    	SshUtil ssh = new SshUtil(ip, USER, key);
		try {
			ssh.runCommand("sudo chef-client\n");
		} catch (Exception e) {
			System.out.println("Could not run chef-client on " + ip);
			e.printStackTrace();
		}

	}
	
	public void start() {
    	
		System.out.println("Starting petals on " + ip);
		
		SshUtil ssh = new SshUtil(ip, USER, key);
		try {
			ssh.runCommand("cd " + PETALS_HOME + "/bin");
			ssh.runCommand("sudo -i " + PETALS_HOME + "/bin/startup.sh -D");
		} catch (Exception e) {
			System.out.println("Could not start petals on " + ip);
			e.printStackTrace();
		}		
	}

	/**
	 * Must be not called before petals has receive start command
	 */
	public void installComponents() {


		waitStarted();
		System.out.println("Installing petals components on " + ip);
		
		SshUtil ssh = new SshUtil(ip, USER, key);
		try {
			ssh.runCommand("wget " + COMPONENTS_URL);
			ssh.runCommand("tar xzf " + COMPONENTS_ZIP);			
			ssh.runCommand("sudo cp components/*.zip " + PETALS_HOME + "/install");			
		} catch (Exception e) {
			System.out.println("Could not install petals components on " + ip);
			e.printStackTrace();
		}	
	}
	
	// wait petals be completely started
	private void waitStarted() {
		
		System.out.println("Waiting pertals start in " + ip);
		
		SshUtil ssh = new SshUtil(ip, USER, key);
		try {
			
			String folder = PETALS_HOME + "/logs";
			Thread.sleep(1000);
			String logFile = ssh.runCommand("ls -tr " + folder + "/*.log | tail -n 1");
			System.out.println("Looking to " + logFile + " in " + ip);
			String excerpt = "Time to look for new services to expose";
			String grep = "";
			while (!grep.contains(excerpt)) {
				grep = ssh.runCommand("grep '" + excerpt + "' " + logFile);
				Thread.sleep(500);
			}
		} catch (Exception e) {
			System.out.println("Could not wait petals be started on " + ip);
			e.printStackTrace();
		}		
	}
}
