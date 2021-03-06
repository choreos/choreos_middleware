/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.selector;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.ee.config.QoSManagementConfiguration;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.MemoryType;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.ObjectFilter;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

/**
 * Filter nodes that match requirements. The filter policy is extracted from
 * MAPPER_POLICY configuration. The default behavior if the ANY_FIT policy.
 * 
 * @author leonardo
 * 
 */
class NodeFilter implements ObjectFilter<CloudNode, DeployableServiceSpec> {

    private enum MapperPolicy {
	ANY_FIT, EXACT_FIT
    };

    private static final String MAPPER_POLICY_PROPERTY = "MAPPER_POLICY";
    private static final MapperPolicy DEFAULT_MAPPER_POLICY = MapperPolicy.ANY_FIT;
    private static final String[] NODE_TYPES = QoSManagementConfiguration
	    .getMultiple(QoSManagementConfiguration.NODE_TYPES);

    private MapperPolicy policy;

    public NodeFilter() {
	this.policy = this.retrieveMapperPolicy();
    }

    private MapperPolicy retrieveMapperPolicy() {
	MapperPolicy mapperPolicy = DEFAULT_MAPPER_POLICY;
	String policyStr = QoSManagementConfiguration.get(QoSManagementConfiguration.MAPPER_POLICY);
	try {
	    mapperPolicy = MapperPolicy.valueOf(policyStr);
	} catch (Exception e) {
	    mapperPolicy = DEFAULT_MAPPER_POLICY;
	}
	return mapperPolicy;
    }

    public static String[] types() {
	return NODE_TYPES;
    }

    @Override
    public List<CloudNode> filter(List<CloudNode> nodes, DeployableServiceSpec spec) {
	List<CloudNode> filtered = new ArrayList<CloudNode>();
	for (CloudNode node : nodes) {
	    if (isAcceptable(spec.getResourceImpact(), node)) {
		filtered.add(node);
	    }
	}
	return filtered;
    }

    private boolean near(int a, int b) {
	if (Math.abs(a - b) < 0.1 * (double) a)
	    return true;

	return false;
    }

    private int getBaseMemoryFromType(MemoryType memory) {
	switch (memory) {
	case SMALL:
	    return 256;
	case MEDIUM:
	    return 512;
	case LARGE:
	    return 768;
	default:
	    return 256;
	}
    }

    private boolean isAcceptable(ResourceImpact resourceImpact, CloudNode selected) {
	if (noMemoryRequirements(resourceImpact)) {
	    return true;
	}
	switch (this.policy) {
	case ANY_FIT:
	    return getBaseMemoryFromType(resourceImpact.getMemory()) <= selected.getRam();
	case EXACT_FIT:
	    return near(getBaseMemoryFromType(resourceImpact.getMemory()), selected.getRam());
	default:
	    return false;
	}
    }

    private boolean noMemoryRequirements(ResourceImpact resourceImpact) {
	return resourceImpact == null || resourceImpact.getMemory() == null;
    }

}
