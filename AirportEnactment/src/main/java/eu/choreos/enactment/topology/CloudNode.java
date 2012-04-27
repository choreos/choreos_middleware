package eu.choreos.enactment.topology;

/**
 * With this class we can retrieve 
 * some necessary node information
 * @author leonardo
 *
 */
class CloudNode {

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
		return "";
	}

	private String retrievePriveteIP() {
		return "";
	}
	
}
