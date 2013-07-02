package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;

public class DeployedServicesRetriever {
    
    private List<DeployableService> services;
    
    private Logger logger = Logger.getLogger(DeployedServicesRetriever.class);
    
    public DeployedServicesRetriever(List<DeployableService> services) {
        this.services = services;
    }

    public List<DeployableService> retrieveDeployedServicesFromDeploymentManager() {
        List<DeployableService> retrievedServices = new ArrayList<DeployableService>();
        for (DeployableService svc: services) {
            DeployableService retrievedService;
            try {
                retrievedService = retrieveService(svc);
                if (retrievedService.hasInstances())
                    retrievedServices.add(retrievedService);
                else
                    logger.warn("Service " + retrievedService.getUUID() + " has no instances");
            } catch (ServiceNotFoundException e) {
                logger.error("Service " + svc.getUUID() + " not found");
            }
        }
        return retrievedServices;
    }
    
    private DeployableService retrieveService (DeployableService service) throws ServiceNotFoundException {
        ServicesManager servicesManager = RESTClientsRetriever.getServicesClient(service.getSpec().getOwner());  
        return servicesManager.getService(service.getUUID());
    }
    
}
