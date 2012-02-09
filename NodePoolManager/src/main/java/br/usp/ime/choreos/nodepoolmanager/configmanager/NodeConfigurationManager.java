package br.usp.ime.choreos.nodepoolmanager.configmanager;

import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class NodeConfigurationManager {

    private final String hostname;

    public NodeConfigurationManager(String hostname) {
        this.hostname = hostname;
    }

    public void updateNodeConfiguration() throws Exception {
        new SshUtil(hostname).runCommand("chef-client\n");
    }
}
