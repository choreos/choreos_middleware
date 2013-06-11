package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.bus.ServicesProxifier;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.context.ContextSenderFactory;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ChoreographyEnacter {

    private Choreography chor;
    private Map<String, DeployableService> deployedMap;

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

    private Map<String, DeployableService> deploy() throws EnactmentException {
	Deployer deployer = new Deployer();
	deployedMap = deployer.deployServices(chor);
	chor.setDeployableServices(new ArrayList<DeployableService>(deployedMap.values()));
	return deployedMap;
    }

    private void proxifyServices(Choreography choreography) {
	ServicesProxifier proxifier = new ServicesProxifier(chor);
	proxifier.proxify();
    }
    
    private void castContext() {
	ContextSenderFactory factory = new ContextSenderFactory(); 
	ContextCaster caster = new ContextCaster(factory);
	caster.cast(chor.getRequestedChoreographySpec(), deployedMap);
    }

    private void logEnd() {
	logger.info("Enactment completed; chorId=" + chor.getId());
    }

}
