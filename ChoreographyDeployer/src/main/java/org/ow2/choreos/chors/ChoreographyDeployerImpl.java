/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.bus.ESBNodesSelector;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.ServiceInstancesProxifier;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.context.ContextSender;
import org.ow2.choreos.chors.context.ContextSenderFactory;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ChoreographyDeployerImpl implements ChoreographyDeployer {

    private static final String BUS_PROPERTY = "BUS";

    private ChorRegistry reg = ChorRegistry.getInstance();
    
    private Logger logger = Logger.getLogger(ChoreographyDeployerImpl.class);

    @Override
    public String createChoreography(ChoreographySpec chor) {
	String chorId = reg.create(chor);
	logger.info("Choreography " + chorId + " created.");
	return chorId;
    }

    @Override
    public Choreography getChoreography(String chorId) {
	Choreography chor = reg.get(chorId);
	return chor;
    }

    @Override
    public Choreography enactChoreography(String chorId) throws EnactmentException, ChoreographyNotFoundException {
	logger.info("Requested to enact choreography " + chorId);
	Choreography chor = reg.get(chorId);
	if (chor == null) {
	    logger.info("Could not get choreography with ID = " + chorId);
	    throw new ChoreographyNotFoundException(chorId);
	}
	if (chor.getChoreographySpec() == chor.getRequestedChoreographySpec())
	    logger.info("Starting enactment; chorId= " + chorId);
	else if (chor.getChoreographySpec() == chor.getRequestedChoreographySpec())
	    logger.info("Starting enactment for requested update; chorId= " + chorId);
	Deployer deployer = new Deployer();
	Map<String, ChoreographyService> deployedMap = deployer.deployChoreographyServices(chor);
	chor.setChoreographyServices(new ArrayList<ChoreographyService>(deployedMap.values()));
	logger.info("Deployed services=" + deployedMap);
	boolean useTheBus = Boolean.parseBoolean(ChoreographyDeployerConfiguration.get(BUS_PROPERTY));
	if (useTheBus) {
	    this.proxifyServices(chor);
	}
	ContextSender sender = ContextSenderFactory.getInstance(ServiceType.SOAP);
	ContextCaster caster = new ContextCaster(sender);
	logger.info("Resquested to cast service context");
	caster.cast(chor.getRequestedChoreographySpec(), deployedMap);
	logger.info("Going to set choreography " + chorId + " as deployed");
	chor.finishChoreographyEnactment();
	logger.info("Enactment completed; chorId=" + chorId);
	return chor;
    }

    @Override
    public void updateChoreography(String chorId, ChoreographySpec spec) throws ChoreographyNotFoundException {
	logger.info("Requested to update choreography " + chorId);
	Choreography chor = reg.get(chorId);

	if (chor == null) {
	    logger.info("Could not get choreography with ID = " + chorId);
	    throw new ChoreographyNotFoundException(chorId);
	}

	if (spec.equals(chor.getChoreographySpec())) {
	    logger.info("Requested to update choreography with the same spec that already have");
	    return;
	}
	logger.info("Starting update on choreography with ID " + chorId);
	logger.info("Updating choreography spec of choreography " + chorId);
	chor.setChoreographySpec(spec);
	logger.info("Choreography spec updated for choreography " + chorId);
	logger.info("Choreography " + chorId + " ready to be re-enacted");
	return;
    }

    private void proxifyServices(Choreography choreography) {
	logger.info("Resquested to proxify depoloyed services");
	ESBNodesSelector selector = new ESBNodesSelector();
	Map<ServiceInstance, EasyESBNode> instancesNodesMap = selector.selectESBNodes(choreography);
	ServiceInstancesProxifier proxifier = new ServiceInstancesProxifier();
	proxifier.proxify(instancesNodesMap);
	// TODO: should PUT /services/ (a registry would resolve...)
    }
}
