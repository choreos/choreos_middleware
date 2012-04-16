package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;


public class POWSEnacter implements Enacter {

	@Override
	public void enact() {
		
		System.out.println("Enacting POWS...");
		
		ServicesRetriever retriever = new POWSRetriever();
		Set<ServiceSpec> specs = retriever.retrieve();
		
		Deployer deployer = new JarDeployer();
		for (ServiceSpec spec: specs) {
			deployer.deploy(spec);
		}
	}

}
