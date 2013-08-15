package org.ow2.choreos.tracker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ChorSpecCreator {

    private static String warFile;
    private static final String ENDPOINT = "endpoint";
    private static final int INSTANCES = 1;

    private transient List<DeployableServiceSpec> chorServiceSpecs;

    public static void setWarFile(final String uri) {
        warFile = uri;
    }

    public ChoreographySpec create(final int chorSize) {
        this.chorServiceSpecs = createServiceSpecs(chorSize);
        setDependencies();
        return createChorSpec();
    }

    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    private List<DeployableServiceSpec> createServiceSpecs(final int chorSize) {
        final List<DeployableServiceSpec> serviceSpecs = new ArrayList<DeployableServiceSpec>(chorSize);
        final TrackerInfo trackerInfo = new TrackerInfo();
        for (int i = 0; i < chorSize; i++) {
            final DeployableServiceSpec spec = new DeployableServiceSpec();
            spec.setName(trackerInfo.getName(i));
            spec.setServiceType(ServiceType.SOAP);
            spec.setRoles(Collections.singletonList(TrackerInfo.ROLE));
            spec.setPackageUri(warFile);
            spec.setPackageType(PackageType.TOMCAT);
            spec.setEndpointName(ENDPOINT);
            spec.setNumberOfInstances(INSTANCES);
            serviceSpecs.add(spec);
        }
        return serviceSpecs;
    }

    private void setDependencies() {
        String calleeServiceName;
        ServiceDependency dependency;
        DeployableServiceSpec callerService;
        final TrackerInfo trackerInfo = new TrackerInfo(); // NOPMD

        for (int i = 0; i < chorServiceSpecs.size() - 1; i++) {
            calleeServiceName = trackerInfo.getName(i + 1);
            dependency = getDependency(calleeServiceName);

            callerService = chorServiceSpecs.get(i);
            callerService.addDependency(dependency);
        }
    }

    private ServiceDependency getDependency(final String calleeServiceName) {
        final ServiceDependency dependency = new ServiceDependency();
        dependency.setServiceSpecName(calleeServiceName);
        dependency.setServiceSpecRole(TrackerInfo.ROLE);
        return dependency;
    }

    private ChoreographySpec createChorSpec() {
        final ChoreographySpec chorSpec = new ChoreographySpec();
        for (DeployableServiceSpec chorServiceSpec : chorServiceSpecs) {
            chorSpec.addServiceSpec(chorServiceSpec);
        }
        return chorSpec;
    }
}
