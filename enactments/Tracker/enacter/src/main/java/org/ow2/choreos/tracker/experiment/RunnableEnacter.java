package org.ow2.choreos.tracker.experiment;

import java.net.MalformedURLException;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.tracker.Enacter;

class RunnableEnacter implements Runnable {

    private static final String TRACKER_WAR_URL = "http://valinhos.ime.usp.br:54080/services/tracker2.0.war";
    
    Enacter enacter;
    Report report;
    int chorsSize;
    boolean ok = false;
    
    private static Logger logger = Logger.getLogger(RunnableEnacter.class);

    RunnableEnacter(Enacter enacter, Report report, int chorsSize) {
        this.enacter = enacter;
        this.report = report;
        this.chorsSize = chorsSize;
    }

    @Override
    public void run() {
        logger.info("Enacting Enacter#" + enacter.getId());
        try {
            long t0 = System.nanoTime();
            enacter.enact(TRACKER_WAR_URL, chorsSize);
            long tf = System.nanoTime();
            report.addChorEnactmentTime(tf - t0);
            ok = true;
        } catch (MalformedURLException e) {
            failed();
        } catch (EnactmentException e) {
            failed();
        } catch (ChoreographyNotFoundException e) {
            failed();
        }
        logger.info("Enacter#" + enacter.getId() + " enacted.");
    }

    private void failed() {
        logger.error("Enacter#" + enacter.getId() + " not enacted!");
        ok = false;
    }
}

