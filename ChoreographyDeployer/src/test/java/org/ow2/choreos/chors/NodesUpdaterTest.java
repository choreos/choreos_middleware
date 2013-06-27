package org.ow2.choreos.chors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

;

public class NodesUpdaterTest {

    private Choreography chor, chorWithReplicas;

    @Before
    public void setUp() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        chor = models.getChoreography();
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 3);
        chorWithReplicas = models.getChoreography();
    }

    @Test
    public void shouldUpdateAllServices() throws EnactmentException {

        NodePoolManager npmMock = mock(NodePoolManager.class);
        RESTClientsRetriever retrieverMock = mock(RESTClientsRetriever.class);
        when(retrieverMock.getNodesClient(any(String.class))).thenReturn(npmMock);

        List<DeployableService> services = chor.getDeployableServices();
        NodesUpdater updater = new NodesUpdater(services, chor.getId(), retrieverMock);
        List<DeployableService> updatedServices = updater.updateNodes();

        assertEquals(services.size(), updatedServices.size());
        for (DeployableService service : services) {
            String serviceUUID = service.getSpec().getUuid();
            DeployableService updatedService = getUpdatedServiceByName(serviceUUID, updatedServices);
            assertEquals(service.getInstances().size(), updatedService.getInstances().size());
        }
    }

    @Test
    public void shouldUpdateAllServicesWithReplicas() throws EnactmentException {

        NodePoolManager npmMock = mock(NodePoolManager.class);
        RESTClientsRetriever retrieverMock = mock(RESTClientsRetriever.class);
        when(retrieverMock.getNodesClient(any(String.class))).thenReturn(npmMock);

        List<DeployableService> services = chorWithReplicas.getDeployableServices();
        NodesUpdater updater = new NodesUpdater(services, chorWithReplicas.getId(), retrieverMock);
        List<DeployableService> updatedServices = updater.updateNodes();

        assertEquals(services.size(), updatedServices.size());
        for (DeployableService service : services) {
            String serviceUUID = service.getSpec().getUuid();
            DeployableService updatedService = getUpdatedServiceByName(serviceUUID, updatedServices);
            assertEquals(service.getInstances().size(), updatedService.getInstances().size());
        }
    }

    @Test
    public void shouldUpdateNoService() throws EnactmentException, NodeNotUpdatedException, NodeNotFoundException {

        NodePoolManager badNPMMock = mock(NodePoolManager.class);
        doThrow(new NodeNotUpdatedException("whathever")).when(badNPMMock).updateNode(any(String.class));
        RESTClientsRetriever retrieverMock = mock(RESTClientsRetriever.class);
        when(retrieverMock.getNodesClient(any(String.class))).thenReturn(badNPMMock);

        List<DeployableService> services = chor.getDeployableServices();
        NodesUpdater updater = new NodesUpdater(services, chor.getId(), retrieverMock);
        List<DeployableService> updatedServices = updater.updateNodes();

        assertTrue(updatedServices.isEmpty());
    }

    private DeployableService getUpdatedServiceByName(String uuid, List<DeployableService> updatedServices) {
        for (DeployableService updated : updatedServices) {
            String updatedUUID = updated.getSpec().getUuid();
            if (updatedUUID.equals(uuid))
                return updated;
        }
        throw new NoSuchElementException();
    }

}
