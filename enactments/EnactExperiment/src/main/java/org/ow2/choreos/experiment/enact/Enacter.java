package org.ow2.choreos.experiment.enact;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Service;

public class Enacter implements Runnable {
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/choreographydeployer";
	private static final String TRAVEL_AGENCY = "travelagency";	

	ChoreographySpec ChoreographySpec; // input
	int idx; // input
	String travelWSDL = null; // result: deployed travel agency service WSDL
	long duration; // result: enactment duration in milliseconds
	boolean ok = true;
	Report report;
	
	public Enacter(ChoreographySpec ChoreographySpec, int idx, Report report) {
		this.ChoreographySpec = ChoreographySpec;
		this.idx = idx;
		this.report = report;
	}
	
	@Override
	public void run() {
		
		System.out.println(Utils.getTimeStamp() + "Enacting choreography #" + idx);
		
		long t0 = System.currentTimeMillis();
		ChoreographyDeployer enacter = new ChorDeployerClient(ENACTMENT_ENGINE_HOST);
		String chorId = enacter.createChoreography(ChoreographySpec);
		Choreography chor = null;
		try {
			chor = enacter.enactChoreography(chorId);
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
		Service travelService = chor.getDeployableServiceBySpecName(TRAVEL_AGENCY);
		
		travelWSDL = travelService.getUris().get(0) + "?wsdl";
		
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
		for (DeployableService svc: chor.getDeployableServices()) {
			String nodeId = svc.getInstances().get(0).getNode().getId();
			String nodeIp = svc.getInstances().get(0).getNode().getIp();
			String machine = nodeIp + " (" + nodeId + ")";
			machines.add(machine);
		}
		return machines;
	}
}
