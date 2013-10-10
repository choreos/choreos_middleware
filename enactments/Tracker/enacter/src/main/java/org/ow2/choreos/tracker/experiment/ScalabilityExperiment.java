package org.ow2.choreos.tracker.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.OSCommand;

public class ScalabilityExperiment {

    private static final int NUM_EXECUTIONS = 2;
    private static final int CHORS_QUANTITY = 1;
    private static final int[] CHORS_SIZES = new int[] { 5, 10 };
    private static final int[] VMS_LIMITS = new int[] { 1, 2 };
//    public static final int[] CHORS_SIZES = new int[] { 200, 600, 1000, 1400, 1800 };
//    public static final int[] VMS_LIMITS = new int[] { 10, 30, 50, 70, 90 };

    private static final int TIME_TO_WAIT_EE_START_MILLISEC = 1 * 60 * 1000;
    private static final String CHOREOS_MIDDLEWARE_FOLDER = "/home/ubuntu/choreos_middleware";

    private static Logger logger = Logger.getLogger(ScalabilityExperiment.class);

    private List<ExperimentDefinition> definitions;
    private OSCommand dmCommand, cdCommand;
    private ExperimentDefinition currentDefinition;

    public static void main(String[] args) {
        LogConfigurator.configLog();
        ScalabilityExperiment exps = new ScalabilityExperiment();
        exps.run();
    }

    private void run() {
        Report.setRepetitions(NUM_EXECUTIONS);
        createDefinitions();
        for (ExperimentDefinition def : definitions) {
            currentDefinition = def;
            executeDefinition(def);
        }
        cleanAmazon();
    }

    private void createDefinitions() {
        definitions = new ArrayList<ExperimentDefinition>();
        for (int s = 0; s < CHORS_SIZES.length; s++) {
            for (int i = 0; i < NUM_EXECUTIONS; i++) {
                int size = CHORS_SIZES[s];
                int vmsLimit = VMS_LIMITS[s];
                definitions.add(new ExperimentDefinition(i+1, CHORS_QUANTITY, size, vmsLimit));
            }
        }
    }

    private void executeDefinition(ExperimentDefinition def) {
        logger.info("Executing definition " + def);
        cleanAmazon();
        startEE();
        configureEE();
        Experiment exp = new Experiment(def);
        exp.run();
        stopEE();
    }

    private void cleanAmazon() {
        OSCommand command = new OSCommand("sh " + CHOREOS_MIDDLEWARE_FOLDER + "/scripts/clean_aws.sh");
        CommandTask task = new CommandTask(command);
        Invoker<String> invoker = new InvokerBuilder<String>(task, 120).trials(5).pauseBetweenTrials(30).build();
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            logger.error("Could not kill EC2 instances!!!");
            System.exit(-1);
        }
        logger.info("EC2 instances destroyed");
    }

    private void startEE() {
        logger.info("Starting EE...");
        String mvnExecCom = "mvn exec:java -o";
        try {
            dmCommand = new OSCommand(mvnExecCom, CHOREOS_MIDDLEWARE_FOLDER + "/DeploymentManager");
            dmCommand.executeAssync();
            cdCommand = new OSCommand(mvnExecCom, CHOREOS_MIDDLEWARE_FOLDER + "/ChoreographyDeployer");
            cdCommand.executeAssync();
        } catch (CommandLineException e) {
            logger.error("Could not start EE");
        }
        try {
            Thread.sleep(TIME_TO_WAIT_EE_START_MILLISEC);
        } catch (InterruptedException e) {
            logger.error("Killed while sleeping!");
        }
        logger.info("EE started (I hope)");
    }

    private void configureEE() {
        String host = "http://0.0.0.0:9100/deploymentmanager";
        WebClient client = WebClient.create(host);
        client.type(MediaType.TEXT_PLAIN);
        client.path("nodes").path("vm_limit");
        String vmLimit = Integer.toString(currentDefinition.getVmLimit());
        client.put(vmLimit);
        logger.info("EE configured");
    }

    private void stopEE() {
        dmCommand.killProcess();
        cdCommand.killProcess();
        logger.info("EE killed");
    }

    private class CommandTask implements Callable<String> {

        OSCommand command;

        public CommandTask(OSCommand command) {
            this.command = command;
        }

        @Override
        public String call() throws Exception {
            return command.execute();
        }

    }

}
