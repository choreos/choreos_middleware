/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.chors.context.ContextCaster;
import org.ow2.choreos.chors.context.ContextSender;
import org.ow2.choreos.chors.context.ContextSenderFactory;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * To run this test you must before start the airline and travel-agency services
 * provided within the samples/EnactmentEngineServices project on localhost.
 * 
 * You must not also call the travel agency setInvocationAddress operation
 * before run the test
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class ContextCasterTest {

    private static final String AIRLINE = ModelsForTest.AIRLINE;
    private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;
    private static final String TRAVEL_AGENCY_URI = "http://localhost:1235/travelagency";

    private ChoreographySpec chorSpec;
    private Map<String, DeployableService> deployedServices;
    private ModelsForTest models;

    @BeforeClass
    public static void configLog() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {
	models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	chorSpec = models.getChorSpec();
	deployedServices = new HashMap<String, DeployableService>();
	deployedServices.put(AIRLINE, models.getAirlineService());
	deployedServices.put(TRAVEL_AGENCY, models.getTravelService());
    }

    @Test
    public void test() throws WSDLException, XmlException, IOException, FrameworkException,
	    InvalidOperationNameException, NoSuchFieldException {

	checkPreCondition();

	ContextSenderFactory fac = new ContextSenderFactory();
	@SuppressWarnings("static-access")
	ContextSender sender = fac.getInstance(ServiceType.SOAP);
	ContextCaster caster = new ContextCaster(sender);
	caster.cast(chorSpec, deployedServices);

	WSClient travel = new WSClient(TRAVEL_AGENCY_URI + "?wsdl");
	Item response = travel.request("buyTrip");
	String codes = response.getChild("return").getContent();

	assertEquals("33--22", codes);
    }

    private void checkPreCondition() throws WSDLException, XmlException, IOException, FrameworkException,
	    InvalidOperationNameException, NoSuchFieldException {

	WSClient travel = new WSClient(TRAVEL_AGENCY_URI + "?wsdl");
	Item response = travel.request("buyTrip");
	String codes = response.getChild("return").getContent();

	assertEquals("Not possible to buy now", codes);
    }

}
