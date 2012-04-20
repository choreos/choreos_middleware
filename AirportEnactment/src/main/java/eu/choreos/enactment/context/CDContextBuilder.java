package eu.choreos.enactment.context;

import java.util.HashSet;
import java.util.Set;

import eu.choreos.servicedeployer.datamodel.Service;

class CDContextBuilder {

	public Set<Service> buildContext(Service cdConsume,
			Set<Service> cdConsumes, Set<Service> powss) {

		Set<Service> cdContext = new HashSet<Service>();
		
		putConsumes(cdConsume, cdContext, cdConsumes);
		putPows(cdConsume, cdContext, powss);
		
		return cdContext;
	}

	private void putConsumes(Service cd, Set<Service> cdContext, 
			Set<Service> cdConsumes) {

		for (Service consume: cdConsumes) {
			if (!cd.equals(consume)) {
				cdContext.add(consume);
			}
		}
	}
	
	private void putPows(Service cdConsume, Set<Service> cdContext,
			Set<Service> powss) {
	
		for (Service pows: powss) {
			if (pows.getRole().equals(cdConsume.getRole())) {
				cdContext.add(pows);
			}
		}
	}
}
