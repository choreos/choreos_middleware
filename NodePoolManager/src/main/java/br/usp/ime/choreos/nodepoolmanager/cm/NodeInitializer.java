package br.usp.ime.choreos.nodepoolmanager.cm;

import br.usp.ime.choreos.nodepoolmanager.utils.ScriptsProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeInitializer {

    private final String nodeName;

    public NodeInitializer(String nodeName) {
        this.nodeName = nodeName;
    }

    public void cleanPetals() throws Exception {
        new SshUtil(nodeName).runCommand("sudo rm -rf /opt/*\n");
    }

    public boolean isInitialized() throws Exception {
        String returnText = "";
        returnText = new SshUtil(nodeName).runCommand("ls /opt");
        System.out.println(returnText);
        return !returnText.equals("");
    }

    public void initialize() throws Exception {
        String command = new ScriptsProvider().chefStartupScript("chef/key.pem");
        String output = new SshUtil(nodeName).runCommand(command);
        System.out.println(output);
    }

}
