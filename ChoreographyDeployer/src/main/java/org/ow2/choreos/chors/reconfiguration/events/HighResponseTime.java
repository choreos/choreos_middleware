package org.ow2.choreos.chors.reconfiguration.events;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.reconfiguration.ComplexEventHandler;
import org.ow2.choreos.chors.reconfiguration.HandlingEvent;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class HighResponseTime extends ComplexEventHandler {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void handleEvent(HandlingEvent event) {

	String node = event.getNode();
	String service = event.getService();

	Choreography choreography = registryHelper.getChoreography(node, service);

	String serviceName = null;
	for (DeployableService s : choreography.getDeployableServices()) {
	    if (s.getUUID().equals(service))
		serviceName = s.getSpec().getName();
	}

	if (choreography == null || serviceName == null)
	    return;

	ChoreographySpec choreographySpec = choreography.getChoreographySpec();

	for (DeployableServiceSpec s : choreographySpec.getDeployableServiceSpecs()) {
	    if (s.getName().equals(service)) {
		logger.debug("Found service spec. Going to increase number of instances");
		s.setNumberOfInstances(s.getNumberOfInstances() + 1);
		break;
	    }
	}

	try {
	    logger.info("Going to update chor with spec: " + choreographySpec);
	    registryHelper.getChorClient().updateChoreography(choreography.getId(), choreographySpec);
	} catch (ChoreographyNotFoundException e) {
	    logger.error(e.getMessage());
	} catch (EnactmentException e) {
	    logger.error(e.getMessage());
	}

	try {
	    logger.info("Enacting choreography");
	    registryHelper.getChorClient().enactChoreography(choreography.getId());
	} catch (EnactmentException e) {
	    logger.error(e.getMessage());
	} catch (ChoreographyNotFoundException e) {
	    logger.error(e.getMessage());
	}

    }
}
