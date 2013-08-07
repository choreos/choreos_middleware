package org.ow2.choreos.deployment.nodes.cm;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerBuilder;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshUtil;

public class NodeSetup {

    public static NodeSetup setupForTest;
    public static boolean testing = false;

    private static final int trials = 3;
    private static final int trialTimeout = 10;

    private CloudNode node;
    private String scriptFileName;
    private Map<String, String> substitutions;

    private String script;

    private Logger logger = Logger.getLogger(NodeSetup.class);

    public static NodeSetup getInstance(CloudNode node, String scriptFileName, Map<String, String> substitutions) {
	if (!testing)
	    return new NodeSetup(node, scriptFileName, substitutions);
	else
	    return setupForTest;
    }

    private NodeSetup(CloudNode node, String scriptFileName, Map<String, String> substitutions) {
	this.node = node;
	this.scriptFileName = scriptFileName;
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
	Invoker<String> invoker = new InvokerBuilder<String>(task, trialTimeout).trials(trials).build();
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
