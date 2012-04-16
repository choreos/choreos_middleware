package eu.choreos.enactment;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public interface Deployer {

	public Service deploy(ServiceSpec spec);
}
