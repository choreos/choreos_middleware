package eu.choreos.nodepoolmanager.selector;

import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

/**
 * A Node Selector specially tailored to the EC Demo 2012
 * 
 * @author leonardo
 *
 */
public class DemoSelector implements NodeSelector {

	private static final String[] VM1 = { "Airline", "AirlineGroundStaffMID",
			"Airport", "GroundTransportationCompany" };

	private static final String[] VM2 = { "Hotel", "StandAndGateManagement",
			"Travelagency", "WeatherForecastService" };

	private Node node1, node2;

	/**
	 * Before select a node, the cloud provider must contains two clean VMs
	 * @param cloudProvider
	 */
	DemoSelector(CloudProvider cloudProvider) {
		
		this.node1 = cloudProvider.getNodes().get(0);
		this.node2 = cloudProvider.getNodes().get(1);		
	}

	public Node selectNode(Config config) {

		for (String svc: VM1) {
			if (config.getName().toLowerCase().contains(svc.toLowerCase())) {
				return node1;
			}
		}
		
		for (String svc: VM2) {
			if (config.getName().toLowerCase().contains(svc.toLowerCase())) {
				return node2;
			}
		}
		
		// default
		return node1;
	}

}
