/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.chors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.apache.xmlbeans.XmlException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerBuilder;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.WSDLException;

/**
 * This test will enact a choreography with two services. One of them will serve
 * with three replicas and will be updated to serve with two
 * 
 * Before the test, start the DeploymentManagerServer
 * 
 * @author tfmend
 * 
 */
@Category(IntegrationTest.class)
public class DecreaseNumberOfInstancesTest {

    private ChoreographySpec spec;
    private ChoreographySpec newSpec;

    @BeforeClass
    public static void startServers() {
	LogConfigurator.configLog();
    }

    @Before
    public void setUp() {

	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 3);
	spec = models.getChorSpec();
	ModelsForTest newModels = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 2);
	newSpec = newModels.getChorSpec();
    }

    @Test
    public void shouldEnactChoreographyWithTwoAirlineServicesAndChangeToThree() throws Exception {

	ChoreographyDeployer ee = new ChoreographyDeployerImpl();

	String chorId = ee.createChoreography(spec);
	Choreography chor = ee.enactChoreography(chorId);

	Service airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);

	DeployableService travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);

	Invoker<String> invoker = getBuyTripInvoker(travel);

	String codes, codes2, codes3 = "";

	codes = invoker.invoke();
	codes2 = invoker.invoke();
	codes3 = invoker.invoke();

	assertEquals(3, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
	assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
	assertTrue(codes3.startsWith("33") && codes3.endsWith("--22"));
	assertFalse(codes.equals(codes2));
	assertFalse(codes3.equals(codes));
	assertFalse(codes3.equals(codes2));

	ee.updateChoreography(chorId, newSpec);
	chor = ee.enactChoreography(chorId);

	airline = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
	travel = chor.getDeployableServiceBySpecName(ModelsForTest.TRAVEL_AGENCY);
	invoker = getBuyTripInvoker(travel);

	codes = invoker.invoke();
	codes2 = invoker.invoke();

	assertEquals(2, airline.getUris().size());
	assertTrue(codes.startsWith("33") && codes.endsWith("--22"));
	assertTrue(codes2.startsWith("33") && codes2.endsWith("--22"));
	assertFalse(codes.equals(codes2));

    }

    private Invoker<String> getBuyTripInvoker(DeployableService travelAgency) {
	BuyTripTask task = new BuyTripTask(travelAgency);
	Invoker<String> invoker = new InvokerBuilder<String>(task, 10).trials(2).pauseBetweenTrials(10).build();
	return invoker;
    }

    private class BuyTripTask implements Callable<String> {

	private WSClient client;

	public BuyTripTask(DeployableService travelAgency) {
	    try {
		client = new WSClient(travelAgency.getUris().get(0) + "?wsdl");
	    } catch (WSDLException e) {
		e.printStackTrace();
	    } catch (XmlException e) {
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    } catch (FrameworkException e) {
		e.printStackTrace();
	    }
	}

	@Override
	public String call() throws Exception {
	    Item response = client.request("buyTrip");
	    return response.getChild("return").getContent();
	}

    }

}
