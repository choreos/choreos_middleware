package eu.choreos.enactment.topology;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TopologyCaster {

	private CloudNode master;
	private List<CloudNode> slaves;
	
	public TopologyCaster(String masterHostname, 
			List<String> slaveHostnames) {
		
		this.master = new CloudNode(masterHostname);
		this.slaves = new ArrayList<CloudNode>();
		for (String host: slaveHostnames) {
			this.slaves.add(new CloudNode(host));
		}
	}
	
	public void cast() {
		
		TopologyBuilder builder = new TopologyBuilder(master, slaves);
		File topology = builder.build();
		
		TopologySender sender = new TopologySender();
		for (CloudNode node: slaves) {
			sender.send(topology, node.getHostname());
		}
	}
}
