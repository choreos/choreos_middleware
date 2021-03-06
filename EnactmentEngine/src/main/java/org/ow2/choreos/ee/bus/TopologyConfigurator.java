package org.ow2.choreos.ee.bus;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class TopologyConfigurator {

    private Choreography chor;

    private Logger logger = Logger.getLogger(TopologyConfigurator.class);

    /**
     * Configure topology (add neighbors) among deployed EasyESB nodes. 
     * Note: This action is not related with EasyESB nodes used to run CDs.
     * 
     * @param chor
     *            Choreography whose service instances already have the
     *            easyEsbNodeAdminEndpoint property
     */
    public TopologyConfigurator(Choreography chor) {
        this.chor = chor;
    }

    /**
     * Configures a topology of EasyESB nodes according to the services
     * dependencies within a choreography. The topology is configured by
     * successive invocations to the addNeighbour operation on esb nodes.
     * 
     * @throws TopologyNotConfigureException
     */
    public void configureTopology() throws TopologyNotConfigureException {
        for (DeployableService service : chor.getDeployableServices()) {
            for (ServiceInstance instance : service.getInstances()) {
                EasyESBNode esbNode = getESBNode(instance);
                List<ServiceDependency> dependencies = service.getSpec().getDependencies();
                if (dependencies != null) {
                    for (ServiceDependency dependency : dependencies) {
                        DeployableService providingService = chor.getDeployableServiceBySpecName(dependency
                                .getServiceSpecName());
                        for (ServiceInstance providingInstance : providingService.getInstances()) {
                            EasyESBNode neighbourEsbNode = getESBNode(providingInstance);
                            try {
                                esbNode.addNeighbour(neighbourEsbNode);
                                neighbourEsbNode.addNeighbour(esbNode);
                            } catch (EasyESBException e) {
                                throw new TopologyNotConfigureException();
                            }
                        }
                    }
                }
            }
        }
    }

    private EasyESBNode getESBNode(ServiceInstance instance) {
        String adminEndpoint = instance.getProxification().getEasyEsbNodeAdminEndpoint();
        EasyESBNode esbNode = ESBRegister.getEsbNode(adminEndpoint);
        if (esbNode == null) {
            logger.error("Could not retrieve EasyESBNode " + adminEndpoint + " from ESB Register");
            throw new IllegalStateException();
        }
        return esbNode;
    }

}
