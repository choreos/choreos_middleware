package org.ow2.choreos;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;

public class Enacter {
	
	private static final String ENACTMENT_ENGINE_HOST = "http://localhost:9102/choreographydeployer";

	ChorSpec chorSpec; // input
	
	public Enacter(ChorSpec chorSpec) {
		this.chorSpec = chorSpec;
	}
	
	public void exec() {		
		ChoreographyDeployer enacter = new ChorDeployerClient(ENACTMENT_ENGINE_HOST);
		
		String chorId = enacter.createChoreography(chorSpec);
		
		@SuppressWarnings("unused")
		Choreography chor = null;
		try {
			chor = enacter.enact(chorId);
		} catch (EnactmentException e) {
			e.printStackTrace();
			System.out.println("EnactmentException happened");
			System.exit(1);
		} catch (ChoreographyNotFoundException e) {
			System.out.println("ChoreographyNotFoundException");
			System.exit(1);
		}
	}
	
	public static void main(String[] args){
		Enacter e = new Enacter(Spec.getSpec());
		e.exec();
	}
}
