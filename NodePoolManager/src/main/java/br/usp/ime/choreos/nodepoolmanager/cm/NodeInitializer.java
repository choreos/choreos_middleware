package br.usp.ime.choreos.nodepoolmanager.cm;

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
        System.out.println(returnText);
        return !returnText.equals("");
    }

    public void initialize() throws Exception {
        System.out.println("Initializing node with chef");

        String command = new ScriptsProvider().getChefStartupScript(Configuration.get("CHEF_KEY_FILE"));
        String output = new SshUtil(hostname).runCommand(command);
        // System.out.println(output);
    }

}
