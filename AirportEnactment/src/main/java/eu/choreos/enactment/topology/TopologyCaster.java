package eu.choreos.enactment.topology;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import eu.choreos.enactment.CloudNode;

public class TopologyCaster {

	private CloudNode master;
	private List<CloudNode> slaves;
	
	public TopologyCaster(String masterHostname, 
			List<String> slaveHostnames) {
		
		this.master = new CloudNode(masterHostname);
		this.slaves = new ArrayList<CloudNode>();
		if (slaveHostnames != null) {
			for (String host: slaveHostnames) {
				this.slaves.add(new CloudNode(host));
			}
		}
	}
	
	public void cast() {
		
		TopologyBuilder builder = new TopologyBuilder(master, slaves);
		File topology = builder.build();
		
		TopologySender sender = new TopologySender();
		sender.send(topology, master.getHostname());
		for (CloudNode slave: slaves) {
			sender.send(topology, slave.getHostname());
		}
	}
}
