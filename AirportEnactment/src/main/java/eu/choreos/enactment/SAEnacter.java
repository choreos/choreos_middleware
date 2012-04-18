package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.ServiceDeployerClient;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SAEnacter implements Enacter {

	private SpecBuilder cdSpecBuilder = new CDSpecBuilder();
	private SpecBuilder saConsumeSpecBuilder = new SAConsumeSpecBuilder();
	private SpecBuilder saProvideSpecBuilder = new SAProvideSpecBuilder();
	private SpecBuilder[] specBuilders = { cdSpecBuilder, saConsumeSpecBuilder,
			saProvideSpecBuilder };


	@Override
	public void enact() {
	
		System.out.println("Enacting SAs...");

		SpecRetriever retriever = new SpecRetriever(specBuilders);
		Set<ServiceSpec> specs = retriever.retrieve();

		ServiceDeployer deployer = new ServiceDeployerClient();

		for (ServiceSpec spec: specs) {
			System.out.println("Deploying " + spec.getEndpointName()
					+ " from " + spec.getCodeUri());
//			Service service = deployer.deploy(spec);
//			System.out.println("Service deployed = " + service);
		}
	}

}
