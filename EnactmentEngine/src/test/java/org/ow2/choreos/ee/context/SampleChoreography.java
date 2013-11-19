package org.ow2.choreos.ee.context;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

/**
 * To be used on ContextCasterTest
 * 
 * @author leonardo
 * 
 */
public class SampleChoreography {
    
    private static final int PORT = 9345;
    private static final String ROLE = "track";
    private static final String PACKAGE_URL = "http://wahatever/pack.tar.gz";
    private static final String VERSION = "1.0";

    private Choreography chor;
    private ChoreographySpec chorSpec;
    private CloudNode node;
    
    private AtomicInteger counter = new AtomicInteger(); 
    
    public SampleChoreography() {
        initSpec();
        initChor();
    }
    
    public Choreography getChoreography() {
        return chor;
    }
    
    public String getUri(String name) {
        return "http://" + node.getIp() + ":" + PORT + "/" + name;
    }
    
    public String getRole() {
        return ROLE;
    }

    private void initSpec() {
        chorSpec = new ChoreographySpec();
        initSpec1();
        initSpec2();
        initSpec3();
        initSpec4();
        initSpec5();
        initSpec6();
        initSpec7();
        initSpec8();
    }

    private DeployableServiceSpec initSpec(String name) {
        DeployableServiceSpec spec = new DeployableServiceSpec(name, ServiceType.SOAP, PackageType.COMMAND_LINE,
                new ResourceImpact(), VERSION, PACKAGE_URL, PORT, name, 1);
        spec.setRoles(Collections.singletonList(ROLE));
        return spec;
    }

    private void initSpec1() {
        DeployableServiceSpec spec = initSpec("spec1");
        ServiceDependency dep1 = new ServiceDependency("spec2", ROLE);
        ServiceDependency dep2 = new ServiceDependency("spec3", ROLE);
        ServiceDependency dep3 = new ServiceDependency("spec5", ROLE);
        spec.addDependency(dep1);
        spec.addDependency(dep2);
        spec.addDependency(dep3);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec2() {
        DeployableServiceSpec spec = initSpec("spec2");
        ServiceDependency dep = new ServiceDependency("spec4", ROLE);
        spec.addDependency(dep);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec3() {
        DeployableServiceSpec spec = initSpec("spec3");
        ServiceDependency dep = new ServiceDependency("spec4", ROLE);
        spec.addDependency(dep);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec4() {
        DeployableServiceSpec spec = initSpec("spec4");
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec5() {
        DeployableServiceSpec spec = initSpec("spec5");
        ServiceDependency dep1 = new ServiceDependency("spec6", ROLE);
        ServiceDependency dep2 = new ServiceDependency("spec7", ROLE);
        spec.addDependency(dep1);
        spec.addDependency(dep2);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec6() {
        DeployableServiceSpec spec = initSpec("spec6");
        ServiceDependency dep = new ServiceDependency("spec8", ROLE);
        spec.addDependency(dep);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec7() {
        DeployableServiceSpec spec = initSpec("spec7");
        ServiceDependency dep = new ServiceDependency("spec8", ROLE);
        spec.addDependency(dep);
        chorSpec.addServiceSpec(spec);
    }
    
    private void initSpec8() {
        DeployableServiceSpec spec = initSpec("spec8");
        chorSpec.addServiceSpec(spec);
    }
    
    private void initChor() {
        chor = new Choreography();
        chor.setId("1");
        chor.setChoreographySpec(chorSpec);
        initNode();
        initServices();
    }

    private void initNode() {
        node = new CloudNode();
        node.setIp("192.168.56.101");
    }
    
    private void initServices() {
        for (DeployableServiceSpec spec: chorSpec.getDeployableServiceSpecs()) {
            DeployableService svc = new DeployableService(spec);
            svc.setUUID(Integer.toString(counter.getAndIncrement()));
            svc.setSelectedNodes(Collections.singleton(node));
            ServiceInstance instance = new ServiceInstance(node);
            instance.setInstanceId(Integer.toString(counter.getAndIncrement()));
            instance.setNativeUri(getUri(spec.getName()));
            instance.setServiceSpec(spec);
            svc.addInstance(instance);
            chor.addService(svc);
        }
    }

}
