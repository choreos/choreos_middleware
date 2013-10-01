package org.ow2.choreos;

import java.util.Collections;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ThalesSpecs {

    public static final String AIRPORT = "airport";
    public static final String AIRPORT_BUS_COMPANY = "airportbuscompany";
    public static final String AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR = "airportdisplayactuatorsaggregator";
    public static final String AIRPORT_INFRARED_SENSORS_AGGREGATOR = "airportinfraredsensorsaggregator";
    public static final String AIRPORT_NOISE_SENSORS_AGGREGATOR = "aiportnoisesensorsaggregator";
    public static final String AIRPORT_PRESSURE_SENSORS_AGGREGATOR = "aiportpressuresensorsaggregator";
    public static final String AIRPORT_SIGN_ACTUATORS_AGGREGATOR = "airportsignactuatorsaggregator";
    public static final String AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR = "airportspeakeractuatorsaggregator";
    public static final String BOOKABLE_AMENITY = "bookableamenity";
    public static final String LUGGAGE_HANDLING_COMPANY = "luggagehandlingcompany";
    public static final String MID_DISPLAY_ACTUATORS_AGGREGATOR = "middisplayactuatorsaggregator";
    public static final String MID_LOCATION_SENSORS_AGGREGATOR = "midlocationsensorsaggregator";
    public static final String MID_MICROPHONE_SENSORS_AGGREGATOR = "midmicrophonesensorsaggregator";
    public static final String SECURITY_COMPANY = "securitycompany";
    public static final String STAND_AND_GATE_MANAGEMENT = "standandgatemanagement";

    public static final String AIRPORT_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airport-service.jar";
    public static final String AIRPORT_BUS_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportbuscompany-service.jar";
    public static final String AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportdisplayactuatorsaggregator-service.jar";
    public static final String AIRPORT_INFRARED_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportinfraredsensorsaggregator-service.jar";
    public static final String AIRPORT_NOISE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportnoisesensorsaggregator-service.jar";
    public static final String AIRPORT_PRESSURE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportpressuresensorsaggregator-service.jar";
    public static final String AIRPORT_SIGN_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportsignactuatorsaggregator-service.jar";
    public static final String AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/airportspeakeractuatorsaggregator-service.jar";
    public static final String BOOKABLE_AMENITY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/bookableamenity-service.jar";
    public static final String LUGGAGE_HANDLING_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/luggagehandlingcompany-service.jar";
    public static final String MID_DISPLAY_ACTUATORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/middisplayactuatorsaggregator-service.jar";
    public static final String MID_LOCATION_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/midlocationsensorsaggregator-service.jar";
    public static final String MID_MICROPHONE_SENSORS_AGGREGATOR_JAR = "http://sd-49168.dedibox.fr/DeployableServices/midmicrophonesensorsaggregator-service.jar";
    public static final String SECURITY_COMPANY_JAR = "http://sd-49168.dedibox.fr/DeployableServices/securitycompany-service.jar";
    public static final String STAND_AND_GATE_MANAGEMENT_JAR = "http://sd-49168.dedibox.fr/DeployableServices/standandgatemanagement-service.jar";

    public static final int AIRPORT_PORT = 8004;
    public static final int AIRPORT_BUS_COMPANY_PORT = 8023;
    public static final int AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_PORT = 8006;
    public static final int AIRPORT_INFRARED_SENSORS_AGGREGATOR_PORT = 8007;
    public static final int AIRPORT_NOISE_SENSORS_AGGREGATOR_PORT = 8008;
    public static final int AIRPORT_PRESSURE_SENSORS_AGGREGATOR_PORT = 8024;
    public static final int AIRPORT_SIGN_ACTUATORS_AGGREGATOR_PORT = 8010;
    public static final int AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_PORT = 8011;
    public static final int BOOKABLE_AMENITY_PORT = 8013;
    public static final int LUGGAGE_HANDLING_COMPANY_PORT = 8017;
    public static final int MID_DISPLAY_ACTUATORS_AGGREGATOR_PORT = 8018;
    public static final int MID_LOCATION_SENSORS_AGGREGATOR_PORT = 8019;
    public static final int MID_MICROPHONE_SENSORS_AGGREGATOR_PORT = 8020;
    public static final int SECURITY_COMPANY_PORT = 8021;
    public static final int STAND_AND_GATE_MANAGEMENT_PORT = 8022;

    private final ResourceImpact resourceImpact = new ResourceImpact();

    private final String serviceVersion = "0.1";

    private ChoreographySpec chorSpec;

    private DeployableServiceSpec airportSpec;
    private DeployableServiceSpec airportBusCompanySpec;
    private DeployableServiceSpec airportDisplayActuatorsAggregatorSpec;
    private DeployableServiceSpec airportInfraredSensorsAggregatorSpec;
    private DeployableServiceSpec airportNoiseSensorsAggregatorSpec;
    private DeployableServiceSpec airportPressureSensorsAggregatorSpec;
    private DeployableServiceSpec airportSignActuatorsAggregatorSpec;
    private DeployableServiceSpec airportSpeakerActuatorsAggregatorSpec;
    private DeployableServiceSpec bookableAmenitySpec;
    private DeployableServiceSpec luggageHandlingCompanySpec;
    private DeployableServiceSpec midDisplayActuatorsAggregatorSpec;
    private DeployableServiceSpec midLocationSensorsAggregatorSpec;
    private DeployableServiceSpec midMicrophoneSensorsAggregatorSpec;
    private DeployableServiceSpec securityCompanySpec;
    private DeployableServiceSpec standAndGateManagementSpec;

    public ThalesSpecs() {
        initAirportSpecs();
        initAirportBusCompanySpecs();
        initAirportDisplayActuatorsAggregatorSpecs();
        initAirportInfraredSensorsAggregatorSpecs();
        initAirportNoiseSensorsAggregatorSpecs();
        initAirportPressureSensorsAggregatorSpecs();
        initAirportSignActuatorsAggregatorSpecs();
        initAirportSpeakerActuatorsAggregatorSpecs();
        initBookableAmenitySpecs();
        initLuggageHandlingCompanySpecs();
        initMIDDisplayActuatorsAggregatorSpecs();
        initMIDLocationSensorsAggregatorSpecs();
        initMIDMicrophoneSensorsAggregatorSpecs();
        initsecurityCompanySpecs();
        initStandAndGateManagementSpecs();

        createChorSpec();
    }

    private void createChorSpec() {
        this.chorSpec = new ChoreographySpec(this.airportSpec, this.airportBusCompanySpec,
                this.airportDisplayActuatorsAggregatorSpec, this.airportInfraredSensorsAggregatorSpec,
                this.airportNoiseSensorsAggregatorSpec, this.airportPressureSensorsAggregatorSpec,
                this.airportSignActuatorsAggregatorSpec, this.airportSpeakerActuatorsAggregatorSpec,
                this.bookableAmenitySpec, this.luggageHandlingCompanySpec, this.midDisplayActuatorsAggregatorSpec,
                this.midLocationSensorsAggregatorSpec, this.midMicrophoneSensorsAggregatorSpec,
                this.securityCompanySpec, this.standAndGateManagementSpec);
    }

    private void initStandAndGateManagementSpecs() {
        standAndGateManagementSpec = new DeployableServiceSpec(STAND_AND_GATE_MANAGEMENT, ServiceType.SOAP,
                PackageType.COMMAND_LINE, resourceImpact, serviceVersion, STAND_AND_GATE_MANAGEMENT_JAR,
                STAND_AND_GATE_MANAGEMENT_PORT, STAND_AND_GATE_MANAGEMENT, 1);
        standAndGateManagementSpec.setRoles(Collections.singletonList(STAND_AND_GATE_MANAGEMENT));
        standAndGateManagementSpec.addDependency(new ServiceDependency(AIRPORT, AIRPORT));
    }

    private void initsecurityCompanySpecs() {
        securityCompanySpec = new DeployableServiceSpec(SECURITY_COMPANY, ServiceType.SOAP, PackageType.COMMAND_LINE,
                resourceImpact, serviceVersion, SECURITY_COMPANY_JAR, SECURITY_COMPANY_PORT, SECURITY_COMPANY, 1);
        securityCompanySpec.setRoles(Collections.singletonList(SECURITY_COMPANY));
        securityCompanySpec.addDependency(new ServiceDependency(AIRPORT, AIRPORT));
    }

    private void initMIDMicrophoneSensorsAggregatorSpecs() {
        midMicrophoneSensorsAggregatorSpec = new DeployableServiceSpec(MID_MICROPHONE_SENSORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                MID_MICROPHONE_SENSORS_AGGREGATOR_JAR, MID_MICROPHONE_SENSORS_AGGREGATOR_PORT,
                MID_MICROPHONE_SENSORS_AGGREGATOR, 1);
        midMicrophoneSensorsAggregatorSpec.setRoles(Collections.singletonList(MID_MICROPHONE_SENSORS_AGGREGATOR));
    }

    private void initMIDLocationSensorsAggregatorSpecs() {
        midLocationSensorsAggregatorSpec = new DeployableServiceSpec(MID_LOCATION_SENSORS_AGGREGATOR, ServiceType.SOAP,
                PackageType.COMMAND_LINE, resourceImpact, serviceVersion, MID_LOCATION_SENSORS_AGGREGATOR_JAR,
                MID_LOCATION_SENSORS_AGGREGATOR_PORT, MID_LOCATION_SENSORS_AGGREGATOR, 1);
        midLocationSensorsAggregatorSpec.setRoles(Collections.singletonList(MID_LOCATION_SENSORS_AGGREGATOR));
    }

    private void initMIDDisplayActuatorsAggregatorSpecs() {
        midDisplayActuatorsAggregatorSpec = new DeployableServiceSpec(MID_DISPLAY_ACTUATORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                MID_DISPLAY_ACTUATORS_AGGREGATOR_JAR, MID_DISPLAY_ACTUATORS_AGGREGATOR_PORT,
                MID_DISPLAY_ACTUATORS_AGGREGATOR, 1);
        midDisplayActuatorsAggregatorSpec.setRoles(Collections.singletonList(MID_DISPLAY_ACTUATORS_AGGREGATOR));
    }

    private void initLuggageHandlingCompanySpecs() {
        luggageHandlingCompanySpec = new DeployableServiceSpec(LUGGAGE_HANDLING_COMPANY, ServiceType.SOAP,
                PackageType.COMMAND_LINE, resourceImpact, serviceVersion, LUGGAGE_HANDLING_COMPANY_JAR,
                LUGGAGE_HANDLING_COMPANY_PORT, LUGGAGE_HANDLING_COMPANY, 1);
        luggageHandlingCompanySpec.setRoles(Collections.singletonList(LUGGAGE_HANDLING_COMPANY));
        luggageHandlingCompanySpec.addDependency(new ServiceDependency(AIRPORT, AIRPORT));
    }

    private void initBookableAmenitySpecs() {
        bookableAmenitySpec = new DeployableServiceSpec(BOOKABLE_AMENITY, ServiceType.SOAP, PackageType.COMMAND_LINE,
                resourceImpact, serviceVersion, BOOKABLE_AMENITY_JAR, BOOKABLE_AMENITY_PORT, BOOKABLE_AMENITY, 1);
        bookableAmenitySpec.setRoles(Collections.singletonList(BOOKABLE_AMENITY));
    }

    private void initAirportSpeakerActuatorsAggregatorSpecs() {
        airportSpeakerActuatorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_JAR, AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR_PORT,
                AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR, 1);
        airportSpeakerActuatorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR));
    }

    private void initAirportSignActuatorsAggregatorSpecs() {
        airportSignActuatorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_SIGN_ACTUATORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_SIGN_ACTUATORS_AGGREGATOR_JAR, AIRPORT_SIGN_ACTUATORS_AGGREGATOR_PORT,
                AIRPORT_SIGN_ACTUATORS_AGGREGATOR, 1);
        airportSignActuatorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_SIGN_ACTUATORS_AGGREGATOR));
    }

    private void initAirportPressureSensorsAggregatorSpecs() {
        airportPressureSensorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_PRESSURE_SENSORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_PRESSURE_SENSORS_AGGREGATOR_JAR, AIRPORT_PRESSURE_SENSORS_AGGREGATOR_PORT,
                AIRPORT_PRESSURE_SENSORS_AGGREGATOR, 1);
        airportPressureSensorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_PRESSURE_SENSORS_AGGREGATOR));
    }

    private void initAirportNoiseSensorsAggregatorSpecs() {
        airportNoiseSensorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_NOISE_SENSORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_NOISE_SENSORS_AGGREGATOR_JAR, AIRPORT_NOISE_SENSORS_AGGREGATOR_PORT,
                AIRPORT_NOISE_SENSORS_AGGREGATOR, 1);
        airportNoiseSensorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_NOISE_SENSORS_AGGREGATOR));
    }

    private void initAirportInfraredSensorsAggregatorSpecs() {
        airportInfraredSensorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_INFRARED_SENSORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_INFRARED_SENSORS_AGGREGATOR_JAR, AIRPORT_INFRARED_SENSORS_AGGREGATOR_PORT,
                AIRPORT_INFRARED_SENSORS_AGGREGATOR, 1);
        airportInfraredSensorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_INFRARED_SENSORS_AGGREGATOR));
    }

    private void initAirportDisplayActuatorsAggregatorSpecs() {
        airportDisplayActuatorsAggregatorSpec = new DeployableServiceSpec(AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR,
                ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact, serviceVersion,
                AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_JAR, AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR_PORT,
                AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR, 1);
        airportDisplayActuatorsAggregatorSpec.setRoles(Collections.singletonList(AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR));
    }

    private void initAirportBusCompanySpecs() {
        airportBusCompanySpec = new DeployableServiceSpec(AIRPORT_BUS_COMPANY, ServiceType.SOAP,
                PackageType.COMMAND_LINE, resourceImpact, serviceVersion, AIRPORT_BUS_COMPANY_JAR,
                AIRPORT_BUS_COMPANY_PORT, AIRPORT_BUS_COMPANY, 1);
        airportBusCompanySpec.setRoles(Collections.singletonList(AIRPORT_BUS_COMPANY));
        airportBusCompanySpec.addDependency(new ServiceDependency(AIRPORT, AIRPORT));
    }

    private void initAirportSpecs() {
        airportSpec = new DeployableServiceSpec(AIRPORT, ServiceType.SOAP, PackageType.COMMAND_LINE, resourceImpact,
                serviceVersion, AIRPORT_JAR, AIRPORT_PORT, AIRPORT, 1);
        airportSpec.setRoles(Collections.singletonList(AIRPORT));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_BUS_COMPANY, AIRPORT_BUS_COMPANY));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR,
                AIRPORT_DISPLAY_ACTUATORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_INFRARED_SENSORS_AGGREGATOR,
                AIRPORT_INFRARED_SENSORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_NOISE_SENSORS_AGGREGATOR,
                AIRPORT_NOISE_SENSORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_PRESSURE_SENSORS_AGGREGATOR,
                AIRPORT_PRESSURE_SENSORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_SIGN_ACTUATORS_AGGREGATOR,
                AIRPORT_SIGN_ACTUATORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR,
                AIRPORT_SPEAKER_ACTUATORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(BOOKABLE_AMENITY, BOOKABLE_AMENITY));
        airportSpec.addDependency(new ServiceDependency(LUGGAGE_HANDLING_COMPANY, LUGGAGE_HANDLING_COMPANY));
        airportSpec.addDependency(new ServiceDependency(MID_DISPLAY_ACTUATORS_AGGREGATOR,
                MID_DISPLAY_ACTUATORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(MID_LOCATION_SENSORS_AGGREGATOR,
                MID_LOCATION_SENSORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(MID_MICROPHONE_SENSORS_AGGREGATOR,
                MID_MICROPHONE_SENSORS_AGGREGATOR));
        airportSpec.addDependency(new ServiceDependency(SECURITY_COMPANY, SECURITY_COMPANY));
        airportSpec.addDependency(new ServiceDependency(STAND_AND_GATE_MANAGEMENT, STAND_AND_GATE_MANAGEMENT));
    }

    public ChoreographySpec getChorSpec() {
        return chorSpec;
    }

}
