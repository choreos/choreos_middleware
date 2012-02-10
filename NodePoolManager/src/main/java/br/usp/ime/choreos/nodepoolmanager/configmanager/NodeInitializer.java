package br.usp.ime.choreos.nodepoolmanager.configmanager;

import java.io.IOException;

import br.usp.ime.choreos.nodepoolmanager.Configuration;
import br.usp.ime.choreos.nodepoolmanager.utils.ScriptsProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeInitializer {

    private final String hostname;

    public NodeInitializer(String hostname) {
        this.hostname = hostname;
    }

    public void cleanPetals() throws Exception {
        new SshUtil(hostname).runCommand("sudo rm -rf /opt/*\n");
    }

    public boolean isInitialized() throws Exception {
        String returnText = "";
        returnText = new SshUtil(hostname).runCommand("ls /opt");
        System.out.println(">>"+returnText+"<<");
        return !returnText.equals("");
    }

    public void initialize() throws Exception {
        System.out.println("Initializing node with chef");

        String command = new ScriptsProvider().getChefBootstrapScript(Configuration.get("CHEF_ORGANIZATION_KEY_FILE"));
        new SshUtil(hostname).runCommand(command);
        installPetals();
    }

	private void installPetals() throws IOException, Exception {
		String command;
		command = new ScriptsProvider().getChefServerManagerScript(hostname);
		System.out.println(command);
        Runtime.getRuntime().exec(command);
        new NodeConfigurationManager(hostname).updateNodeConfiguration();
	}
}
