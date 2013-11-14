package org.ow2.choreos.chors.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Collections;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class CDContextCasterTest {

    private static final String CDS_NODE_IP = "192.168.56.100";
    private static final String CD_AIRLINE_NAME = "AirlineCD";
    private static final String CD_AIRLINE_ROLE = "CD-Airline";
    private static final String CD_AIRLINE_ENDPOINT = "CD-airline";
    private static final String CD_TRAVEL_NAME = "TravelAgencyCD";
    private static final String CD_TRAVEL_ROLE = "CD-TravelAgency";
    private static final String CD_TRAVEL_ENDPOINT = "CD-travel-agency";
    
    private ModelsForTest models;
    private DeployableServiceSpec travelSpec, airlineCDSpec, travelCDSpec;
    private DeployableService travelService, travelCD, airlineCD;
    private CloudNode cdsNode;
    private ContextSender senderMock;
    
    @Before
    public void setUp() {
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        travelSpec = models.getTravelSpec();
        travelService = models.getTravelService();
        createCDsNode();
        createdAirlineCD();
        createdTravelCD();
        createDependencies();
        configureMock();
    }

    private void createCDsNode() {
        cdsNode = new CloudNode();
        cdsNode.setIp(CDS_NODE_IP);
    }

    private void createdAirlineCD() {
        airlineCDSpec = new DeployableServiceSpec();
        airlineCDSpec.setName(CD_AIRLINE_NAME);
        airlineCDSpec.setRoles(Collections.singletonList(CD_AIRLINE_ROLE));
        airlineCDSpec.setPackageUri("http://blabla");
        airlineCDSpec.setPackageType(PackageType.EASY_ESB);
        airlineCDSpec.setServiceType(ServiceType.COORDEL);
        airlineCDSpec.setEndpointName(CD_AIRLINE_ENDPOINT);
        airlineCD = new DeployableService(airlineCDSpec);
        airlineCD.setSelectedNodes(Collections.singleton(cdsNode));
        airlineCD.setUUID("77");
        ServiceInstance airlineCDInstance = new ServiceInstance();
        airlineCDInstance.setInstanceId("771");
        airlineCDInstance.setNode(cdsNode);
        airlineCDInstance.setServiceSpec(airlineCDSpec);
        airlineCD.addInstance(airlineCDInstance);
        models.getChorSpec().addServiceSpec(airlineCDSpec);
        models.getChoreography().addService(airlineCD);
    }
    
    private void createdTravelCD() {
        travelCDSpec = new DeployableServiceSpec();
        travelCDSpec.setName(CD_TRAVEL_NAME);
        travelCDSpec.setRoles(Collections.singletonList(CD_TRAVEL_ROLE));
        travelCDSpec.setPackageUri("http://blabla");
        travelCDSpec.setPackageType(PackageType.EASY_ESB);
        travelCDSpec.setServiceType(ServiceType.COORDEL);
        travelCDSpec.setEndpointName(CD_TRAVEL_ENDPOINT);
        travelCD = new DeployableService(travelCDSpec);
        travelCD.setSelectedNodes(Collections.singleton(cdsNode));
        travelCD.setUUID("99");
        ServiceInstance travelCDInstance = new ServiceInstance();
        travelCDInstance.setInstanceId("991");
        travelCDInstance.setNode(cdsNode);
        travelCDInstance.setServiceSpec(travelCDSpec);
        travelCD.addInstance(travelCDInstance);
        models.getChorSpec().addServiceSpec(travelCDSpec);
        models.getChoreography().addService(travelCD);
    }
    
    private void createDependencies() {
        // remove dependency travel --> airline and add dependency travel --> airline-cd
        travelSpec.setDependencies(Collections.singletonList(new ServiceDependency(CD_AIRLINE_NAME, CD_AIRLINE_ROLE)));
        // add dependencies travel-cd <--> airline-cd
        airlineCDSpec.addDependency(new ServiceDependency(CD_TRAVEL_NAME, CD_TRAVEL_ROLE));
        travelCDSpec.addDependency(new ServiceDependency(CD_AIRLINE_NAME, CD_AIRLINE_ROLE));
        // obs: airline-cd --> airline is a implicit dependency (must be configure on config.xml)
    }
    
    private ContextSender configureMock() {
        senderMock = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = senderMock;
        return senderMock;
    }
    
    @Test
    public void shouldCastCDsContext() throws ContextNotSentException {
        
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();

        String travelUri = travelService.getUris().get(0);
        String cdAirlineUri = airlineCD.getUris().get(0);
        String cdTravelUri = travelCD.getUris().get(0);
        verify(senderMock).sendContext(travelUri, CD_AIRLINE_ROLE, CD_AIRLINE_NAME, airlineCD.getUris());
        verify(senderMock).sendContext(cdAirlineUri, CD_TRAVEL_ROLE, CD_TRAVEL_NAME, travelCD.getUris());
        verify(senderMock).sendContext(cdTravelUri, CD_AIRLINE_ROLE, CD_AIRLINE_NAME, airlineCD.getUris());
    }

    @After
    public void tearDown() {
        ContextSenderFactory.testing = false;
    }
}
