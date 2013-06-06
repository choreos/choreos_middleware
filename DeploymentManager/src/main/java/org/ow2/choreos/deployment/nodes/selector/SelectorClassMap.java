package org.ow2.choreos.deployment.nodes.selector;

import org.ow2.choreos.utils.Configuration;

class SelectorClassMap {

    private static final String FILE_PATH = "node_selector.properties";

    private final Configuration configuration;

    private static SelectorClassMap INSTANCE = new SelectorClassMap();

    private SelectorClassMap() {

	this.configuration = new Configuration(FILE_PATH);
    }

    public static String getClassName(String nodeSelectorType) {

	return INSTANCE.configuration.get(nodeSelectorType);
    }

}
