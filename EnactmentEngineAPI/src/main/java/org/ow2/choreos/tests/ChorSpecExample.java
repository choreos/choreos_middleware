/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.tests;

import java.util.Collections;

import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceDependency;
import org.ow2.choreos.services.datamodel.ServiceType;

/**
 * This code is intended to be published as an example on EE user guide
 * 
 * @author leonardo
 * 
 */
public class ChorSpecExample {

    public static final String AIRLINE = "airline";
    public static final String TRAVEL_AGENCY = "travelagency";
    public static final String AIRLINE_JAR = 
            "http://valinhos.ime.usp.br:54080/airline.jar";
    public static final String TRAVEL_AGENCY_JAR = 
            "http://valinhos.ime.usp.br:54080/travel.jar";
    public static final int AIRLINE_PORT = 1234;
    public static final int TRAVEL_AGENCY_PORT = 1235;

    private ChoreographySpec chorSpec;
    private DeployableServiceSpec airlineSpec;
    private DeployableServiceSpec travelSpec;

    public ChoreographySpec getChorSpec() {
        createAirlineSpec();
        createTravelAgencySpec();
        chorSpec = new ChoreographySpec(this.airlineSpec, this.travelSpec);
        return chorSpec;
    }

    private void createAirlineSpec() {
        airlineSpec = new DeployableServiceSpec();
        airlineSpec.setName(AIRLINE);
        airlineSpec.setServiceType(ServiceType.SOAP);
        airlineSpec.setPackageType(PackageType.COMMAND_LINE);
        airlineSpec.setPackageUri(AIRLINE_JAR);
        airlineSpec.setPort(AIRLINE_PORT);
        airlineSpec.setEndpointName(AIRLINE);
        airlineSpec.setRoles(Collections.singletonList(AIRLINE));
    }

    private void createTravelAgencySpec() {
        travelSpec = new DeployableServiceSpec();
        travelSpec.setName(TRAVEL_AGENCY);
        travelSpec.setServiceType(ServiceType.SOAP);
        travelSpec.setPackageType(PackageType.COMMAND_LINE);
        travelSpec.setPackageUri(TRAVEL_AGENCY_JAR);
        travelSpec.setPort(TRAVEL_AGENCY_PORT);
        travelSpec.setEndpointName(TRAVEL_AGENCY);
        travelSpec.setRoles(Collections.singletonList(TRAVEL_AGENCY));
        ServiceDependency dependency = new ServiceDependency();
        dependency.setServiceSpecName(AIRLINE);
        dependency.setServiceSpecRole(AIRLINE);
        travelSpec.addDependency(dependency);
    }

}