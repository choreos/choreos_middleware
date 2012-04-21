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
	 * Pass the powsContext to coordination delegates
	 * and the cdConsumeContext to the POWSs
	 * 
	 * @param powsContext the base soap services
	 * @param powsProvideContext the set of SOAP Provide services
	 * that plug pows services into the bus
	 * @param cdConsumeContext the set of SOAP Consume services
	 * that consume the Coordination Delegate services on the bus
	 */
	public void cast(Set<Service> powss, Set<Service> powsProvides,
			Set<Service> cdConsumes) {
		
		castContextToPOWSs(powss, cdConsumes);
		castContextToCDs(cdConsumes, powsProvides);
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

	// each CD talks with its correspondent POWS Provide and the other CDs
	private void castContextToCDs(Set<Service> cdConsumes,
			Set<Service> powsProvides) {

		ContextCaster caster = new ContextCaster();
		CDContextBuilder builder = new CDContextBuilder();
		
		for (Service cd: cdConsumes) {
			Set<Service> cdContext = builder.buildContext(cd, cdConsumes,
					powsProvides);
			caster.cast(cd, cdContext);
		}
	}
}
