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
		
		Report report = new Report();
	
		int vmsQty = Experiment.CHORS_QTY * Experiment.SERVICES_PER_CHOR;
		report.setVmsQuantity(vmsQty);
		Bootstrapper booter = new Bootstrapper(vmsQty, report);
		booter.boot();
		
		Experiment experiment = new Experiment(report);
		experiment.run();
	}
}
