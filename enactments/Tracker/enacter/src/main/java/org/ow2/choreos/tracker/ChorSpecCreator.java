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
    private static final int INSTANCES = 1;

    private transient List<DeployableServiceSpec> chorServiceSpecs;
    private transient final TrackerProperties trackerProps = new TrackerProperties();

    public static void setWarFile(final String uri) {
        warFile = uri;
    }

    public ChoreographySpec create(final int chorSize) {
        this.chorServiceSpecs = createServiceSpecs(chorSize);
        setDependencies();
        return createChorSpec();
    }

    private List<DeployableServiceSpec> createServiceSpecs(final int chorSize) {
        final List<DeployableServiceSpec> serviceSpecs = new ArrayList<DeployableServiceSpec>(chorSize);

        for (int i = 0; i < chorSize; i++) {
            final DeployableServiceSpec spec = getServiceSpec(i);
            serviceSpecs.add(spec);
        }

        return serviceSpecs;
    }

    private DeployableServiceSpec getServiceSpec(final int trackerNumber) {
        final DeployableServiceSpec spec = new DeployableServiceSpec();

        setCommonAttributes(spec, trackerNumber);
        setSpecificAttributes(spec, trackerNumber);

        return spec;
    }

    private void setCommonAttributes(final DeployableServiceSpec spec, final int trackerNumber) {
        spec.setName(trackerProps.getName(trackerNumber));
        spec.setServiceType(ServiceType.SOAP);
        spec.setPackageUri(warFile);
        spec.setPackageType(PackageType.TOMCAT);
        spec.setNumberOfInstances(INSTANCES);
    }

    private void setSpecificAttributes(final DeployableServiceSpec spec, final int trackerNumber) {
        final TrackerType type = trackerProps.getType(trackerNumber);
        spec.setRoles(Collections.singletonList(type.toString()));
        spec.setEndpointName(trackerProps.getEndpoint(type));
    }

    private void setDependencies() {
        final int groupSize = 5; // NOPMD

        for (int i = 0; i < chorServiceSpecs.size(); i += groupSize) {
            final List<DeployableServiceSpec> dependents = getDeployableServiceSpecs(i, groupSize);
            final List<ServiceDependency> dependencies = getDependencies(i, groupSize);

            dependents.get(0).addDependency(dependencies.get(1));
            dependents.get(1).addDependency(dependencies.get(2));
            dependents.get(1).addDependency(dependencies.get(4));
            dependents.get(2).addDependency(dependencies.get(3));
            dependents.get(4).addDependency(dependencies.get(3));

            connectGroups(i - groupSize, dependencies.get(0));
        }
    }

    private void connectGroups(final int dependentIndex, final ServiceDependency dependency) {
        if (dependentIndex >= 0) {
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
        ServiceDependency dependency;

        for (int i = 0; i < length; i++) {
            dependency = createDependency(start + i);
            dependencies.add(dependency);
        }

        return dependencies;
    }

    private ServiceDependency createDependency(final int trackerNumber) {
        final String name = trackerProps.getName(trackerNumber);
        final TrackerType type = trackerProps.getType(trackerNumber);

        final ServiceDependency dependency = new ServiceDependency();
        dependency.setServiceSpecName(name);
        dependency.setServiceSpecRole(type.toString());

        return dependency;
    }

    private ChoreographySpec createChorSpec() {
        final ChoreographySpec chorSpec = new ChoreographySpec();
        for (DeployableServiceSpec chorServiceSpec : chorServiceSpecs) {
            chorSpec.addServiceSpec(chorServiceSpec);
        }
        return chorSpec;
    }

    public static void main(final String[] args) {
        final ChorSpecCreator creator = new ChorSpecCreator();
        final ChoreographySpec chorSpec = creator.create(5);
        chorSpec.printDependenciesMap();
    }
}
