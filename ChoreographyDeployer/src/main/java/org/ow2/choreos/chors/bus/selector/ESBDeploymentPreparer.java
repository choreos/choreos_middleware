package org.ow2.choreos.chors.bus.selector;


import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class ESBDeploymentPreparer {

    public static final String COMMAND = ". $HOME/chef-solo/add_recipe_to_node.sh easyesb";
    
    private CloudNode node;
    SshWaiter sshWaiter = new SshWaiter();
    
    public ESBDeploymentPreparer(CloudNode node) {
        this.node = node;
    }

    public void prepareESBDeployment() throws BusNotPreparedException {
        PreparerTask task = new PreparerTask();
        // TODO
        int timeout = 60;
        int trials = 3;
        Invoker<Void> invoker = new Invoker<Void>(task, trials, timeout, 0, TimeUnit.SECONDS);
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            throw new BusNotPreparedException();
        }
    }
    
    private class PreparerTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            // TODO
            int timeout = 60;
            SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
            ssh.runCommand(COMMAND);
            return null;
        }
    }
    
}
