package br.usp.ime.choreos.nodepoolmanager.cm;

import java.io.File;
import java.net.URL;

import org.apache.commons.io.FileUtils;

import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeInitializer {

    private final String nodeName;

    public NodeInitializer(String nodeName) {
        this.nodeName = nodeName;
    }

    public boolean isInitialized() {
        String returnText = "";
        try {
            returnText = new SshUtil(nodeName).runCommand("ls /opt");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return !returnText.equals("");
    }

    public void initialize() {
        try {
            URL scriptFile = ClassLoader.getSystemResource("chef/startup_script.sh");
            String command = FileUtils.readFileToString(new File(scriptFile.getFile()));
            new SshUtil(nodeName).runCommand(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
