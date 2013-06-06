/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.Node;

public class RecipeApplier {

    private Logger logger = Logger.getLogger(RecipeApplier.class);

    private static final String CHEF_REPO = DeploymentManagerConfiguration.get("CHEF_REPO");
    private static final String CHEF_CONFIG_FILE = DeploymentManagerConfiguration.get("CHEF_CONFIG_FILE");

    /**
     * Uses the default recipe
     * 
     * @param node
     * @param cookbook
     * @return
     * @throws PrepareDeploymentFailedException
     */
    public void applyRecipe(Node node, String cookbook) throws PrepareDeploymentFailedException {
	this.applyRecipe(node, cookbook, "");
    }

    public void applyRecipe(Node node, String cookbook, String recipe) throws PrepareDeploymentFailedException {

	String recipeFullName;
	if (recipe.equals(""))
	    recipeFullName = cookbook;
	else
	    recipeFullName = cookbook + "::" + recipe;

	NodeChecker checker = new NodeChecker();
	try {
	    checker.checkAndPrepareNode(node);
	} catch (NodeNotOKException e) {
	    throw new PrepareDeploymentFailedException(recipeFullName);
	}

	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);

	logger.debug("aplying " + recipeFullName + " to node " + node.getChefName());

	// Chef fails silently when adding multiple recipes concurrently
	synchronized (RecipeApplier.class) {
	    try {
		knife.node().runListAdd(node.getChefName(), recipeFullName);
	    } catch (KnifeException e) {
		throw new PrepareDeploymentFailedException(recipeFullName);
	    }
	}

	boolean ok = verifyRecipeInRunList(knife, node.getChefName(), cookbook, recipe);
	if (!ok) {
	    throw new PrepareDeploymentFailedException(recipeFullName);
	}
    }

    private boolean verifyRecipeInRunList(Knife knife, String chefName, String cookbook, String recipe) {

	String recipeFullName;
	if (recipe.equals(""))
	    recipeFullName = cookbook;
	else
	    recipeFullName = cookbook + "::" + recipe;

	try {
	    ChefNode chefNode = knife.node().show(chefName);
	    return chefNode.hasRecipeOnRunlist(recipeFullName);
	} catch (KnifeException e) {
	    return false;
	}
    }
}
