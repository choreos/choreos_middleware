package org.ow2.choreos;

import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.utils.Alarm;

public class USPEnactment {

    public static void main(String[] args) throws EnactmentException, ChoreographyNotFoundException {

        // setup
        final String CHOR_DEPLOYER_URI = "http://localhost:9102/choreographydeployer";
        ChoreographyDeployer chorDeployer = new ChorDeployerClient(CHOR_DEPLOYER_URI);
        USPSpecs uspSpecs = new USPSpecs();
        ChoreographySpec chorSpec = uspSpecs.getSpec(); 
        
        // action
        String chorId = chorDeployer.createChoreography(chorSpec);
        Choreography chor = chorDeployer.enactChoreography(chorId);
        
        // output
        System.out.println(chor);

        // alarm
        Alarm alarm = new Alarm();
        alarm.play();        
    }

}
