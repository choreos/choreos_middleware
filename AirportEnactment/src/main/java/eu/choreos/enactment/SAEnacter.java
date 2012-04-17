package eu.choreos.enactment;

import java.util.Set;

import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.ServiceDeployerClient;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class SAEnacter implements Enacter {

	@Override
	public void enact() {
		System.out.println("Enacting SAs...");

		ServicesRetriever cdRetriever = new CDRetriever();
		ServicesRetriever soapSARetriever = new SoapSARetriever();

		ServiceDeployer deployer = new ServiceDeployerClient();

		Set<ServiceSpec> cdSpecs = cdRetriever.retrieve();
		for (ServiceSpec spec: cdSpecs) {
			System.out.println("Deploying CD " + spec.getEndpointName()
					+ " from " + spec.getCodeUri());
			Service service = deployer.deploy(spec);
			System.out.println("Service deployed = " + service);
		}
		
		Set<ServiceSpec> soapSASpecs = soapSARetriever.retrieve();
		for (ServiceSpec spec: soapSASpecs) {
			System.out.println("Deploying SOAP SA " + spec.getEndpointName()
					+ " from " + spec.getCodeUri());
			Service service = deployer.deploy(spec);
			System.out.println("Service deployed = " + service);
		}
	}

}
