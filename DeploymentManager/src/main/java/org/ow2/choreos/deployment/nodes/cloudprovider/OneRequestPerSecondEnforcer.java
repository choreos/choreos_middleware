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

    private static Logger logger = Logger.getLogger(OneRequestPerSecondEnforcer.class);

    // only threads with the token can make requests
    private boolean token = true;
    private Random random = new Random();

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

        releaseToken();
    }

    private boolean getToken() {
        boolean ok = false;
        synchronized (this) {
            ok = token;
            if (ok) {
                token = false;
            }
        }
        return ok;
    }

    private void releaseToken() {
        token = true;
    }

}
