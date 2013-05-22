package eu.choreos.enactment;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

import eu.choreos.enactment.Configuration;
import eu.choreos.enactment.utils.SshUtil;

/**
 * With this class we can retrieve 
 * some necessary node information
 * @author leonardo
 *
 */
public class CloudNode {

	private static final String PRIVATE_DNS_SCRIPT = "private_dns.sh";
	private static final String USER = "ubuntu";
	private String hostname, privateIP, privateDNS;
	
	public CloudNode(String hostname, String privateIP, String privateDNS) {
		this.hostname = hostname;
		this.privateIP = privateIP;
		this.privateDNS = privateDNS;
	}

	public CloudNode(String hostname) {
		this.hostname = hostname;
	}

	public String getHostname() {
		return hostname;
	}

	public String getPrivateIp() {
		if (privateIP == null)
			privateIP = retrievePriveteIP();
		return privateIP;
	}

	public String getPrivateDNS() {
		if (privateDNS == null)
			privateDNS = retrievePrivateDNS();
		return privateDNS;
	}
	
	private String retrievePrivateDNS() {
		
    	URL scriptFile = ClassLoader.getSystemResource(PRIVATE_DNS_SCRIPT);
    	String command = null;
		try {
			command = FileUtils.readFileToString(new File(scriptFile.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Should not happen");
		}
		
		String key = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
 		SshUtil ssh = new SshUtil(hostname, USER, key);
 		while (!ssh.isAccessible()){
 			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}

 		String dns = null;
 		try {
			dns = ssh.runCommand(command);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not retrieve private DNS");
		}

		return dns.replace("\n", "").trim();
	}

	private String retrievePriveteIP() {
		
		String key = Configuration.get("AMAZON_PRIVATE_SSH_KEY");
 		SshUtil ssh = new SshUtil(hostname, USER, key);
 		while (!ssh.isAccessible()){
 			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {}
		}

 		String ifconfig = null;
 		try {
			ifconfig = ssh.runCommand("ifconfig");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Could not retrieve private DNS");
		}

 		Pattern pattern = Pattern.compile("inet addr\\:([0-9.]*)  Bcast");
 		Matcher matcher = pattern.matcher(ifconfig);
 		String ip = null;
 		if (matcher.find()) {
 			ip = matcher.group(1);
 		} else {
 			System.out.println("Could not retrieve the IP");
 		}
 		
		return ip.replace("\n", "").trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hostname == null) ? 0 : hostname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CloudNode other = (CloudNode) obj;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CloudNode [hostname=" + hostname + ", privateIP=" + privateIP
				+ ", privateDNS=" + privateDNS + "]";
	}
	
}
