package org.ow2.choreos.tracker.experiment;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.tracker.Enacter;

class RunnableVerifier implements Runnable {

    Enacter enacter;
    Report report;
    boolean ok = false;
    int servicesWorking;
    
    private static Logger logger = Logger.getLogger(RunnableVerifier.class);

    RunnableVerifier(Enacter enacter, Report report) {
        this.enacter = enacter;
        this.report = report;
    }

    @Override
    public void run() {
        logger.info("Verifying Enacter#" + enacter.getId());
        try {
            long t0 = System.nanoTime();
            ok = enacter.verifyAnswer();
            long tf = System.nanoTime();
            report.addCheckTime(tf - t0);
            if (ok) {
                int all = Experiment.CHORS_SIZE;
                logger.info("All " + all + " services working on enacter " + enacter.getId());
                servicesWorking = all;
            } else {
                verifyServicePerService();
            }
        } catch (MalformedURLException e) {
            logger.error("Ops, this problem should not occur with Enacter#" + enacter.getId());
            ok = false;
        }
        logger.info("Enacter#" + enacter.getId() + " ok: " + ok);
    }

    private void verifyServicePerService() {
        Choreography chor = enacter.getChoreography();
        int len = chor.getServices().size();
        logger.info("Verifying " + len + " services in enacter " + enacter.getId());
        servicesWorking = 0;
        for (DeployableService svc : chor.getDeployableServices()) {
            String wsdl = getWsdl(svc);
            WSDLChecker checker = new WSDLChecker(wsdl);
            if (checker.check()) {
                servicesWorking++;
                logger.info("Tracker OK: " + wsdl);
            } else {
                logger.error("Tracker not accessible (enacter " + enacter.getId() + "): " + wsdl);
            }
        }
    }

    private String getWsdl(Service svc) {
        String uri = svc.getUris().get(0);
        String wsdl = uri.replaceAll("/$", "").concat("?wsdl");
        return wsdl;
    }

}
