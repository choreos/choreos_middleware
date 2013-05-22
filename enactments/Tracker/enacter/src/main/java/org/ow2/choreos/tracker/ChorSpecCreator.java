package org.ow2.choreos.tracker;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographyServiceDependency;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ChorSpecCreator {

	private static String warFile;
	private static final String ENDPOINT = "endpoint";
	private static final int INSTANCES = 1;

	private transient List<ChoreographyServiceSpec> chorServiceSpecs;

	public static void setWarFile(final String uri) {
		warFile = uri;
	}

	public ChoreographySpec create(final int chorSize) {
		final List<ServiceSpec> serviceSpecs = createServiceSpecs(chorSize);
		this.chorServiceSpecs = choreographServiceSpecs(serviceSpecs);
		setDependencies();
		return createChorSpec();
	}

	@SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
	private List<ServiceSpec> createServiceSpecs(final int chorSize) {
		final List<ServiceSpec> serviceSpecs = new ArrayList<ServiceSpec>(
				chorSize);
		for (int i = 0; i < chorSize; i++) {
			serviceSpecs.add(new DeployableServiceSpec(ServiceType.SOAP,
					PackageType.TOMCAT, null, null, warFile, ENDPOINT,
					INSTANCES));
		}
		return serviceSpecs;
	}

	private List<ChoreographyServiceSpec> choreographServiceSpecs(
			final List<ServiceSpec> serviceSpecs) {
		final List<ChoreographyServiceSpec> chorServiceSpecs = new ArrayList<ChoreographyServiceSpec>(
				serviceSpecs.size());
		ChoreographyServiceSpec choreographed;

		for (int i = 0; i < serviceSpecs.size(); i++) {
			choreographed = choreographServiceSpec(serviceSpecs.get(i), i);
			chorServiceSpecs.add(choreographed);
		}

		return chorServiceSpecs;
	}

	private ChoreographyServiceSpec choreographServiceSpec(
			final ServiceSpec serviceSpec, final int trackerNumber) {
		final ChoreographyServiceSpec choreographed = new ChoreographyServiceSpec();
		choreographed.setServiceSpec(serviceSpec);
		choreographed.addRole(TrackerInfo.ROLE);

		final TrackerInfo trackerInfo = new TrackerInfo();
		choreographed.setName(trackerInfo.getName(trackerNumber));

		return choreographed;
	}

	private void setDependencies() {
		String calleeServiceName;
		ChoreographyServiceDependency dependency;
		ChoreographyServiceSpec callerService;
		final TrackerInfo trackerInfo = new TrackerInfo(); // NOPMD

		for (int i = 0; i < chorServiceSpecs.size() - 1; i++) {
			calleeServiceName = trackerInfo.getName(i + 1);
			dependency = getDependency(calleeServiceName);

			callerService = chorServiceSpecs.get(i);
			callerService.addDependency(dependency);
		}
	}

	private ChoreographyServiceDependency getDependency(
			final String calleeServiceName) {
		final ChoreographyServiceDependency dependency = new ChoreographyServiceDependency();
		dependency.setChoreographyServiceSpecName(calleeServiceName);
		dependency.setChoreographyServiceSpecRole(TrackerInfo.ROLE);

		return dependency;
	}

	private ChoreographySpec createChorSpec() {
		final ChoreographySpec chorSpec = new ChoreographySpec();
		for (ChoreographyServiceSpec chorServiceSpec : chorServiceSpecs) {
			chorSpec.addChoreographyServiceSpec(chorServiceSpec);
		}
		return chorSpec;
	}
}
