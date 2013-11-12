package org.ow2.choreos.chors.reconfiguration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.services.datamodel.qos.DesiredQoS;
import org.ow2.choreos.services.datamodel.qos.ResponseTimeMetric;
import org.ow2.choreos.tests.ModelsForTest;

public class ChorRulesBuilderTest {

    final static String TRAVELAGENCY_RULES = "travelagency_chor_rules";
    final static String ONLY_AIRLINE_RULES = "only_airline_rules";

    final static String AIRLINE_UUID = "1";
    final static String TRAVELAGENCY_UUID = "2";
    final static String CHOR_ID = "1";

    List<DeployableService> services;
    String expectedRule;
    String expectedRule2;

    @Before
    public void setUp() {
	try {
	    expectedRule = FileUtils.readFileToString(new File(getClass().getClassLoader()
		    .getResource(TRAVELAGENCY_RULES).getFile()));
	    expectedRule2 = FileUtils.readFileToString(new File(getClass().getClassLoader()
		    .getResource(ONLY_AIRLINE_RULES).getFile()));
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void createCorrectChorRules() {
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
	chor.setDeployableServices(services);

	assertEquals(expectedRule, new ChorRulesBuilder().assemblyGlimpseRules(chor));
    }

    @Test
    public void serviceSpecWithNullDesiredQoS() {
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
	travelagency.getSpec().setDesiredQoS(null);

	services = chor.getDeployableServices();
	chor.setDeployableServices(services);

	assertEquals(expectedRule2, new ChorRulesBuilder().assemblyGlimpseRules(chor));

    }

}
