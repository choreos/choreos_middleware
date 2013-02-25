package org.ow2.choreos.experiment.enact;


/**
 * The same than Experiment,
 * but pre-allocates the VMs; 
 * So, it is necessary to set NODE_SELECTOR=ROUND_ROBIN
 * 
 * @author leonardo
 *
 */
public class ExperimentWithAllocation {
	
	public static final int CHORS_QTY = 2; // how many micro choreographies there will be
	public static final int RUN = 1; // sequence ordinal

	public static void main(String[] args) {
	
		Report report = new Report(RUN);
	
		int vmsQty = CHORS_QTY * Experiment.SERVICES_PER_CHOR;
		report.setVmsQuantity(vmsQty);
		Bootstrapper booter = new Bootstrapper(vmsQty, report);
		booter.boot();
		
		Experiment experiment = new Experiment(report, CHORS_QTY);
		experiment.run();
	}
}
