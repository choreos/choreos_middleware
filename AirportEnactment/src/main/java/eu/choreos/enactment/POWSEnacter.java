package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.enactment.context.ChoreographyContext;
import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.ServiceDeployerClient;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;


public class POWSEnacter implements Enacter {

	@Override
	public void enact(ChoreographyContext context) {
		
		System.out.println("Enacting POWS...");
		
		SpecBuilder[] builders = {new POWSSpecBuilder()};
		SpecRetriever retriever = new SpecRetriever(builders);
		Set<ServiceSpec> specs = retriever.retrieve();
		
		ServiceDeployer deployer = new ServiceDeployerClient();
		for (ServiceSpec spec: specs) {
			System.out.println("Deploying JAR " + spec.getEndpointName()
					+ " from " + spec.getCodeUri());
//			Service service = deployer.deploy(spec);
//			System.out.println("Service deployed = " + service);
			context.addService(spec.getCodeUri(), 
					spec.getEndpointName());
		}
	}

}
