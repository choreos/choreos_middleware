package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 
 * Provides an exclusive token to concurrent clients.
 * 
 * The token is used to ensure only a client at time is enable to perform some
 * action. Used to enforce AWS EC2 rule: clients should not make more than one
 * request per second.
 * 
 * @author leonardo
 * 
 */
public class DelayedRequestEnforcer {

    private final static Logger logger = Logger.getLogger(DelayedRequestEnforcer.class);

    private final long minTimeBetweenRequestsInMillis;

    private boolean token = true;
    private Random random = new Random();

    public DelayedRequestEnforcer(long minTimeBetweenRequestsInMillis) {
        this.minTimeBetweenRequestsInMillis = minTimeBetweenRequestsInMillis;
    }

    public void enforceRule() {
        while (!getToken()) {
            waitDelta();
        }
        waitMinTimeBetweenRequests();
        releaseToken();
    }

    private void waitDelta() {
        final int DELAY = 10;
        final int DELTA = random.nextInt(10);
        try {
            Thread.sleep(DELAY + DELTA);
        } catch (InterruptedException e) {
            logger.error("Exception at sleeping =/");
        }
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

    private void waitMinTimeBetweenRequests() {
        try {
            Thread.sleep(minTimeBetweenRequestsInMillis);
        } catch (InterruptedException e) {
            logger.error("Exception at sleeping =/");
        }
    }
    
    private void releaseToken() {
        token = true;
    }

}
