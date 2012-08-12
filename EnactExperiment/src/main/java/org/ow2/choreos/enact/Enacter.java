package org.ow2.choreos.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.client.EnactEngClient;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public class Enacter implements Runnable {
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/enactmentengine";
	private static final String TRAVEL_AGENCY = "travelagency";	

	ChorSpec chorSpec; // input
	int idx; // input
	String travelWSDL = null; // result: deployed travel agency service WSDL
	long duration; // result: enactment duration in milliseconds
	boolean ok = true;
	
	public Enacter(ChorSpec chorSpec, int idx) {
		this.chorSpec = chorSpec;
		this.idx = idx;
	}
	
	@Override
	public void run() {
		
		System.out.println(Utils.getTimeStamp() + "Enacting choreography #" + idx);
		
		long t0 = System.currentTimeMillis();
		EnactmentEngine enacter = new EnactEngClient(ENACTMENT_ENGINE_HOST);
		String chorId = enacter.createChoreography(chorSpec);
		Choreography chor = null;
		try {
			chor = enacter.enact(chorId);
		} catch (EnactmentException e) {
			System.out.println(Utils.getTimeStamp() + "Enactment #" + idx + " has failed");
			ok = false;
			return;
		}
		long tf = System.currentTimeMillis();
		duration = tf - t0;
		Service travelService = chor.getDeployedServiceByName(TRAVEL_AGENCY);
		travelWSDL = travelService.getUri() + "?wsdl";
		
		System.out.println(Utils.getTimeStamp() + "Choreography #" + idx + " enacted in " + duration + " miliseconds");
		StringBuilder chorMachinesMessage = new StringBuilder("Machines used by choreography #" + idx + ":");
		for (String mch: getMachinesFromChor(chor)) {
			chorMachinesMessage.append(mch + "; ");
		}
		System.out.println(Utils.getTimeStamp() + chorMachinesMessage.toString());
	}
	
	private List<String> getMachinesFromChor(Choreography chor) {
		
		List<String> machines = new ArrayList<String>();
		for (Service svc: chor.getDeployedServices()) {
			String machine = svc.getIp() + " (" + svc.getNodeId() + ")";
			machines.add(machine);
		}
		return machines;
	}
}
