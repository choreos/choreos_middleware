package eu.choreos.enactment.topology;

import eu.choreos.enactment.CloudNode;

public class CloudNodeTest {

	// use a valid IP
	private static final String IP =  "23.21.22.170";
	
	/**
	 * Not-automated test
	 */
	public static void main(String[] args) {

		CloudNode node = new CloudNode(IP);
		System.out.println(node.getPrivateDNS());
		System.out.println(node.getPrivateIp());
		System.out.println("###");
	}

}
