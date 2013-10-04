package org.ow2.choreos.chors.reconfiguration.events;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.chors.reconfiguration.ComplexEventHandler;
import org.ow2.choreos.chors.reconfiguration.HandlingEvent;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class LowCpuUserEvent extends ComplexEventHandler {

    Logger logger = Logger.getLogger(this.getClass());

    @Override
    public void handleEvent(HandlingEvent event) {
	List<DeployableService> services = registryHelper.getServicesHostedOn(event.getNode());

	if (services.isEmpty()) {
	    logger.warn("Ooops! No servives hosted on " + event.getNode());
	    return;
	}

	List<DeployableServiceSpec> serviceSpecs = registryHelper.getServiceSpecsForServices(services);

	for (DeployableServiceSpec spec : serviceSpecs) {

	    Choreography c = registryHelper.getChor(event.getNode());
	    ChoreographySpec cSpec = c.getChoreographySpec();
	    for (DeployableServiceSpec s : cSpec.getDeployableServiceSpecs()) {
		if (s.getName().equals(spec.getName())) {
		    logger.debug("Found service spec. Going to increase number of instances");
		    if (s.getNumberOfInstances() > 1)
			s.setNumberOfInstances(s.getNumberOfInstances() - 1);
		    break;
		}
	    }

	    try {
		logger.info("Going to update chor with spec: " + cSpec);
		registryHelper.getChorClient().updateChoreography("1", cSpec);
	    } catch (ChoreographyNotFoundException e) {
		logger.error(e.getMessage());
	    } catch (EnactmentException e) {
		logger.error(e.getMessage());
	    }

	    try {
		logger.info("Enacting choreography");
		registryHelper.getChorClient().enactChoreography("1");
	    } catch (EnactmentException e) {
		logger.error(e.getMessage());
	    } catch (ChoreographyNotFoundException e) {
		logger.error(e.getMessage());
	    }

	}
    }

}
