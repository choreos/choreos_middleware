/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;

public class NodeSelectorFactory {

    private static final String NODE_SELECTOR_PROPERTY = "NODE_SELECTOR";

    private static Map<String, NodeSelector> selectors = new ConcurrentHashMap<String, NodeSelector>();

    private static Logger logger = Logger.getLogger(NodeSelectorFactory.class);

    /**
     * 
     * @return
     * @throws IllegalArgumentException
     *             if could not retrieve NodeSelector
     */
    public static NodeSelector getInstance() {
	String nodeSelectorType = DeploymentManagerConfiguration.get(NODE_SELECTOR_PROPERTY);
	if (nodeSelectorType == null) {
	    logger.error(NODE_SELECTOR_PROPERTY + " property not set on properties file!");
	    throw new IllegalArgumentException();
	}
	return getInstance(nodeSelectorType);
    }

    /**
     * 
     * @param nodeSelectorType
     * @return
     * @throws IllegalArgumentException
     *             if could not retrieve NodeSelector
     */
    public static NodeSelector getInstance(String nodeSelectorType) {
	if (!selectors.containsKey(nodeSelectorType)) {
	    synchronized (NodeSelectorFactory.class) {
		if (!selectors.containsKey(nodeSelectorType)) {
		    NodeSelector nodeSelector = newInstance(nodeSelectorType);
		    selectors.put(nodeSelectorType, nodeSelector);
		}
	    }
	}
	return selectors.get(nodeSelectorType);
    }

    private static NodeSelector newInstance(String nodeSelectorType) {
	String className = SelectorClassMap.getClassName(nodeSelectorType);
	NodeSelector nodeSelector = null;
	try {
	    @SuppressWarnings("unchecked") // catches handle the problem
	    Class<NodeSelector> clazz = (Class<NodeSelector>) Class.forName(className);
	    nodeSelector = clazz.newInstance();
	} catch (ClassNotFoundException e) {
	    logger.error("invalid NODE_SELECTOR type!");
	    throw new IllegalArgumentException();
	} catch (InstantiationException e) {
	    logger.error("invalid NODE_SELECTOR type!");
	    throw new IllegalArgumentException();
	} catch (IllegalAccessException e) {
	    logger.error("invalid NODE_SELECTOR type!");
	    throw new IllegalArgumentException();
	} catch (ClassCastException e) {
	    logger.error("invalid NODE_SELECTOR type!");
	    throw new IllegalArgumentException();
	}
	return nodeSelector;
    }

}
