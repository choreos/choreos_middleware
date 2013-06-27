package org.ow2.choreos.deployment.services.preparer;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;
import org.ow2.choreos.utils.TimeoutsAndTrials;

public class InstanceDeploymentPreparer {

    private DeployableServiceSpec spec;
    private CloudNode node;
    
    private String instanceId;

    public InstanceDeploymentPreparer(DeployableServiceSpec spec, CloudNode node) {
        this.spec = spec;
        this.node = node;
    }

    public void prepareDeployment() throws PrepareDeploymentFailedException {
        runDeploymentPrepare();
        scheduleHandler();
    }
    
    private void runDeploymentPrepare() throws PrepareDeploymentFailedException {
        int timeout = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TIMEOUT");
        int trials = TimeoutsAndTrials.get("PREPARE_DEPLOYMENT_TRIALS");
        String command = getCommand();
        PreparerTask task = new PreparerTask(command, node);
        Invoker<String> invoker = new Invoker<String>(task, trials, timeout, TimeUnit.SECONDS);
        try {
            instanceId = invoker.invoke();
        } catch (InvokerException e) {
            throw new PrepareDeploymentFailedException(spec.getName(), node);
        }
    }

    private String getCommand() {
        String packageUri = spec.getPackageUri();
        String cookbookTemplateName = spec.getPackageType().getExtension();
        return ". chef-solo/prepare_deployment.sh " + packageUri + " " + cookbookTemplateName;
    }
    
    private void scheduleHandler() {
        InstanceCreatorUpdateHandler handler = getHandler(instanceId);
        NodeUpdater nodeUpdater = NodeUpdaters.getUpdaterFor(node.getId());
        nodeUpdater.addHandler(handler);
    }

    private InstanceCreatorUpdateHandler getHandler(String instanceId) {
        String serviceId = spec.getUuid();
        InstanceCreatorUpdateHandler handler = new InstanceCreatorUpdateHandler(serviceId, instanceId, spec, node);
        return handler;
    }
    
    private class PreparerTask implements Callable<String> {

        String command;
        CloudNode node;

        public PreparerTask(String command, CloudNode node) {
            this.command = command;
            this.node = node;
        }

        @Override
        public String call() throws Exception {
            SshUtil ssh = waitForSshAccess();
            String serviceInstanceId = ssh.runCommand(command);
            ssh.disconnect();
            return serviceInstanceId;
        }

        private SshUtil waitForSshAccess() throws SshNotConnected {
            SshWaiter sshWaiter = new SshWaiter();
            int timeout = TimeoutsAndTrials.get("CONNECT_SSH_TIMEOUT");
            return sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
        }

    }

}
