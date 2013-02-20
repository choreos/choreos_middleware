package org.ow2.choreos.experiment.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.Service;

public class Enacter implements Runnable {
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/choreographydeployer";
	private static final String TRAVEL_AGENCY = "travelagency";	

	ChorSpec chorSpec; // input
	int idx; // input
	String travelWSDL = null; // result: deployed travel agency service WSDL
	long duration; // result: enactment duration in milliseconds
	boolean ok = true;
	Report report;
	
	public Enacter(ChorSpec chorSpec, int idx, Report report) {
		this.chorSpec = chorSpec;
		this.idx = idx;
		this.report = report;
	}
	
	@Override
	public void run() {
		
		System.out.println(Utils.getTimeStamp() + "Enacting choreography #" + idx);
		
		long t0 = System.currentTimeMillis();
		ChoreographyDeployer enacter = new ChorDeployerClient(ENACTMENT_ENGINE_HOST);
		String chorId = enacter.createChoreography(chorSpec);
		Choreography chor = null;
		try {
			chor = enacter.enact(chorId);
		} catch (EnactmentException e) {
			System.out.println(Utils.getTimeStamp() + "Enactment #" + idx + " has failed (chorId=" + chorId +")");
			ok = false;
			return;
		} catch (ChoreographyNotFoundException e) {
			System.out.println(Utils.getTimeStamp() + "Enactment #" + idx + " has failed (chorId=" + chorId +")");
			ok = false;
			return;
		}
		long tf = System.currentTimeMillis();
		duration = tf - t0;
		Service travelService = chor.getDeployedServiceByName(TRAVEL_AGENCY);
		travelWSDL = travelService.getNativeUri() + "?wsdl";
		
		System.out.println(Utils.getTimeStamp() + "Choreography #" + idx + " enacted in " + duration + " miliseconds");
		report.addChorEnactmentTime(duration);
		StringBuilder chorMachinesMessage = new StringBuilder("Machines used by choreography  #" + idx + " (chorId=" + chorId +"):");
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
