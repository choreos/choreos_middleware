package org.ow2.choreos;

import java.util.Collections;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

public class USPSpecs {

    ChoreographySpec spec;
    
    public ChoreographySpec getSpec() {
        if (spec == null)
            generateSpec();
        return spec;
    }
    
    private void generateSpec() {

        DeployableServiceSpec airlineSpec = new DeployableServiceSpec();
        airlineSpec.setEndpointName("airline");
        airlineSpec.setPort(1234);
        airlineSpec.setName("airline");
        airlineSpec.setPackageType(PackageType.COMMAND_LINE);
        airlineSpec.setServiceType(ServiceType.SOAP);
        airlineSpec.setPackageUri("http://valinhos.ime.usp.br:54080/services/airline-ptc13.jar");
        airlineSpec.setRoles(Collections.singletonList("airline"));
        
        DeployableServiceSpec travelSpec = new DeployableServiceSpec();
        travelSpec.setEndpointName("travelagency");
        travelSpec.setPort(1235);
        travelSpec.setName("travelagency");
        travelSpec.setPackageType(PackageType.COMMAND_LINE);
        travelSpec.setServiceType(ServiceType.SOAP);
        travelSpec.setPackageUri("http://valinhos.ime.usp.br:54080/enact_test/v2-2/travel-agency-service.jar");
        travelSpec.setRoles(Collections.singletonList("travelagency"));
        ServiceDependency dep = new ServiceDependency();
        dep.setServiceSpecName("airline");
        dep.setServiceSpecRole("airline");
        travelSpec.addDependency(dep);
        
        spec = new ChoreographySpec();
        spec.addServiceSpec(airlineSpec);
//        spec.addServiceSpec(travelSpec);
    }
    
}
