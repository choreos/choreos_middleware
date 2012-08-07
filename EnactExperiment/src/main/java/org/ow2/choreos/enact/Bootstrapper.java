package org.ow2.choreos.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.client.NPMClient;
import org.ow2.choreos.npm.datamodel.Node;

public class Bootstrapper {

	private static final String NPM_HOST = "localhost:9100/nodepoolmanager";
	
	private int vmsQuantity; // how many VMs we will use
	
	public Bootstrapper(int vmsQuantity) {
		this.vmsQuantity = vmsQuantity;
	}
	
	// ips
	private List<Node> vms;

	/**
	 * Creates VMs,
	 */
	public void boot() {
		
		System.out.println("Creating VMs...");
		System.out.print("Created machines: ");
		for (Node vm: createVMs()) 
			System.out.print(vm.getIp() + "  ");
		System.out.println("\n### Bootstrap completed ###");
	}

	private List<Node> createVMs() {
	
		final List<Node> vms = new ArrayList<Node>();
		Thread[] trds = new Thread[vmsQuantity];
		
		for (int i=0; i<vmsQuantity; i++) {
			trds[i] = new Thread(new Runnable() {
				@Override public void run() {
					NodePoolManager npm = new NPMClient(NPM_HOST);
					Node vm = npm.createNode(null);
					vms.add(vm);
				}
			});
			trds[i].start();
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
}
