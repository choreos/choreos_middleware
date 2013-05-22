package eu.choreos.enactment.context;

import java.util.Set;

import eu.choreos.servicedeployer.datamodel.Service;

/**
 * Casts the proper context to the services
 * @author leonardo
 *
 */
public class ChorContextCaster {

	/**
	 * Pass the cdConsumeContext to the POWSs
	 * 
	 * @param powss the base soap services
	 * @param cdConsumes the set of SOAP Consume services
	 * that consume the Coordination Delegate services on the bus
	 */
	public void cast(Set<Service> powss, Set<Service> cdConsumes) {
		
		castContextToPOWSs(powss, cdConsumes);
	}

	// each POWS talks only with CDs
	private void castContextToPOWSs(Set<Service> powss,
			Set<Service> cdConsumes) {
		
		ContextCaster caster = new ContextCaster();
		POWSContextBuilder builder = new POWSContextBuilder();
		
		for (Service pows: powss) {
			Set<Service> context = builder.buildContext(pows, cdConsumes);
			caster.cast(pows, context);
		}
	}
}
