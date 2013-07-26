package org.ow2.choreos.chors;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.bus.ServicesProxifier;
import org.ow2.choreos.chors.bus.TopologyConfigurator;
import org.ow2.choreos.chors.bus.TopologyNotConfigureException;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ChoreographyEnacter {
    
    private static final String BUS_PROPERTY = "BUS";

    private Choreography chor;

    private Logger logger = Logger.getLogger(ChoreographyEnacter.class);

    public ChoreographyEnacter(Choreography chor) {
        this.chor = chor;
    }

    public Choreography enact() throws EnactmentException {
        logBegin();
        deploy();
        createLegacyServices();
        if (useTheBus()) {
            proxifyServices();
            configureESBTopology();
        }
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

    private void createLegacyServices() {
        LegacyServicesCreator legacyServicesCreator = new LegacyServicesCreator();
        List<LegacyService> legacyServices = legacyServicesCreator.createLegacyServices(chor.getChoreographySpec());
        chor.setLegacyServices(legacyServices);
    }
    
    private boolean useTheBus() {
        return Boolean.parseBoolean(ChoreographyDeployerConfiguration.get(BUS_PROPERTY));
    }

    private void proxifyServices() {
        ServicesProxifier proxifier = new ServicesProxifier(chor);
        proxifier.proxify();
    }
    
    private void configureESBTopology() {
        TopologyConfigurator topologyConfigurator = new TopologyConfigurator(chor);
        try {
            topologyConfigurator.configureTopology();
        } catch (TopologyNotConfigureException e) {
            logger.error("ESB topology was not properly configured");
        }
    }

    private void castContext() {
        ContextCaster caster = new ContextCaster();
        caster.cast(chor);
    }

    private void logEnd() {
        logger.info("Enactment completed; chorId=" + chor.getId());
    }

}
