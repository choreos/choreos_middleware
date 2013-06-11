package org.ow2.choreos.chors;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.bus.ServicesProxifier;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.context.ContextSenderFactory;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ChoreographyEnacter {

    private Choreography chor;

    private Logger logger = Logger.getLogger(ChoreographyEnacter.class);

    public ChoreographyEnacter(Choreography chor) {
	this.chor = chor;
    }

    public Choreography enact() throws EnactmentException {
	logBegin();
	deploy();
	proxifyServices(chor);
	castContext();
	chor.enactmentFinished();
	logEnd();
	return chor;
    }

    private void logBegin() {
	if (chor.getChoreographySpec() == chor.getRequestedChoreographySpec())
	    logger.info("Starting enactment; chorId= " + chor.getId());
	else if (chor.getChoreographySpec() == chor.getRequestedChoreographySpec())
	    logger.info("Starting enactment for requested update; chorId= " + chor.getId());
    }

    private void deploy() throws EnactmentException {
	ServicesDeployer deployer = new ServicesDeployer(chor);
	List<DeployableService> deployedServices = deployer.deployServices();
	chor.setDeployableServices(deployedServices);
    }

    private void proxifyServices(Choreography choreography) {
	ServicesProxifier proxifier = new ServicesProxifier(chor);
	proxifier.proxify();
    }
    
    private void castContext() {
	ContextSenderFactory factory = new ContextSenderFactory(); 
	ContextCaster caster = new ContextCaster(factory);
	caster.cast(chor);
    }

    private void logEnd() {
	logger.info("Enactment completed; chorId=" + chor.getId());
    }

}
