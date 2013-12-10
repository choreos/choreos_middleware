package org.ow2.choreos.ee.nodes.cm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerBuilder;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshUtil;

public class NodeSetup {

    public static NodeSetup setupForTest;
    public static boolean testing = false;

    private static final int trials = 3;

    private CloudNode node;
    private String scriptFileName;
    private Map<String, String> substitutions;
    private int timeoutMinutes;

    private String script;

    private Logger logger = Logger.getLogger(NodeSetup.class);

    public static NodeSetup getInstance(CloudNode node, String scriptFileName, int timeoutMinutes,
            Map<String, String> substitutions) {
        if (!testing)
            return new NodeSetup(node, scriptFileName, timeoutMinutes, substitutions);
        else
            return setupForTest;
    }

    private NodeSetup(CloudNode node, String scriptFileName, int timeoutMinutes, Map<String, String> substitutions) {
        this.node = node;
        this.scriptFileName = scriptFileName;
        this.timeoutMinutes = timeoutMinutes;
        this.substitutions = substitutions;
    }

    private void applySubstitutions() {
        for (Map.Entry<String, String> substitution : substitutions.entrySet()) {
            script = script.replace(substitution.getKey(), substitution.getValue());
        }
    }

    private void getScript() {
        URL scriptFile;
        scriptFile = this.getClass().getClassLoader().getResource(scriptFileName);
        script = null;
        try {
            script = FileUtils.readFileToString(new File(scriptFile.getFile()));
        } catch (IOException e) {
            logger.error("Could not retrieve script " + scriptFileName);
            throw new IllegalStateException();
        }
    }

    public void setup() throws NodeSetupException {
        NodeSetupTask task = new NodeSetupTask();
        Invoker<String> invoker = new InvokerBuilder<String>("NodeSetupTask", task, timeoutMinutes).trials(trials)
                .timeUnit(TimeUnit.MINUTES).build();
        getScript();
        applySubstitutions();
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            throw new NodeSetupException();
        }
    }

    private class NodeSetupTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
            String output = ssh.runCommand(script);
            ssh.disconnect();
            return output;
        }
    }

}
