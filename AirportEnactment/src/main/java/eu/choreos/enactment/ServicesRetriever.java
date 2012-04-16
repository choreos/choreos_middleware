package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public interface ServicesRetriever {

	public Set<ServiceSpec> retrieve();
}
