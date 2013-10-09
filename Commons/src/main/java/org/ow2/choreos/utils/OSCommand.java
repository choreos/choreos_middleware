package org.ow2.choreos.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

// a non-static version of CommandLine
public class OSCommand {

    private static final String DEFAULT_WORKING_DIR = ".";

    private String command;
    private String workingDirectory = DEFAULT_WORKING_DIR;
    private boolean verbose = false;
    private Process process;
    private String result;
    private int exitStatus;

    private static Logger logger = Logger.getLogger(OSCommand.class);

    public OSCommand(String command) {
        this.command = command;
    }

    public OSCommand(String command, boolean verbose) {
        this.command = command;
        this.verbose = verbose;
    }

    public OSCommand(String command, String workingDirectory) {
        this.command = command;
        this.workingDirectory = workingDirectory;
    }

    public OSCommand(String command, String workingDirectory, boolean verbose) {
        this.command = command;
        this.workingDirectory = workingDirectory;
        this.verbose = verbose;
    }

    public String getCommand() {
        return command;
    }

    public String getResult() {
        return result;
    }

    public int getExitStatus() {
        return exitStatus;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    /**
     * 
     * @return the output of the command
     * @throws CommandLineException
     *             if exit status > 0 or some other bad thing happens
     */
    public String execute() throws CommandLineException {
        executeCommand();
        CommandResultRetriever retriever = new CommandResultRetriever();
        retriever.call();
        return result;
    }

    public void executeAssync() throws CommandLineException {
        executeCommand();
        CommandResultRetriever task = new CommandResultRetriever();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(task);
    }

    private class CommandResultRetriever implements Callable<Void> {
        @Override
        public Void call() throws CommandLineException {
            try {
                readCommandOutput();
                checkExitStatus();
            } catch (IOException e) {
                logger.error("Error while executing " + command);
                throw new CommandLineException("Command failed: " + command);
            } catch (CommandLineException e) {
                logger.error("Error while executing " + command);
                throw new CommandLineException("Command failed: " + command);
            }
            return null;
        }
    }

    public void killProcess() {
        process.destroy();
    }

    private void executeCommand() throws CommandLineException {
        if (verbose) {
            logger.info(command);
        }
        File wd = new File(workingDirectory);
        try {
            process = Runtime.getRuntime().exec(command, null, wd);
        } catch (IOException e) {
            fail();
        }
    }

    private String readCommandOutput() throws IOException {
        result = "";
        BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = in.readLine()) != null) {
            result = result + line + '\n';
            if (verbose) {
                logger.info(result);
            }
        }
        return result;
    }

    private void checkExitStatus() throws CommandLineException {
        try {
            int status = process.waitFor();
            if (status > 0) {
                throw new CommandLineException("Command failed: " + command);
            }
        } catch (InterruptedException e) {
            throw new CommandLineException("Command failed: " + command);
        }
    }

    @Override
    public String toString() {
        return command;
    }

    private void fail() throws CommandLineException {
        logger.error("Error while executing " + command);
        throw new CommandLineException("Command failed: " + command);
    }

}
