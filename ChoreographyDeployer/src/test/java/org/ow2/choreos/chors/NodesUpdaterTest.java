package org.ow2.choreos.chors;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;


public class NodesUpdaterTest {

    private Choreography chor;

    @Before
    public void setUp() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        chor = models.getChoreography();
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 3);
    }

    @Test
    public void shouldUpdateAllNodes() throws Exception {

        NodePoolManager npmMock = mock(NodePoolManager.class);
        RESTClientsRetriever retrieverMock = mock(RESTClientsRetriever.class);
        when(retrieverMock.getNodesClient(anyString())).thenReturn(npmMock);

        List<DeployableService> services = chor.getDeployableServices();
        NodesUpdater updater = new NodesUpdater(services, chor.getId(), retrieverMock);
        updater.updateNodes();

        for (DeployableService svc: services) {
            String nodeId = svc.getSelectedNodes().get(0).getId();
            verify(npmMock).updateNode(nodeId);
        }
    }

}
