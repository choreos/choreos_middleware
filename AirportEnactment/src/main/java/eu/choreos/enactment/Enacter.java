package eu.choreos.enactment;

import java.util.HashSet;
import java.util.Set;

import eu.choreos.enactment.spec.SpecBuilder;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class Enacter {

	private String type;
	private SpecBuilder[] specBuilders;
	
	public Enacter(String type, SpecBuilder[] specBuilders) {
		this.type = type;
		this.specBuilders = specBuilders;
	}
	
	public Set<Service> enact() {
		
		System.out.println("Enacting " + type + "s...");

		Set<Service>	deployed = new HashSet<Service>();
		
		SpecRetriever retriever = new SpecRetriever(specBuilders);
		Set<ServiceSpec> specs = retriever.retrieve();
		
//		ServiceDeployer deployer = new ServiceDeployerClient();
		for (ServiceSpec spec: specs) {
			System.out.println("Deploying " + type + " " + spec.getRole()
					+ " from " + spec.getCodeUri());
//			Service service = deployer.deploy(spec);
//			System.out.println("Service deployed = " + service);
			Service service = new Service(spec);
			service.setHost("localhost"); // fake
			deployed.add(service);
			System.out.println(service.getId() + " deployed at " + service.getUri());
		}

		return deployed;
	}

}
