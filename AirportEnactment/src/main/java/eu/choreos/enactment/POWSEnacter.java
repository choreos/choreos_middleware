package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.ServiceDeployerClient;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;


public class POWSEnacter implements Enacter {

	@Override
	public void enact() {
		
		System.out.println("Enacting POWS...");
		
		ServicesRetriever retriever = new SimplePOWSRetriever();
		// ServicesRetriever retriever = new POWSRetriever();
		Set<ServiceSpec> specs = retriever.retrieve();
		
		ServiceDeployer deployer = new ServiceDeployerClient();
		for (ServiceSpec spec: specs) {
			System.out.println("Deploying JAR " + spec.getEndpointName()
					+ " from " + spec.getCodeUri());
			Service service = deployer.deploy(spec);
			System.out.println("Service deployed = " + service);
		}
	}

}
