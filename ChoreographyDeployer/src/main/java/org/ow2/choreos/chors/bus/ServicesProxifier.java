package org.ow2.choreos.chors.bus;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class ServicesProxifier {

    private Choreography chor;

    private Logger logger = Logger.getLogger(ServicesProxifier.class);

    public ServicesProxifier(Choreography chor) {
        this.chor = chor;
    }

    public void proxify() {
        logger.info("Resquested to proxify depoloyed services");
        ESBNodesSelector selector = new ESBNodesSelector();
        Map<ServiceInstance, EasyESBNode> instancesNodesMap = selector.selectESBNodes(chor);
        ServiceInstancesProxifier proxifier = new ServiceInstancesProxifier();
        proxifier.proxify(instancesNodesMap);
        // TODO: should PUT /services/ (a registry would resolve...)
    }

}
