package org.ow2.choreos.enact;


/**
 * The same then Experiment,
 * but pre-allocates the VMs; 
 * So, it is necessary to set NODE_SELECTOR=ROUND_ROBIN
 * 
 * @author leonardo
 *
 */
public class ExperimentWithAllocation {

	public static void main(String[] args) {
	
		int vmsQty = Experiment.CHORS_QTY * Experiment.SERVICES_PER_CHOR;
		Bootstrapper booter = new Bootstrapper(vmsQty);
		booter.boot();
		
		Experiment experiment = new Experiment();
		experiment.run();
	}
}
