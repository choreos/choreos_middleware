package org.ow2.choreos.tracker.experiment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.utils.CommandLineException;
import org.ow2.choreos.utils.OSCommand;

public class ScalabilityExperiment {
    
    public static final int NUM_EXECUTIONS = 10;
    public static final int CHORS_QUANTITY = 1;
    public static final int[] CHORS_SIZES = new int[]{200, 600, 1000, 1400, 1800};
    public static final int[] VMS_LIMITS = new int[]{10, 30, 50, 70, 90};

    private static final String CHOREOS_MIDDLEWARE_FOLDER = "home/ubuntu/choreos_middleware";
    
    private static Logger logger = Logger.getLogger(ScalabilityExperiment.class);
    
    private List<ExperimentDefinition> definitions;
    
    public static void main(String[] args) {
        ScalabilityExperiment exps = new ScalabilityExperiment();
        exps.run();
    }
    
    private void run() {
        createDefinitions();
        for (int i=0; i<NUM_EXECUTIONS; i++) {
            executeDefinition(definitions.get(i));
        }
    }

    private void createDefinitions() {
        definitions = new ArrayList<ExperimentDefinition>();
        for (int s=0; s<CHORS_SIZES.length; s++) {
            for (int i=0; i<NUM_EXECUTIONS; i++) {
                int size = CHORS_SIZES[s];
                int vmsLimit = VMS_LIMITS[s];
                definitions.add(new ExperimentDefinition(i, CHORS_QUANTITY, size, vmsLimit));
            }
        }
    }

    private void executeDefinition(ExperimentDefinition def) {
        logger.info("Executing definition " + def);
        cleanAmazon();
        startEE();
        Experiment exp = new Experiment(def);
        exp.run(); // TODO set timeout
        stopEE();
    }

    private void cleanAmazon() {
        OSCommand command = new OSCommand("." + CHOREOS_MIDDLEWARE_FOLDER + "/scripts/clean_aws.sh");
        CommandTask task = new CommandTask(command);
        Invoker<String> invoker = new InvokerBuilder<String>(task, 120).trials(3).build();
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            logger.error("Could not kill EC2 instances!!!");
        }
    }

    private void startEE() {
        String mvnExecCom = "mvn:exec";
        // TODO: execute each one of these commands in a new thread, so we do not get blocked
        try {
            OSCommand dmCommand = new OSCommand(mvnExecCom, CHOREOS_MIDDLEWARE_FOLDER + "/DeploymentManager");
            dmCommand.execute();
            OSCommand cdCommand = new OSCommand(mvnExecCom, CHOREOS_MIDDLEWARE_FOLDER + "/ChoreographyDeployer");
            cdCommand.execute();
        } catch (CommandLineException e) {
            logger.error("Could not start EE");
        }
    }

    private void stopEE() {
        // ./kill_ee.sh
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
