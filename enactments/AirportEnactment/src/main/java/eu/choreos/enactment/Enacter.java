package eu.choreos.enactment;

import java.util.HashSet;
import java.util.Set;

import eu.choreos.enactment.spec.SpecBuilder;
import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.ServiceDeployerClient;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class Enacter {

	private String type;
	private SpecBuilder[] specBuilders;
	private Set<Service> deployed = new HashSet<Service>();
	
	public Enacter(String type, SpecBuilder[] specBuilders) {
		this.type = type;
		this.specBuilders = specBuilders;
	}
	
	public Set<Service> enact() {
		
		System.out.println("Enacting " + type + "s...");

		SpecRetriever retriever = new SpecRetriever(specBuilders);
		Set<ServiceSpec> specs = retriever.retrieve();

		// each threads deploy one service
		Thread[] trds = new Thread[specs.size()];
		
		int i = 0;
		for (ServiceSpec spec: specs) {
			trds[i] = new Thread(new SpecEnacter(spec));
			trds[i].start();
			i++;
		}
		
		// wait for all threads finish
		for (Thread t: trds) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return deployed;
	}
	
	private class SpecEnacter implements Runnable {

		private ServiceSpec spec;
		public SpecEnacter(ServiceSpec spec) {
			this.spec = spec;
		}
		
		@Override
		public void run() {
			System.out.println("Deploying " + type + " " + spec.getRole()
					+ " from " + spec.getCodeUri());
			ServiceDeployer deployer = new ServiceDeployerClient();
			Service service = deployer.deploy(spec);
			if (service != null) {
				System.out.println(service.getId() + " deployed at " + service.getUri());			
				deployed.add(service);
			}
		}
		
	}

}
