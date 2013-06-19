package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Used to enforce AWS EC2 rule: clients should not make more than one request
 * per second
 * 
 * @author leonardo
 * 
 */
public class OneRequestPerSecondEnforcer {

    // only threads with the creationToken can create new nodes
    private boolean creationToken = true;
    private Random random = new Random();

    private Logger logger = Logger.getLogger(OneRequestPerSecondEnforcer.class);

    public void enforceRule() {

	while (!getToken()) {
	    final int DELAY = 10;
	    final int DELTA = random.nextInt(10);
	    try {
		Thread.sleep(DELAY + DELTA);
	    } catch (InterruptedException e) {
		logger.error("Exception at sleeping =/");
	    }
	}

	final int TWO_SECONDS = 2000;
	try {
	    Thread.sleep(TWO_SECONDS);
	} catch (InterruptedException e) {
	    logger.error("Exception at sleeping =/");
	}

	releasesToken();
    }

    private boolean getToken() {
	boolean ok = false;
	synchronized (this) {
	    ok = creationToken;
	    if (ok) {
		creationToken = false;
	    }
	}
	return ok;
    }
    
    private void releasesToken() {
	creationToken = true;
    }
    
}
