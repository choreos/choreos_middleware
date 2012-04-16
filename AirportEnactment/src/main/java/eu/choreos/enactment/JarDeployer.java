package eu.choreos.enactment;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class JarDeployer implements Deployer {

	@Override
	public Service deploy(ServiceSpec spec) {

		System.out.println("Deploying " + spec.getEndpointName());
		return null;
	}

}
