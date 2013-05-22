package eu.choreos.servicedeployer;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import eu.choreos.enactment.Configuration;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class ServiceDeployerClient implements ServiceDeployer {

	private String HOST;
	
	public ServiceDeployerClient() {
		HOST = Configuration.get("SERVICE_DEPLOYER");
	}

	private WebClient setupClient() {

		WebClient client = WebClient.create(HOST);
		
		// remove time out
		// not proud of it!
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		http.setClient(httpClientPolicy);
		
		return client;
	}
	
	@Override
	public Service deploy(ServiceSpec spec) {

		WebClient client = setupClient();
		client.path("services");   	
        Service service = client.post(spec, Service.class);
        
		if (client.getResponse().getStatus() == 201) {
	        service.setRole(spec.getRole());
	        return service;
		} else {
			System.out.println("Response from " + service.getName()
					+ " deployment: " + client.getResponse().getStatus());
			return null;
		}
	}

}
