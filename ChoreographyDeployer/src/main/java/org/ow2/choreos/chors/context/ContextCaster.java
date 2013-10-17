/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.TimeoutsAndTrials;

/**
 * Context choreography context to choreography services
 * 
 * @author leonardo
 * 
 */
public class ContextCaster {

    private final int MAX_THREADS = 200;

    private final int timeout;
    private final int trials;
    private final int pauseBetweenTrials;

    private Choreography chor;
    private ExecutorService executor;

    private Logger logger = Logger.getLogger(ContextCaster.class);

    public ContextCaster(Choreography chor) {
        this.timeout = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_TIMEOUT");
        this.trials = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_TRIALS");
        this.pauseBetweenTrials = TimeoutsAndTrials.get("SET_INVOCATION_ADDRESS_PAUSE");
        this.chor = chor;
    }

    public void cast() {
        if (!inputOK()) {
            logger.warn("No deployed services found. Not going to cast context.");
            return;
        }
        int nThreads = getNThreads();
        this.executor = Executors.newFixedThreadPool(nThreads);
        logger.info("Passing context to deployed services on choreograghy " + chor.getId());
        for (Service consumer : chor.getServices()) {
            castContextsToConsumer(consumer);
        }
        waitContextCasting();
    }

    private boolean inputOK() {
        return chor.getDeployableServices() != null && !chor.getDeployableServices().isEmpty();
    }

    private int getNThreads() {
        int size = chor.getDeployableServices().size();
        return size > MAX_THREADS ? MAX_THREADS : size;
    }

    private void castContextsToConsumer(Service consumer) {
        ServiceContextCaster serviceContextCaster = new ServiceContextCaster(chor, consumer);
        ServiceContextCasterTask task = new ServiceContextCasterTask(serviceContextCaster);
        executor.submit(task);
    }

    private void waitContextCasting() {
        int totalTimeout = trials * (timeout + pauseBetweenTrials);
        totalTimeout += 0.3 * totalTimeout;
        Concurrency.waitExecutor(executor, totalTimeout, TimeUnit.SECONDS, logger, "Could not wait context casting");
    }
    
    private class ServiceContextCasterTask implements Runnable {

        ServiceContextCaster serviceContextCaster;
        
        public ServiceContextCasterTask(ServiceContextCaster serviceContextCaster) {
            super();
            this.serviceContextCaster = serviceContextCaster;
        }

        @Override
        public void run() {
            serviceContextCaster.cast();            
        }
    }

}
