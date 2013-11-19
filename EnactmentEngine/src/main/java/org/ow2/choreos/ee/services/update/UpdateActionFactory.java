package org.ow2.choreos.ee.services.update;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class UpdateActionFactory {

    private Logger logger = Logger.getLogger(UpdateActionFactory.class);

    public List<UpdateAction> getActions(DeployableService currentService, DeployableServiceSpec newServiceSpec) {

        DeployableServiceSpec currentServiceSpec = currentService.getSpec();
        List<UpdateAction> actions = new ArrayList<UpdateAction>();

        logger.info("Calculating changes on service spec \nOld = " + currentServiceSpec + "\nNew = " + newServiceSpec);
        if (currentServiceSpec.getNumberOfInstances() < newServiceSpec.getNumberOfInstances()) {
            UpdateAction action = new IncreaseNumberOfReplicas(currentService, newServiceSpec);
            actions.add(action);
        } else if (currentServiceSpec.getNumberOfInstances() > newServiceSpec.getNumberOfInstances()) {
            UpdateAction action = new DecreaseNumberOfReplicas(currentService, newServiceSpec);
            actions.add(action);
        }

        if (!(currentServiceSpec.getResourceImpact() == null || currentServiceSpec.getResourceImpact().getMemory() == null)) {
            if (!(currentServiceSpec.getResourceImpact().getMemory().ordinal() == newServiceSpec.getResourceImpact()
                    .getMemory().ordinal())) {
                UpdateAction action = new Migrate(currentService, newServiceSpec);
                actions.add(action);
            }
        }

        logger.info("Detected changes: " + actions);
        return actions;

    }

}
