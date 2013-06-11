package org.ow2.choreos.chors.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class ChoreographyTest {
    
    private static final String AIRLINE = ModelsForTest.AIRLINE;
    private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;

    private Choreography chor;
    private DeployableService airlineService;
    private DeployableService travelService;
    
    @Before
    public void setUp() {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 3);
	chor = models.getChoreography();
	airlineService = models.getAirlineService();
	travelService = models.getTravelService();
    }
    
    @Test
    public void shouldRetrieveMap() {
	Map<String, DeployableService> map = chor.getMapOfDeployableServicesBySpecNames();
	assertTrue(map.containsKey(AIRLINE));
	assertTrue(map.containsKey(TRAVEL_AGENCY));
	assertEquals(AIRLINE, map.get(AIRLINE).getSpec().getName());
	assertEquals(TRAVEL_AGENCY, map.get(TRAVEL_AGENCY).getSpec().getName());
	assertEquals(airlineService, map.get(AIRLINE));
	assertEquals(travelService, map.get(TRAVEL_AGENCY));
    }
    
    @Test
    public void shoulRemoveInstance() {
	DeployableService airline = chor.getDeployableServiceBySpecName(AIRLINE);
	assertEquals(3, airline.getInstances().size());
	ServiceInstance instance = airline.getInstances().get(0);
	String id = instance.getInstanceId();
	chor.removeServiceInstance(instance);
	List<String> instancesId = getInstancesIds();
	assertEquals(2, airline.getInstances().size());
	assertFalse(instancesId.contains(id));
    }

    private List<String> getInstancesIds() {
	List<String> ids = new ArrayList<String>();
	for (DeployableService svc: chor.getDeployableServices()) {
	    for (ServiceInstance instance: svc.getInstances()) {
		ids.add(instance.getInstanceId());
	    }
	}
	return ids;
    }
    
}
