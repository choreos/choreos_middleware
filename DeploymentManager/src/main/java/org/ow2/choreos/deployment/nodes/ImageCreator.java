package org.ow2.choreos.deployment.nodes;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;
import org.ow2.choreos.breaker.Invoker;
import org.ow2.choreos.breaker.InvokerBuilder;
import org.ow2.choreos.breaker.InvokerException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.LogConfigurator;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

/**
 * Creates a VM that can be used to generate an image to be used by EE.
 * 
 * @author leonardo
 * 
 */
public class ImageCreator {

    public static final String PREPARE_TOMCAT_COMMAND = ". $HOME/chef-solo/add_recipe_to_node.sh tomcat::choreos";
    public static final String XSB_LINK = "http://valinhos.ime.usp.br:54080/easyesb/xsb-over-dsb-04.07.13.tar.gz";
    public static final String DOWNLOAD_XSB_COMMAND = "wget " + XSB_LINK;
    public static final String DELETE_CHEF_SOLO_FOLDER_COMMAND = "rm -rf chef-solo";

    private NodePoolManager npm;
    private CloudNode node;

    private Logger logger = Logger.getLogger(ImageCreator.class);

    public static void main(String[] args) throws Exception {
        LogConfigurator.configLog();
        ImageCreator creator = new ImageCreator();
        creator.createImage();
    }

    private void createImage() throws Exception {
        logger.info("Creating image-machine");
        createNode();
        installTomcat();
        downloadXSB();
        deleteChefSoloFolder();
        logger.info("Done!");
    }

    private void createNode() throws NodeNotCreatedException {
        npm = NPMFactory.getNewNPMInstance();
        node = npm.createNode(new NodeSpec());
    }

    private void installTomcat() throws InvokerException, NodeNotUpdatedException, NodeNotFoundException {
        logger.info("Installing tomcat");
        prepareTomcat();
        updateNode();
    }

    private void prepareTomcat() throws InvokerException {
        PrepareTomcatTask task = new PrepareTomcatTask();
        Invoker<Void> invoker = new InvokerBuilder<Void>(task, 60).build();
        invoker.invoke();
    }

    private void updateNode() throws NodeNotUpdatedException, NodeNotFoundException {
        npm.updateNode(node.getId());
    }

    private void downloadXSB() throws InvokerException {
        logger.info("Downloading XSB");
        DownloadXSBTask task = new DownloadXSBTask();
        Invoker<Void> invoker = new InvokerBuilder<Void>(task, 60).build();
        invoker.invoke();
    }

    private void deleteChefSoloFolder() throws InvokerException {
        logger.info("Deleting chef-solo folder");
        DeleteChefSoloFolderTask task = new DeleteChefSoloFolderTask();
        Invoker<Void> invoker = new InvokerBuilder<Void>(task, 60).build();
        invoker.invoke();
    }

    private class PrepareTomcatTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            int timeout = 60;
            SshWaiter sshWaiter = new SshWaiter();
            SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
            ssh.runCommand(PREPARE_TOMCAT_COMMAND);
            return null;
        }
    }

    private class DownloadXSBTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            int timeout = 60;
            SshWaiter sshWaiter = new SshWaiter();
            SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
            ssh.runCommand(DOWNLOAD_XSB_COMMAND);
            return null;
        }
    }

    private class DeleteChefSoloFolderTask implements Callable<Void> {
        @Override
        public Void call() throws Exception {
            int timeout = 60;
            SshWaiter sshWaiter = new SshWaiter();
            SshUtil ssh = sshWaiter.waitSsh(node.getIp(), node.getUser(), node.getPrivateKeyFile(), timeout);
            ssh.runCommand(DELETE_CHEF_SOLO_FOLDER_COMMAND);
            return null;
        }
    }

}
