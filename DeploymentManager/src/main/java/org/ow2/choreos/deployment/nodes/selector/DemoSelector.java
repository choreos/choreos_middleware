package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

/**
 * A Node Selector specially tailored to the EC Demo 2012
 * 
 * @author leonardo
 *
 */
public class DemoSelector implements NodeSelector {

    // P.S. AirlineGroundStaffMID is caught by Airline
	private static final String[] VM1 = { "Airline", "AirlineGroundStaffMID",
			"Airport", "GroundTransportationCompany" };

	private static final String[] VM2 = { "Hotel", "StandAndGate",
			"Travelagency", "Weather" };

	public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException {

		Node node1 = npm.getNodes().get(0);
		Node node2 = npm.getNodes().get(1);		

		List<Node> list = new ArrayList<Node>();
		
		for (String svc: VM1) {
			if (config.getName().toLowerCase().contains(svc.toLowerCase())) {
				list.add(node1);
			}
		}
		
		for (String svc: VM2) {
			if (config.getName().toLowerCase().contains(svc.toLowerCase())) {
				list.add(node2);
			}
		}
		
		if(list.isEmpty())
			list.add(node1);
		return list;
	}

}
