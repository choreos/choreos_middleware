/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.utils.Configuration;
import org.ow2.choreos.utils.SingletonsFactory;

public class NodeSelectorFactory extends SingletonsFactory<NodeSelector> {

    private static final String NODE_SELECTOR_PROPERTY = "NODE_SELECTOR";
    private static final String CLASS_MAP_FILE_PATH = "node_selector.properties";

    private static NodeSelectorFactory INSTANCE;

    private static Logger logger = Logger.getLogger(NodeSelectorFactory.class);

    public static NodeSelectorFactory getFactoryInstance() {
	if (INSTANCE == null) {
	    synchronized (NodeSelectorFactory.class) {
		if (INSTANCE == null)
		    createNewInstance();
	    }
	}
	return INSTANCE;
    }

    private static void createNewInstance() {
	Configuration conf = new Configuration(CLASS_MAP_FILE_PATH);
	INSTANCE = new NodeSelectorFactory(conf);
    }

    private NodeSelectorFactory(Configuration classMap) {
	super(classMap);
    }

    public NodeSelector getNodeSelectorInstance() {
	String nodeSelectorType = DeploymentManagerConfiguration.get(NODE_SELECTOR_PROPERTY);
	if (nodeSelectorType == null) {
	    logger.error(NODE_SELECTOR_PROPERTY + " property not set on properties file!");
	    throw new IllegalArgumentException();
	}
	return getInstance(nodeSelectorType);
    }

}
