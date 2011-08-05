package br.usp.ime.choreos.nodepoolmanager.cm;

import br.usp.ime.choreos.nodepoolmanager.utils.ScriptsProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeInitializer {

    private final String nodeName;

    public NodeInitializer(String nodeName) {
        this.nodeName = nodeName;
    }

    public void cleanPetals() throws Exception {
        new SshUtil(nodeName).runCommand("sudo rm -rf /opt/petals-platform*\n");
    }

    public boolean isInitialized() {
        String returnText = "";
        try {
            returnText = new SshUtil(nodeName).runCommand("ls /opt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(returnText);
        return !returnText.equals("");
    }

    public void initialize() {
        try {
            String command = new ScriptsProvider().chefStartupScript("chef/key.pem");
            String output = new SshUtil(nodeName).runCommand(command);
            System.out.println(output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
