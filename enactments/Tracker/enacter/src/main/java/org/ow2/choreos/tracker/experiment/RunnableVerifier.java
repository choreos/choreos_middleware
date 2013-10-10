package org.ow2.choreos.tracker.experiment;

import java.net.MalformedURLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.tracker.Enacter;
import org.ow2.choreos.utils.Concurrency;

class RunnableVerifier implements Runnable {

    Enacter enacter;
    int chorsQty, chorsSize;
    Report report;
    boolean ok = false;
    AtomicInteger servicesWorking = new AtomicInteger();
    
    private static Logger logger = Logger.getLogger(RunnableVerifier.class);

    RunnableVerifier(Enacter enacter, Report report, int chorsQty, int chorsSize) {
        this.enacter = enacter;
        this.report = report;
        this.chorsQty = chorsQty;
        this.chorsSize = chorsSize;
    }

    @Override
    public void run() {
        logger.info("Verifying Enacter#" + enacter.getId());
        DeployableService tracker0 = enacter.getChoreography().getMapOfDeployableServicesBySpecNames().get("tracker0");
        logger.info("Tracker0 of enacter " + enacter.getId() + ":" + tracker0.getUris());
        try {
            long t0 = System.nanoTime();
            ok = enacter.verifyAnswer();
            long tf = System.nanoTime();
            report.addCheckTime(tf - t0);
            if (ok) {
                int all = chorsSize;
                logger.info("All " + all + " services working on enacter " + enacter.getId());
                servicesWorking.set(all);
            } else {
                int deployedSize = enacter.getChoreography().getDeployableServices().size();
                if (deployedSize > 0)
                    verifyServicePerService();
                else
                    logger.warn("No services deployed at " + enacter.getId());
            }
        } catch (MalformedURLException e) {
            logger.error("Ops, this problem should not occur with Enacter#" + enacter.getId());
            ok = false;
        }
        logger.info("Enacter#" + enacter.getId() + " ok: " + ok);
    }

    private void verifyServicePerService() {
        int NUM_THREADS = 200 / chorsQty;
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        Choreography chor = enacter.getChoreography();
        int len = chor.getServices().size();
        logger.info("Verifying " + len + " services in enacter " + enacter.getId());
        servicesWorking.set(0);
        for (DeployableService svc : chor.getDeployableServices()) {
            String wsdl = getWsdl(svc);
            VerifierTask task = new VerifierTask(wsdl);
            executor.submit(task);
        }
        Concurrency.waitExecutor(executor, Experiment.VERIFY_TIMEOUT, "Service per service verification did not work properly.");
    }

    private String getWsdl(Service svc) {
        String uri = svc.getUris().get(0);
        String wsdl = uri.replaceAll("/$", "").concat("?wsdl");
        return wsdl;
    }
    
    private class VerifierTask implements Callable<Void> {

        String wsdl;
        
        public VerifierTask(String wsdl) {
            super();
            this.wsdl = wsdl;
        }

        @Override
        public Void call() throws Exception {
            WSDLChecker checker = new WSDLChecker(wsdl);
            if (checker.check()) {
                servicesWorking.incrementAndGet();
                logger.info("Tracker OK: " + wsdl);
            } else {
                logger.error("Tracker not accessible (enacter " + enacter.getId() + "): " + wsdl);
            }
            return null;
        }
        
    }
    
    public static void main(String[] args) {
        String wsdl = "http://54.242.118.223:8080/7e8f190b-20da-49f5-a11f-dc3986baabc9/white?wsdl";
        WSDLChecker checker = new WSDLChecker(wsdl);
        System.out.println(checker.check());
    }

}
