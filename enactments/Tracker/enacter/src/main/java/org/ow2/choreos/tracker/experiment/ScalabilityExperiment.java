package org.ow2.choreos.tracker.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.OSCommand;

public class ScalabilityExperiment {

    private static final int NUM_EXECUTIONS = 10;
    private static final int CHORS_QUANTITY = 1;
    public static final int[] CHORS_SIZES = new int[] { 200, 600, 1000, 1400, 1800 };
    public static final int[] VMS_LIMITS = new int[] { 10, 30, 50, 70, 90 };

    private static final int TIME_TO_WAIT_EE_START_MILLISEC = 1 * 60 * 1000;
    // private static final String CHOREOS_MIDDLEWARE_FOLDER =
    // "/home/leonardo/workspaces/choreos/choreos_middleware";
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
    }

    private void createDefinitions() {
        definitions = new ArrayList<ExperimentDefinition>();
        for (int i = 0; i < NUM_EXECUTIONS; i++) {
            for (int s = 0; s < CHORS_SIZES.length; s++) {
                int size = CHORS_SIZES[s];
                int vmsLimit = VMS_LIMITS[s];
                definitions.add(new ExperimentDefinition(i+1, CHORS_QUANTITY, size, vmsLimit));
            }
        }
    }

    private void executeDefinition(ExperimentDefinition def) {
        logger.info("Executing definition " + def);
        startEE();
        configureEE();
        Experiment exp = new Experiment(def);
        exp.run();
        cleanAmazon();
        stopEE();
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
        waitALittle();
        if (pingEE()) {
            logger.info("EE started");
        } else {
            abortExecution("EE not started");
        }
    }

    private void waitALittle() {
        try {
            Thread.sleep(TIME_TO_WAIT_EE_START_MILLISEC);
        } catch (InterruptedException e) {
            logger.error("Killed while sleeping!");
        }
    }
    
    private boolean pingEE() {
        EEPingTask task = new EEPingTask();
        Invoker<Integer> invoker = new InvokerBuilder<Integer>(task, 10).trials(20).pauseBetweenTrials(30).build();
        try {
            int status = invoker.invoke();
            return status == 404;
        } catch (InvokerException e) {
            return false;
        }
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

    private void cleanAmazon() {
        logger.info("Destroying EC2 instances...");
        String host = "http://0.0.0.0:9100/deploymentmanager";
        NodePoolManager npm = new NodesClient(host);
        try {
            npm.destroyNodes();
            logger.info("EC2 instances destroyed");
        } catch (NodeNotDestroyed e) {
            abortExecution("Could not destroy all EC2 instances!");
        }
    }
    
    private void stopEE() {
        dmCommand.killProcess();
        cdCommand.killProcess();
        logger.info("EE killed");
    }

    private class EEPingTask implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            String host = "http://0.0.0.0:9100/deploymentmanager";
            WebClient client = WebClient.create(host);
            client.path("nodes").path("41");
            Response response = client.get();
            return response.getStatus();
        }
    }
    
    private void abortExecution(String error) {
        logger.error("Aborting execution: " + error);
        System.exit(-1);
    }

}
