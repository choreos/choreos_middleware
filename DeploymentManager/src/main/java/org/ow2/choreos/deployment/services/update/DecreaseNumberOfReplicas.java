package org.ow2.choreos.deployment.services.update;

import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class DecreaseNumberOfReplicas extends BaseAction {

    private static final String NAME = "Decrease number of instances";
    
    private DeployableService currentService;
    private DeployableServiceSpec newSpec;
    
    public DecreaseNumberOfReplicas(DeployableService currentService, DeployableServiceSpec newSpec) {
        this.currentService = currentService;
        this.newSpec = newSpec;
    }
    
    @Override
    public void applyUpdate() {
        // TODO Auto-generated method stub

    }

    @Override
    public String getName() {
        return NAME;
    }

}
