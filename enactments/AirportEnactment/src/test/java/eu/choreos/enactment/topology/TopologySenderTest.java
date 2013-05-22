package eu.choreos.enactment.topology;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.choreos.enactment.CloudNode;

public class TopologySenderTest {

	// use a valid IP
	private static final String IP =  "23.21.22.170";
	
	/**
	 * Not-automated test
	 */
	public static void main(String[] args) {

		CloudNode master = new CloudNode("host1", "192.168.0.101", "CHOREOS-MASTER");
		List<CloudNode> slaves = new ArrayList<CloudNode>();
		slaves.add(new CloudNode("host2", "192.168.0.102", "SLAVE1"));
		slaves.add(new CloudNode("host3", "192.168.0.103", "SLAVE2"));

		TopologyBuilder builder = new TopologyBuilder(master, slaves);
		File topology = builder.build();
		
		TopologySender sender = new TopologySender();
		sender.send(topology, IP);
	}

}
