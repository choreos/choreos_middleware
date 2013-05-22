package eu.choreos.enactment.context;

import java.util.HashSet;
import java.util.Set;

import eu.choreos.servicedeployer.datamodel.Service;

class POWSContextBuilder {

	public Set<Service> buildContext(Service pows,
			Set<Service> cdConsumes) {

		Set<Service> powsContext = new HashSet<Service>();
		
		for (Service consume: cdConsumes) {
			if (!consume.getRole().equals(pows.getRole())) {
				powsContext.add(consume);
			}
		}
		
		return powsContext;
	}
}
