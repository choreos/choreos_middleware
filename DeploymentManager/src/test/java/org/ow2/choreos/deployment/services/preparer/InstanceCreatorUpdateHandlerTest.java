package org.ow2.choreos.deployment.services.preparer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.Concurrency;

public class InstanceCreatorUpdateHandlerTest {

    private DeployableService service; 
    private CloudNode node;
    private String serviceId; 
    private DeployableServiceSpec spec;
    
    @Before
    public void setUp() throws Exception {
        setNode();
        setService();
    }

    private void setNode() {
        node = new CloudNode();
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }

    private void setService() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        spec = models.getAirlineSpec();
        service = new DeployableService();
        service.setSpec(spec);
        serviceId = spec.getUuid();
        DeployedServicesRegistry.getInstance().addService(serviceId, service);
    }
    

    @Test
    public void test() {
        int N = 3;
        List<Thread> trds = new ArrayList<Thread>();
        for (int i=0; i<N; i++) {
            String instanceId = Integer.toString(i);
            InstanceCreatorUpdateHandler handler = new InstanceCreatorUpdateHandler(serviceId, instanceId, spec, node);
            HandlerTask task = new HandlerTask(handler);
            Thread t = new Thread(task);
            trds.add(t);
            t.start();
        }
        Concurrency.waitThreads(trds);
        assertEquals(N, service.getServiceInstances().size());
        for (ServiceInstance instance: service.getServiceInstances()) {
            assertEquals(node, instance.getNode());
            assertEquals(spec, instance.getServiceSpec());
        }
    }

    private class HandlerTask implements Runnable {

        InstanceCreatorUpdateHandler handler;
        
        public HandlerTask(InstanceCreatorUpdateHandler handler) {
            this.handler = handler;
        }

        @Override
        public void run() {
            handler.handle();
        }
        
    }
}
