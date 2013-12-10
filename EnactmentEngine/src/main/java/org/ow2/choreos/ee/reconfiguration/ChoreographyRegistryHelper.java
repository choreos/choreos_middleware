package org.ow2.choreos.ee.reconfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.ee.ChorRegistry;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ChoreographyRegistryHelper {

    private static final String CHOR_DEPLOYER_URI = "http://localhost:9102/choreographydeployer/";

    private ChorRegistry registry = ChorRegistry.getInstance();

    private ChoreographyDeployer chorClient;

    public ChoreographyRegistryHelper() {
	setClients();
    }

    private void setClients() {
	this.chorClient = new ChorDeployerClient(CHOR_DEPLOYER_URI);
    }

    public List<DeployableService> getServicesHostedOn(String ipAddress) {

	Choreography chor = getChor(ipAddress);

	List<DeployableService> result = new ArrayList<DeployableService>();
	for (DeployableService service : chor.getDeployableServices()) {
	    for (CloudNode node : service.getSelectedNodes())
		if (node.getIp().equals(ipAddress)) {
		    result.add(service);
		    break;
		}
	}
	return result;
    }

    public List<DeployableServiceSpec> getServiceSpecsForServices(List<DeployableService> services) {

	List<DeployableServiceSpec> specs = new ArrayList<DeployableServiceSpec>();
	for (DeployableService service : services) {
	    specs.add(service.getSpec());
	}

	return specs;
    }

    public Choreography getChor(String ipAddress) {

	String chorId = searchForChor(ipAddress);

	if (chorId.isEmpty())
	    return null;

	Choreography chor = null;
	try {
	    chor = chorClient.getChoreography(chorId);
	} catch (ChoreographyNotFoundException e) {
	    e.printStackTrace();
	}
	return chor;
    }

    public ChoreographyDeployer getChorClient() {
	return chorClient;
    }

    private String searchForChor(String ipAddress) {
	for (Entry<String, Choreography> chor : registry.getAll().entrySet()) {
	    for (DeployableService service : chor.getValue().getDeployableServices()) {
		for (CloudNode node : service.getSelectedNodes()) {
		    if (node.getIp().equals(ipAddress)) {
			return chor.getKey();
		    }
		}
	    }
	}
	return "";
    }

    public Choreography getChoreography(String node, String service) {

	for (Entry<String, Choreography> chor : registry.getAll().entrySet()) {

	    for (DeployableService s : chor.getValue().getDeployableServices()) {

		if (s.getSpec().getName().equals(service)) {

		    for (CloudNode n : s.getSelectedNodes()) {

			if (n.getIp().equals(node)) {
			    return chor.getValue();
			}
		    }
		}
	    }
	}
	return null;
    }
}
