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
        final int groupSize = 5; // NOPMD

        for (int i = 0; i < chorServiceSpecs.size(); i += groupSize) {
            final List<DeployableServiceSpec> dependents = getDeployableServiceSpecs(i, groupSize);
            final List<ServiceDependency> dependencies = getDependencies(i, groupSize);

            dependents.get(0).addDependency(dependencies.get(1));
            dependents.get(1).addDependency(dependencies.get(2));
            dependents.get(2).addDependency(dependencies.get(3));
            dependents.get(4).addDependency(dependencies.get(3));

            dependents.get(1).addDependency(dependencies.get(4));

            connectGroups(i - groupSize, dependencies.get(0));
        }
    }

    private void connectGroups(final int dependentIndex, final ServiceDependency dependency) {
        if (dependentIndex > 0) {
            final DeployableServiceSpec dependent = chorServiceSpecs.get(dependentIndex);
            dependent.addDependency(dependency);
        }
    }

    private List<DeployableServiceSpec> getDeployableServiceSpecs(final int start, final int length) {
        final List<DeployableServiceSpec> specs = new ArrayList<DeployableServiceSpec>(length);
        DeployableServiceSpec spec;

        for (int i = 0; i < length; i++) {
            spec = chorServiceSpecs.get(start + i);
            specs.add(spec);
        }

        return specs;
    }

    private List<ServiceDependency> getDependencies(final int start, final int length) {
        final List<ServiceDependency> dependencies = new ArrayList<ServiceDependency>(length);
        final TrackerInfo trackerInfo = new TrackerInfo();
        String serviceSpecName;
        ServiceDependency dependency;

        for (int i = 0; i < length; i++) {
            serviceSpecName = trackerInfo.getName(start + i);
            dependency = createDependency(serviceSpecName);
            dependencies.add(dependency);
        }

        return dependencies;
    }

    private ServiceDependency createDependency(final String calleeServiceName) {
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
