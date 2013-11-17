package org.ow2.choreos;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

public class AirportSpecs {
	private static final String AIRPORT = "airport";
	private static final String AIRPORT_BUS_COMPANY = "airportbuscompany";
	private static final String AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR = "airportdisplayactuatorsaggregator";
	private static final String AIRPORT_INFRARED_SENSORS_AGGREGATOR = "airportinfraredsensorsaggregator";
	private static final String AIRPORT_NOISE_SENSORS_AGGREGATOR = "aiportnoisesensorsaggregator";
	private static final String AIRPORT_PRESSURE_SENSORS_AGGREGATOR = "aiportpressuresensorsaggregator";
	private static final String AIRPORT_SIGN_ACTUATORS_AGGREGATOR = "airportsignactuatorsaggregator";
	private static final String AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR = "airportspeakeractuatorsaggregator";
	private static final String BOOKABLE_AMENITY = "bookableamenity";
	private static final String LUGGAGE_HANDLING_COMPANY = "luggagehandlingcompany";
	private static final String MID_DISPLAY_ACTUATORS_AGGREGATOR = "middisplayactuatorsaggregator";
	private static final String MID_LOCATION_SENSORS_AGGREGATOR = "midlocationsensorsaggregator";
	private static final String MID_MICROPHONE_SENSORS_AGGREGATOR = "midmicrophonesensorsaggregator";
	private static final String SECURITY_COMPANY = "securitycompany";
	private static final String STAND_AND_GATE_MANAGEMENT = "standandgatemanagement";

	private static final String AIRPORT_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airport-service.jar";
	private static final String AIRPORT_BUS_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportbuscompany-service.jar";
	private static final String AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportdisplayactuatorsaggregator-service.jar";
	private static final String AIRPORT_INFRARED_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportinfraredsensorsaggregator-service.jar";
	private static final String AIRPORT_NOISE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportnoisesensorsaggregator-service.jar";
	private static final String AIRPORT_PRESSURE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportpressuresensorsaggregator-service.jar";
	private static final String AIRPORT_SIGN_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportsignactuatorsaggregator-service.jar";
	private static final String AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportspeakeractuatorsaggregator-service.jar";
	private static final String BOOKABLE_AMENITY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/bookableamenity-service.jar";
	private static final String LUGGAGE_HANDLING_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/luggagehandlingcompany-service.jar";
	private static final String MID_DISPLAY_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/middisplayactuatorsaggregator-service.jar";
	private static final String MID_LOCATION_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/midlocationsensorsaggregator-service.jar";
	private static final String MID_MICROPHONE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/midmicrophonesensorsaggregator-service.jar";
	private static final String SECURITY_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/securitycompany-service.jar";
	private static final String STAND_AND_GATE_MANAGEMENT_JAR = "http://sd-49168.dedibox.fr/DeployableServices/standandgatemanagement-service.jar";

	private static final int AIRPORT_PORT = 8004;
	private static final int AIRPORT_BUS_COMPANY_PORT = 8023;
	private static final int AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_PORT = 8006;
	private static final int AIRPORT_INFRARED_SENSORS_AGGREGATOR_PORT = 8007;
	private static final int AIRPORT_NOISE_SENSORS_AGGREGATOR_PORT = 8008;
	private static final int AIRPORT_PRESSURE_SENSORS_AGGREGATOR_PORT = 8024;
	private static final int AIRPORT_SIGN_ACTUATORS_AGGREGATOR_PORT = 8010;
	private static final int AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_PORT = 8011;
	private static final int BOOKABLE_AMENITY_PORT = 8013;
	private static final int LUGGAGE_HANDLING_COMPANY_PORT = 8017;
	private static final int MID_DISPLAY_ACTUATORS_AGGREGATOR_PORT = 8018;
	private static final int MID_LOCATION_SENSORS_AGGREGATOR_PORT = 8019;
	private static final int MID_MICROPHONE_SENSORS_AGGREGATOR_PORT = 8020;
	private static final int SECURITY_COMPANY_PORT = 8021;
	private static final int STAND_AND_GATE_MANAGEMENT_PORT = 8022;

	private List<DeployableServiceSpec> services = new ArrayList<DeployableServiceSpec>();
	private ResourceImpact resourceImpact = new ResourceImpact();
	private final static String VERSION = "1.0";
	private DeployableServiceSpec service;

	public ChoreographySpec getChorSpec() {
		createSpecs();

		DeployableServiceSpec[] serviceArray = new DeployableServiceSpec[services
				.size()];
		serviceArray = services.toArray(serviceArray);
		return new ChoreographySpec(serviceArray);
	}

	private void createSpecs() {
		createAirportServiceSpec();
		createStandServiceSpec();
		createSecuritySpec();
		createMidMicSpec();
		createMidLocationSpec();
		createMidDisplaySpec();
		createLuggageSpec();
		createAmenitySpec();
		createSpeakerSpec();
		createSignSpec();
		createPresssureSpec();
		createNoiseSpec();
		createInfraredSpec();
		createDisplaySpec();
		createBusSpec();
	}

	private void createBusSpec() {
		createServiceSpec(AIRPORT_BUS_COMPANY, AIRPORT_BUS_COMPANY_JAR,
				AIRPORT_BUS_COMPANY_PORT);
	}

	private void createDisplaySpec() {
		createServiceSpec(AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR,
				AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_JAR,
				AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_PORT);
	}

	private void createInfraredSpec() {
		createServiceSpec(AIRPORT_INFRARED_SENSORS_AGGREGATOR,
				AIRPORT_INFRARED_SENSORS_AGGREGATOR_JAR,
				AIRPORT_INFRARED_SENSORS_AGGREGATOR_PORT);
	}

	private void createNoiseSpec() {
		createServiceSpec(AIRPORT_NOISE_SENSORS_AGGREGATOR,
				AIRPORT_NOISE_SENSORS_AGGREGATOR_JAR,
				AIRPORT_NOISE_SENSORS_AGGREGATOR_PORT);
	}

	private void createPresssureSpec() {
		createServiceSpec(AIRPORT_PRESSURE_SENSORS_AGGREGATOR,
				AIRPORT_PRESSURE_SENSORS_AGGREGATOR_JAR,
				AIRPORT_PRESSURE_SENSORS_AGGREGATOR_PORT);
	}

	private void createSignSpec() {
		createServiceSpec(AIRPORT_SIGN_ACTUATORS_AGGREGATOR,
				AIRPORT_SIGN_ACTUATORS_AGGREGATOR_JAR,
				AIRPORT_SIGN_ACTUATORS_AGGREGATOR_PORT);
	}

	private void createSpeakerSpec() {
		createServiceSpec(AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR,
				AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_JAR,
				AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_PORT);
	}

	private void createAmenitySpec() {
		createServiceSpec(BOOKABLE_AMENITY, BOOKABLE_AMENITY_JAR,
				BOOKABLE_AMENITY_PORT);
	}

	private void createLuggageSpec() {
		createServiceSpec(LUGGAGE_HANDLING_COMPANY,
				LUGGAGE_HANDLING_COMPANY_JAR, LUGGAGE_HANDLING_COMPANY_PORT);
		addDependency(AIRPORT);
	}

	private void createMidDisplaySpec() {
		createServiceSpec(MID_DISPLAY_ACTUATORS_AGGREGATOR,
				MID_DISPLAY_ACTUATORS_AGGREGATOR_JAR,
				MID_DISPLAY_ACTUATORS_AGGREGATOR_PORT);
	}

	private void createMidLocationSpec() {
		createServiceSpec(MID_LOCATION_SENSORS_AGGREGATOR,
				MID_LOCATION_SENSORS_AGGREGATOR_JAR,
				MID_LOCATION_SENSORS_AGGREGATOR_PORT);
	}

	private void createMidMicSpec() {
		createServiceSpec(MID_MICROPHONE_SENSORS_AGGREGATOR,
				MID_MICROPHONE_SENSORS_AGGREGATOR_JAR,
				MID_MICROPHONE_SENSORS_AGGREGATOR_PORT);
	}

	private void createSecuritySpec() {
		createServiceSpec(SECURITY_COMPANY, SECURITY_COMPANY_JAR,
				SECURITY_COMPANY_PORT);
		addDependency(AIRPORT);
	}

	private void createStandServiceSpec() {
		createServiceSpec(STAND_AND_GATE_MANAGEMENT,
				STAND_AND_GATE_MANAGEMENT_JAR, STAND_AND_GATE_MANAGEMENT_PORT);
		addDependency(AIRPORT);
	}

	private void createAirportServiceSpec() {
		createServiceSpec(AIRPORT, AIRPORT_JAR, AIRPORT_PORT);
		addDependency(AIRPORT_BUS_COMPANY);
		addDependency(AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR);
		addDependency(AIRPORT_INFRARED_SENSORS_AGGREGATOR);
		addDependency(AIRPORT_NOISE_SENSORS_AGGREGATOR);
		addDependency(AIRPORT_PRESSURE_SENSORS_AGGREGATOR);
		addDependency(AIRPORT_SIGN_ACTUATORS_AGGREGATOR);
		addDependency(AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR);
		addDependency(BOOKABLE_AMENITY);
		addDependency(LUGGAGE_HANDLING_COMPANY);
		addDependency(MID_DISPLAY_ACTUATORS_AGGREGATOR);
		addDependency(MID_LOCATION_SENSORS_AGGREGATOR);
		addDependency(SECURITY_COMPANY);
		addDependency(STAND_AND_GATE_MANAGEMENT);

	}

	private void createServiceSpec(final String name, final String jar, int port) {
		service = new DeployableServiceSpec(name, ServiceType.SOAP,
				PackageType.COMMAND_LINE, resourceImpact, VERSION, jar, port,
				name, 1);
		services.add(service);
	}

	private void addDependency(final String depServiceName) {
		final ServiceDependency dependency = new ServiceDependency(
				depServiceName, depServiceName);
		service.addDependency(dependency);
	}
}
