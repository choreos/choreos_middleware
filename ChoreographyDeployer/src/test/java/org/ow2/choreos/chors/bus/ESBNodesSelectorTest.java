package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.bus.selector.ESBNodeSelector;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

import static org.mockito.Mockito.*;

public class ESBNodesSelectorTest {
    
    private static final String ESB_ADMIN_ENDPOINT = "http://localhost:8180/services/adminExternalEndpoint";
    
    private Choreography chor;
    private ServiceInstance airlineInstance, travelInstance;
    private EasyESBNode esbNode = new EasyESBNodeImpl(ESB_ADMIN_ENDPOINT);
    private ESBNodeSelector esbNodeSelectorMock;
    
    @Before
    public void setUp() throws Exception {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE); 
        chor = models.getChoreography();
        airlineInstance = models.getAirlineService().getInstances().get(0);
        travelInstance = models.getTravelService().getInstances().get(0);
        esbNodeSelectorMock = mock(ESBNodeSelector.class);
        List<EasyESBNode> esbNodes = Collections.singletonList(esbNode);
        when(esbNodeSelectorMock.select(any(ResourceImpact.class), anyInt())).thenReturn(esbNodes);
    }

    @Test
    public void shouldReturnProxificationTasksForEachServiceSpec() {
        ESBNodesSelector selector = new ESBNodesSelector(); 
        selector.setSelector(esbNodeSelectorMock);
        List<ProxificationTask> tasks = selector.selectESBNodes(chor);
        assertEquals(2, tasks.size());
        List<String> names = new ArrayList<String>();
        List<String> uris = new ArrayList<String>();
        for (ProxificationTask task : tasks) {
            names.add(task.getSvcName());
            uris.add(task.getNativeUri());
            assertEquals(ESB_ADMIN_ENDPOINT, task.getEasyEsbNode().getAdminEndpoint());
        }
        assertTrue(names.contains(airlineInstance.getServiceSpec().getName()));
        assertTrue(names.contains(travelInstance.getServiceSpec().getName()));
        assertTrue(uris.contains(airlineInstance.getNativeUri()));
        assertTrue(uris.contains(travelInstance.getNativeUri()));
    }

}
