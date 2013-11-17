package org.ow2.choreos;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.utils.CommandLineException;

public class AirportEnact {
    public static final String CHOR_DEPLOYER_URI = "http://localhost:9102/choreographydeployer";

    public static void main(String[] args) throws EnactmentException, ChoreographyNotFoundException,
            CommandLineException {
        // setup
        final ChoreographyDeployer chorDeployer = new ChorDeployerClient(CHOR_DEPLOYER_URI);
        final AirportSpecs specs = new AirportSpecs();
        final ChoreographySpec chorSpec = specs.getChorSpec();

        // action
        final String chorId = chorDeployer.createChoreography(chorSpec);
        final Choreography chor = chorDeployer.enactChoreography(chorId);

        // output
        System.out.println(chor);
    }
}
