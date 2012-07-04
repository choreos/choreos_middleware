package eu.choreos.nodepoolmanager.cloudprovider;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.JSchException;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.ConfigurationManager;
import eu.choreos.nodepoolmanager.chef.ChefScripts;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.CommandLine;
import eu.choreos.nodepoolmanager.utils.LogConfigurator;
import eu.choreos.nodepoolmanager.utils.SshUtil;

/**
 * Please, before running these tests, read the README file
 *  
 * @author leonardo
 *
 */
public class FixedCloudProviderTest {
	
	@Before
	public void setUp() {
		LogConfigurator.configLog();
	}
	
	@Test
	public void shouldReturnNodeInfo() throws RunNodesException {
		
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());
		
		assertTrue(node.getHostname() != null && !node.getHostname().isEmpty());
		
		Pattern pat = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
		Matcher matcher = pat.matcher(node.getIp());
		assertTrue(matcher.matches());
	}
	
	@Test
	public void shouldConnectToTheNode() throws RunNodesException {
		
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());

		try {
			SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
			assertTrue(ssh.isAccessible());
		} catch (JSchException e) {
			System.out.println("Could not connect! " + e);
			fail();
		}
	}
	
	/**
	 * Beware, this test will leave the node bootstrapped
	 * 
	 * @throws Exception
	 */
	@Test
	public void shouldLeaveNodeBootstraped() throws Exception {

		//cleanChefServer();
		
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createOrUseExistingNode(new Node());
		System.out.println(node);

//        waitSsh(node);
//        System.out.println("waited");
        
        ConfigurationManager configurationManager = new ConfigurationManager();
        if (!configurationManager.isInitialized(node)) {
        	System.out.println("Going to bootstrap the node");
        	configurationManager.initializeNode(node);
        	System.out.println("Checking if bootstrap was OK");
        	assertTrue(configurationManager.isInitialized(node));
        } else {
        	System.out.println("Node was already bootstrapped");
        }
	}
	
	private void waitSsh(Node node) throws JSchException {
		
		SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            System.out.println("Going to try again");
	}

	private void cleanChefServer() {
		
		String workdir = Configuration.get("CHEF_REPO");
		String deleteChoreosNode = ChefScripts.getDeleteNode("choreos-node");
		String deleteChoreosClient = ChefScripts.getDeleteClient("choreos-node");
		CommandLine.runLocalCommand(deleteChoreosClient, workdir, true);
		CommandLine.runLocalCommand(deleteChoreosNode, workdir, true);
	}
}
