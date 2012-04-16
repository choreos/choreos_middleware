package eu.choreos.servicedeployer;

import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public interface ServiceDeployer {
	
	public Service deploy(ServiceSpec spec);

}
