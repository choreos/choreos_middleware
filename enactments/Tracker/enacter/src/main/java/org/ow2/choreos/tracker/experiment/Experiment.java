package org.ow2.choreos.tracker.experiment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.tracker.Enacter;
import org.ow2.choreos.utils.Concurrency;
import org.ow2.choreos.utils.LogConfigurator;

public class Experiment {

    public static final int RUN = Integer.parseInt(ExperimentConfiguration.get("RUN"));
    public static final int CHORS_SIZE = Integer.parseInt(ExperimentConfiguration.get("CHORS_SIZE"));
    public static final int VM_LIMIT = Integer.parseInt(ExperimentConfiguration.get("VM_LIMIT"));
    public static final int CHORS_QTY = Integer.parseInt(ExperimentConfiguration.get("CHORS_QTY"));

    public static final int ENACTMENT_TIMEOUT = 50;
    public static final int VERIFY_TIMEOUT = 15;
    
    private int run, chorsQty, chorsSize, vmLimit;
    private Report report;
    private List<RunnableEnacter> enacters;
    private List<RunnableVerifier> verifiers;
    
    private static Logger logger = Logger.getLogger(Experiment.class);

    public static void main(String[] args) {
        LogConfigurator.configLog();
        Experiment experiment = new Experiment(RUN, CHORS_QTY, CHORS_SIZE, VM_LIMIT);
        experiment.run();
    }

    public Experiment(int run, int chorsQty, int chorsSize, int vmLimit) {
        this.run = run;
        this.chorsQty = chorsQty;
        this.chorsSize = chorsSize;
        this.vmLimit = vmLimit;
    }
    
    public Experiment(ExperimentDefinition def) {
        this.run = def.getRun();
        this.chorsQty = def.getChorsQty();
        this.chorsSize = def.getChorsSize();
        this.vmLimit = def.getVmLimit();
    }

    public void run() {

        report = new Report(run, chorsQty, chorsSize, vmLimit);
        logger.info("Running " + report.header);

        long t0_total = System.nanoTime();

        enactTrackers();
        verifyTracker();

        long tf_total = System.nanoTime();
        long delta_total = tf_total - t0_total;
        report.setTotalTime(delta_total);

        finishReport();

        System.out.println(report);
        try {
            report.toFile();
        } catch (IOException e) {
            logger.error("Could not save the report.");
        }
    }

    private void enactTrackers() {
        ExecutorService executor = Executors.newFixedThreadPool(chorsQty);
        enacters = new ArrayList<RunnableEnacter>();
        long t0 = System.nanoTime();
        for (int i = 0; i < chorsQty; i++) {
            Enacter enacter = new Enacter(i);
            RunnableEnacter runnable = new RunnableEnacter(enacter, report, chorsSize);
            enacters.add(runnable);
            executor.submit(runnable);
        }

        Concurrency.waitExecutor(executor, ENACTMENT_TIMEOUT, "Could not properly enact all the chors");
        long tf = System.nanoTime();
        report.setChorsEnactmentTotalTime(tf - t0);
    }
    
    private void verifyTracker() {
        ExecutorService executor = Executors.newFixedThreadPool(chorsQty);
        verifiers = new ArrayList<RunnableVerifier>();
        long t0 = System.nanoTime();
        for (RunnableEnacter enacter : enacters) {
            RunnableVerifier verifier = new RunnableVerifier(enacter.enacter, report, chorsQty, chorsSize);
            verifiers.add(verifier);
            executor.submit(verifier);
        }
        Concurrency.waitExecutor(executor, VERIFY_TIMEOUT, TimeUnit.MINUTES, logger, "Could not properly verify all the chors");
        long tf = System.nanoTime();
        report.setCheckTotalTime(tf - t0);
    }
    
    private void finishReport() {
        int chorsWorking = 0;
        int servicesWorking = 0;
        for (RunnableVerifier verifier : verifiers) {
            if (verifier.ok) {
                chorsWorking++;
            }
            servicesWorking += verifier.servicesWorking.get();
        }
        report.setChorsWorking(chorsWorking);
        report.setServicesWorking(servicesWorking);
    }

}
