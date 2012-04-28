package eu.choreos.enactment.boot;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.enactment.topology.TopologyCaster;
import eu.choreos.npm.NodePoolManager;
import eu.choreos.npm.NodePoolManagerClient;

/**
 * Choreography bootstrapper
 * 
 * It must be run before deploying choreography artifacts
 * @author leonardo
 *
 */
public class Bootstrapper {

	private static final int VMS_NUM = 3; // how many VMs we will use
	
	// ips
	private String master;
	private List<String> slaves;
	
	/**
	 * Creates VMs,
	 * install petals,
	 * generate and copy topology to nodes,
	 * start petals
	 */
	public void boot() {
		
		System.out.println("Creating VMs...");
		slaves = createVMs();
		master = slaves.remove(0); // first VM will be the master
		
		System.out.println("Master: " + master);
		for (String slave: slaves) {
			System.out.println("Slave: " + slave);
		}
		
		installPetals();

		TopologyCaster caster = new TopologyCaster(master, slaves);
		caster.cast();
		
		startPetals();
	}

	// invoke AWS to create VMs and retrieve their IPs
	private List<String> createVMs() {
	
		final List<String> vms = new ArrayList<String>();
		Thread[] trds = new Thread[VMS_NUM];
		
		for (int i=0; i<VMS_NUM; i++) {
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {
					NodePoolManager npm = new NodePoolManagerClient();
					String vm = npm.createNode();
					vms.add(vm);
				}
			});
			trds[i].start();
		}
		
		waitThreads(trds);
		
		return vms;
	}

	private void installPetals() {

		List<String> nodes = getAllNodes();
		Thread[] trds = new Thread[VMS_NUM];
		
		int i = 0;
		for (final String node: nodes) {
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {
					PetalsManager installer = new PetalsManager(node);
					installer.install();
				}
			});
			trds[i].start();
			i++;
		}
		
		waitThreads(trds);
	}

	// starts and install components
	private void startPetals() {

		Thread[] trds = new Thread[VMS_NUM];

		// it's better start first the master
		trds[0] = new Thread(new Runnable() {
			@Override public void run() {
				PetalsManager starter = new PetalsManager(master);
				starter.start();
				starter.installComponents();
				System.out.println("Bootstrap complete at " + master);
			}
		});
		trds[0].start();

		int i = 1;
		for (final String slv: slaves) {
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {
					PetalsManager starter = new PetalsManager(slv);
					starter.start();
					starter.installComponents();
					System.out.println("Bootstrap complete at " + slv);
				}
			});
			trds[i].start();
			i++;
		}
		
		waitThreads(trds);
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
	
	private List<String> getAllNodes() {
		
		List<String> nodes = new ArrayList<String>();
		nodes.add(master);
		for (String slv: slaves) {
			nodes.add(slv);
		}
		return nodes;
	}
}
