package org.ow2.choreos.ee.reconfiguration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.services.datamodel.qos.DesiredQoS;
import org.ow2.choreos.services.datamodel.qos.ResponseTimeMetric;
import org.ow2.choreos.tests.ModelsForTest;

public class ServicesRuleBuilderTest {

    final static String TRAVELAGENCY_RULES = "travelagency_services_rule";

    final static String AIRLINE_UUID = "1";
    final static String TRAVELAGENCY_UUID = "2";

    List<DeployableService> services;
    String expectedRule;

    @Before
    public void setUp() {
	try {
	    expectedRule = FileUtils.readFileToString(new File(getClass().getClassLoader()
		    .getResource(TRAVELAGENCY_RULES).getFile()));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    // TODO @Test
    public void createCorrectRules() {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.TOMCAT);
	Choreography chor = models.getChoreography();

	chor.setId("1");
	DeployableService airline = chor.getDeployableServiceBySpecName("airline");
	airline.setUUID("1");
	DeployableService travelagency = chor.getDeployableServiceBySpecName("travelagency");
	travelagency.setUUID("2");

	DesiredQoS desiredQoS = new DesiredQoS();
	ResponseTimeMetric responseTime = new ResponseTimeMetric();
	responseTime.setAcceptablePercentage(0.05f);
	responseTime.setMaxDesiredResponseTime(120f);
	desiredQoS.setResponseTimeMetric(responseTime);

	airline.getSpec().setDesiredQoS(desiredQoS);
	travelagency.getSpec().setDesiredQoS(desiredQoS);

	services = chor.getDeployableServices();
	assertEquals(expectedRule, new ServicesRuleBuilder().assemblyRules(services, "1"));
    }
}
