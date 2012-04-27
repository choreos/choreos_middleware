package eu.choreos.enactment.boot;

import java.util.ArrayList;
import java.util.List;

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

	private static final int VMS_NUM = 1; // how many VMs we will use
	
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
		List<String> slaves = createVMs();
		master = slaves.remove(0); // first VM will be the master
		
		System.out.println("Master: " + master);
		for (String slave: slaves) {
			System.out.println("Slave: " + slave);
		}
		
		installPetals();
	}

	private void installPetals() {

		Thread[] trds = new Thread[VMS_NUM];

		trds[0] = new Thread(new Runnable() {
			@Override public void run() {
				PetalsInstaller installer = new PetalsInstaller();
				installer.install(master);
			}
		});
		trds[0].start();

		int i = 1;
		for (final String slave: slaves) {
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {
					PetalsInstaller installer = new PetalsInstaller();
					installer.install(slave);
				}
			});
			trds[i].start();
			i++;
		}
		
		// wait for all threads finish
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
		
		// wait for all threads finish
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		return vms;
	}

}
