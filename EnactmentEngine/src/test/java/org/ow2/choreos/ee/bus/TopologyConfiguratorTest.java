package org.ow2.choreos.ee.bus;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.ee.bus.ESBRegister;
import org.ow2.choreos.ee.bus.EasyESBException;
import org.ow2.choreos.ee.bus.EasyESBNode;
import org.ow2.choreos.ee.bus.TopologyConfigurator;
import org.ow2.choreos.ee.bus.TopologyNotConfigureException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class TopologyConfiguratorTest {
    
    private static final String HOST1 = "host1";
    private static final String HOST2 = "host2";
    private static final String HOST3 = "host3";
    private static final String HOST4 = "host4";
    
    private Choreography chor;
    private EasyESBNode esbMock1, esbMock2, esbMock3, esbMock4;

    @Before
    public void setUp() throws Exception {
        setUpChoreography();
        setUpESBNodes();
    }
    
    /**
     * A --> B <-- C --> D
     */
    private void setUpChoreography() {
        
        chor = new Choreography();
        
        DeployableServiceSpec specA = new DeployableServiceSpec();
        specA.setName("serviceA");
        specA.setRoles(Collections.singletonList("A"));
        specA.addDependency(new ServiceDependency("serviceB", "B"));
        
        DeployableService sA = new DeployableService();
        sA.setSpec(specA);
        ServiceInstance siA1 = new ServiceInstance();
        Proxification prox = new Proxification(); 
        prox.setEasyEsbNodeAdminEndpoint(getAdminEndpoint(HOST1));
        siA1.setProxification(prox);
        sA.addInstance(siA1);
        ServiceInstance siA2 = new ServiceInstance();
        prox = new Proxification(); 
        prox.setEasyEsbNodeAdminEndpoint(getAdminEndpoint(HOST1));        
        siA2.setProxification(prox);
        sA.addInstance(siA2);
        chor.addService(sA);
        
        DeployableServiceSpec specB = new DeployableServiceSpec();
        specB.setName("serviceB");
        specB.setRoles(Collections.singletonList("B"));

        DeployableService sB = new DeployableService();
        sB.setSpec(specB);
        ServiceInstance siB = new ServiceInstance();
        prox = new Proxification(); 
        prox.setEasyEsbNodeAdminEndpoint(getAdminEndpoint(HOST2));        
        siB.setProxification(prox);
        sB.addInstance(siB);
        chor.addService(sB);
        
        DeployableServiceSpec specC = new DeployableServiceSpec();
        specC.setName("serviceC");
        specC.setRoles(Collections.singletonList("C"));
        specC.addDependency(new ServiceDependency("serviceB", "B"));
        specC.addDependency(new ServiceDependency("serviceD", "D"));
        
        DeployableService sC = new DeployableService();
        sC.setSpec(specC);
        ServiceInstance siC = new ServiceInstance();
        prox = new Proxification(); 
        prox.setEasyEsbNodeAdminEndpoint(getAdminEndpoint(HOST3));                
        siC.setProxification(prox);
        sC.addInstance(siC);
        chor.addService(sC);
        
        DeployableServiceSpec specD = new DeployableServiceSpec();
        specD.setName("serviceD");
        specD.setRoles(Collections.singletonList("D"));
        
        DeployableService sD = new DeployableService();
        sD.setSpec(specD);
        ServiceInstance siD = new ServiceInstance();
        prox = new Proxification(); 
        prox.setEasyEsbNodeAdminEndpoint(getAdminEndpoint(HOST4));                
        siD.setProxification(prox);
        sD.addInstance(siD);
        chor.addService(sD);
    }
    
    private String getAdminEndpoint(String host) {
        return "http://" + host + ":8180/services/adminExternalEndpoint";
    }
    
    private void setUpESBNodes() {
        
        esbMock1 = mock(EasyESBNode.class);
        when(esbMock1.getAdminEndpoint()).thenReturn(getAdminEndpoint(HOST1));
        ESBRegister.addEsbNode(esbMock1);
        
        esbMock2 = mock(EasyESBNode.class);
        when(esbMock2.getAdminEndpoint()).thenReturn(getAdminEndpoint(HOST2));
        ESBRegister.addEsbNode(esbMock2);
        
        esbMock3 = mock(EasyESBNode.class);
        when(esbMock3.getAdminEndpoint()).thenReturn(getAdminEndpoint(HOST3));
        ESBRegister.addEsbNode(esbMock3);
        
        esbMock4 = mock(EasyESBNode.class);
        when(esbMock4.getAdminEndpoint()).thenReturn(getAdminEndpoint(HOST4));
        ESBRegister.addEsbNode(esbMock4);
    }
    
    @After
    public void clearESBRegister() {
        ESBRegister.clear();
    }

    @Test
    public void test() throws TopologyNotConfigureException, EasyESBException {

        TopologyConfigurator topologyConfigurator = new TopologyConfigurator(chor);
        topologyConfigurator.configureTopology();
        
        verify(esbMock1, times(2)).addNeighbour(esbMock2);
        verify(esbMock2, times(2)).addNeighbour(esbMock1);
        
        verify(esbMock2).addNeighbour(esbMock3);
        verify(esbMock3).addNeighbour(esbMock2);
        
        verify(esbMock3).addNeighbour(esbMock4);
        verify(esbMock4).addNeighbour(esbMock3);
    }

}
