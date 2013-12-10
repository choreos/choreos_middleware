package org.ow2.choreos.ee.services.update;

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

    /**
     * 
     * @param service
     *            may be changed by the updateService method
     * @param serviceSpec
     */
    public ServiceUpdater(DeployableService service, DeployableServiceSpec serviceSpec) {
        this.service = service;
        this.serviceUUID = service.getUUID();
        this.newServiceSpec = serviceSpec;
    }

    public void updateService() throws UnhandledModificationException {
        logger.info("Requested to update service " + serviceUUID + " with spec " + newServiceSpec);
        getActions();
        applyActions();
        logger.info("Service " + serviceUUID + " updated");
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

}
