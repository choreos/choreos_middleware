package org.ow2.choreos.ee.bus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceInstance;
import org.ow2.choreos.chors.datamodel.LegacyServiceSpec;
import org.ow2.choreos.ee.bus.ESBNodesSelector;
import org.ow2.choreos.ee.bus.EasyESBNode;
import org.ow2.choreos.ee.bus.EasyESBNodeImpl;
import org.ow2.choreos.ee.bus.ProxificationTask;
import org.ow2.choreos.ee.bus.selector.ESBNodeSelector;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class ESBNodesSelectorTest {
    
    private static final String ESB_ADMIN_ENDPOINT = "http://localhost:8180/services/adminExternalEndpoint";
    private static final String BANK_ENDPOINT = "http://bank.com/ws";
    
    private Choreography chor;
    private ServiceInstance airlineInstance, travelInstance;
    private LegacyServiceInstance bankInstance;
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
        verifyDeployableServicesProxification(tasks);
    }
    
    private void verifyDeployableServicesProxification(List<ProxificationTask> tasks) {
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
    
    @Test
    public void shouldReturnProxificationTasksForEachServiceSpecIncludingLegacyServices() {
        addLegacyServiceToChorSpec();
        ESBNodesSelector selector = new ESBNodesSelector(); 
        selector.setSelector(esbNodeSelectorMock);
        List<ProxificationTask> tasks = selector.selectESBNodes(chor);
        assertEquals(3, tasks.size());
        verifyDeployableServicesProxification(tasks);
        verifyLegacyServicesProxification(tasks);
    }

    private void addLegacyServiceToChorSpec() {
        LegacyServiceSpec bankSpec = new LegacyServiceSpec();
        List<String> bankUris = Collections.singletonList(BANK_ENDPOINT);
        bankSpec.setName("bank");
        bankSpec.setRoles(Collections.singletonList("bank"));
        bankSpec.setServiceType(ServiceType.SOAP);
        bankSpec.setNativeURIs(bankUris);
        LegacyService bankService = new LegacyService(bankSpec);
        chor.getChoreographySpec().addServiceSpec(bankSpec);
        chor.addService(bankService);
        bankInstance = bankService.getLegacyServiceInstances().get(0);
    }

    private void verifyLegacyServicesProxification(List<ProxificationTask> tasks) {
        List<String> names = new ArrayList<String>();
        List<String> uris = new ArrayList<String>();
        for (ProxificationTask task : tasks) {
            names.add(task.getSvcName());
            uris.add(task.getNativeUri());
        }
        assertTrue(names.contains(bankInstance.getSpec().getName()));
        assertTrue(uris.contains(bankInstance.getUri()));
    }
}
