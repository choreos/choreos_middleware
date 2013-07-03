package org.ow2.choreos.deployment.services.update;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServiceUpdater {

    private DeployableService service;
    private DeployableServiceSpec newServiceSpec;
    private String serviceUUID;
    private List<UpdateAction> actions;

    private Logger logger = Logger.getLogger(ServiceUpdater.class);

    public ServiceUpdater(DeployableService service, DeployableServiceSpec serviceSpec) {
        this.service = service;
        this.serviceUUID = service.getUUID();
        this.newServiceSpec = serviceSpec;
    }
    
    public DeployableService updateService() throws UnhandledModificationException {
        logger.info("Requested to update service " + serviceUUID + " with spec " + newServiceSpec);
        getActions();
        applyActions();
        logger.info("Service " + serviceUUID + " updated");
        return service;
    }
    
    private void getActions() {
        UpdateActionFactory fac = new UpdateActionFactory();
        actions = fac.getActions(service, newServiceSpec);
    }
    
    private void applyActions() {
        for (UpdateAction action : actions) {
            try {
                action.applyUpdate();
            } catch (UpdateActionFailedException e) {
                logger.error("Action '" + action + "' was not applied for service " + serviceUUID);
            }
        }
    }

//    private void applyUpdate(DeployableService currentService, DeployableServiceSpec requestedSpec,
//            List<UpdateAction> actions) throws UnhandledModificationException {
//        logger.info("Going to apply scheduled updates...");
//        for (UpdateAction a : actions) {
//            logger.info("Selected update " + a);
//            switch (a) {
//            case INCREASE_NUMBER_OF_REPLICAS:
//                logger.info("Requesting to increase amount of replicas");
//                requestToIncreaseNumberOfInstances(currentService, requestedSpec);
//                break;
//
//            case DECREASE_NUMBER_OF_REPLICAS:
//                logger.info("Requesting to decrease amount of replicas");
//                requestToDecreaseNumberOfInstances(currentService, requestedSpec);
//                break;
//
//            case MIGRATE:
//                logger.info("Requesting to migrate replicas");
//                requestToMigrateServiceInstances(currentService, requestedSpec);
//                break;
//
//            default:
//                logger.info("Cannot apply modification " + a + "; Raising UnhandledModificationException");
//                throw new UnhandledModificationException();
//            }
//        }
//        logger.info("Setting the new service spec for service " + currentService);
//
//        currentService.setSpec(requestedSpec);
//    }


//    private void requestToDecreaseNumberOfInstances(DeployableService currentService,
//            DeployableServiceSpec requestedSpec) {
//        int decreaseAmount = currentService.getSpec().getNumberOfInstances() - requestedSpec.getNumberOfInstances();
//        removeServiceInstances(currentService, decreaseAmount);
//    }
//
//    private void requestToMigrateServiceInstances(DeployableService currentService, ServiceSpec requestedSpec)
//            throws UnhandledModificationException {
//        currentService.setSpec(requestedSpec);
//        currentService.getServiceInstances().clear();
//        migrateServiceInstances(currentService);
//    }
//
//    private void migrateServiceInstances(DeployableService currentService) throws UnhandledModificationException {
//        try {
//            prepareDeployment(currentService.getSpec(), currentService.getUUID());
//        } catch (ServiceNotCreatedException e) {
//            throw new UnhandledModificationException();
//        }
//    }
//
//    private void removeServiceInstances(DeployableService currentService, int amount) {
//        if (amount < currentService.getServiceInstances().size()) {
//            for (int i = 0; i < amount; i++) {
//                executeServiceInstanceUndeployment(currentService.getServiceInstances().get(0));
//                currentService.getServiceInstances().remove(0);
//            }
//        } else if (amount < currentService.getServiceInstances().size()) {
//            try {
//                this.deleteService(currentService.getUUID());
//            } catch (ServiceNotDeletedException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void addServiceInstances(DeployableService current, int amount) {
//        logger.info("Requesting to execute creation of " + amount + " replicas for service " + current.getUUID());
//        try {
//            DeployableServiceSpec newSpec = current.getSpec();
//            newSpec.setNumberOfInstances(amount);
//            List<CloudNode> nodes = prepareDeployment(newSpec, current.getUUID());
//            current.getSelectedNodes().addAll(nodes);
//        } catch (ServiceNotCreatedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }    
    
}
