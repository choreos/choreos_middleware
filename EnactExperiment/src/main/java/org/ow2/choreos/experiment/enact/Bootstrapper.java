package org.ow2.choreos.experiment.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.nodes.NodeNotCreatedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;

public class Bootstrapper {

	private static final String NPM_HOST = "http://localhost:9100/deploymentmanager";
	private static final int DELAY_BETWEEN_REQUESTS = 2000; // to cope with the amazon "one request per second" rule 

	private int vmsQuantity; // how many VMs we will use
	private Report report;
	
	public Bootstrapper(int vmsQuantity, Report report) {
		this.vmsQuantity = vmsQuantity;
		this.report = report;
	}
	
	private List<Node> vms;

	/**
	 * Creates VMs,
	 */
	public void boot() {
		
		System.out.println(Utils.getTimeStamp() + "Creating VMs...");
		long t0 = System.currentTimeMillis();
		List<Node> vms = createVMs();
		System.out.println(Utils.getTimeStamp() + "Created machines: ");
		for (Node vm: vms) {
			String timeStamp = Utils.getTimeStamp();
			if (timeStamp != null & vm != null) {
				System.out.println(timeStamp + vm.getIp() + "  ");
			}
		}
		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		System.out.println(Utils.getTimeStamp() + "### Bootstrap completed in " + duration + " milliseconds ###");
		report.setVmsCreationTotalTime(duration);
	}

	private List<Node> createVMs() {
	
		final List<Node> vms = new ArrayList<Node>();
		Thread[] trds = new Thread[vmsQuantity];
		
		for (int i=0; i<vmsQuantity; i++) {
			final int idx = i;
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {

					long t0 = System.currentTimeMillis();
					NodePoolManager npm = new NodesClient(NPM_HOST);
					Node req = new Node();
					Node vm = null;
					boolean created = false;
					
					while (!created) {
						try {
							vm = npm.createNode(req, null);
							created = true;
						} catch (NodeNotCreatedException e) {
							System.out.println(Utils.getTimeStamp() + "VM #" + idx + " not created!");
						}
					}
					
					long tf = System.currentTimeMillis();
					long duration = tf - t0;
					vms.add(vm);
					System.out.println(Utils.getTimeStamp() + "VM #" + idx
							+ " created in " + duration + " milliseconds");
					report.addVmCreationTime(duration);
				}
			});
			trds[i].start();
			
			// a pause before trigger a new thread
			try {
				Thread.sleep(DELAY_BETWEEN_REQUESTS);
			} catch (InterruptedException e) {
				System.out.println("Exception while sleeping! Should not happen.");
			}
		}
		
		waitThreads(trds);
		
		return vms;
	}

	private void waitThreads(Thread[] trds) {
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Node> getNodes() {
		
		return vms;
	}
	
	public static void main(String[] args) {
		
		Bootstrapper booter = new Bootstrapper(1, new Report(1));
		booter.boot();
	}
}
