package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class DeployedServicesRetriever {
    
    private List<DeployableServiceSpec> specs;
    
    private Logger logger = Logger.getLogger(DeployedServicesRetriever.class);
    
    public DeployedServicesRetriever(List<DeployableServiceSpec> specs) {
        this.specs = specs;
    }

    public List<DeployableService> retrieveDeployedServicesFromDeploymentManager() {
        List<DeployableService> deployedServices = new ArrayList<DeployableService>();
        for (DeployableServiceSpec spec: specs) {
            DeployableService service;
            try {
                service = retrieveService(spec);
                if (service.hasInstances())
                    deployedServices.add(service);
                else
                    logger.warn("Service " + spec.getUuid() + " has no instances");
            } catch (ServiceNotFoundException e) {
                logger.error("Service " + spec.getUuid() + " not found");
            }
        }
        return deployedServices;
    }
    
    private DeployableService retrieveService (DeployableServiceSpec spec) throws ServiceNotFoundException {
        ServicesManager servicesManager = RESTClientsRetriever.getServicesClient(spec.getOwner());  
        return servicesManager.getService(spec.getUuid());
    }
    
}
