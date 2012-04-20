package eu.choreos.enactment.context;

import java.util.Set;

import eu.choreos.servicedeployer.datamodel.Service;

class ContextCaster {

	/**
	 * Pass the <code>context</code> to the <code>service</code>
	 * @param service
	 * @param context
	 */
	public void cast(Service service, Set<Service> context) {
		
		for (Service partner: context) {
			
			if (!service.equals(partner)) {
				System.out.println(service.getId() + ".setInvocationAddress("
						+ partner.getRole() + ", " + partner.getUri());
			}
		}	
	}
}
