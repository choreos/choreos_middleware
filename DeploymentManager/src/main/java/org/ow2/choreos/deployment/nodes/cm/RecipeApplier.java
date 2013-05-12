package org.ow2.choreos.deployment.nodes.cm;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.nodes.ConfigNotAppliedException;
import org.ow2.choreos.nodes.datamodel.Node;

public class RecipeApplier {

	private Logger logger = Logger.getLogger(RecipeApplier.class);
	
    private static final String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");


	/**
	 * Uses the default recipe
	 * @param node
	 * @param cookbook
	 * @return
	 * @throws ConfigNotAppliedException
	 */
    public void applyRecipe(Node node, String cookbook) throws ConfigNotAppliedException {
        this.applyRecipe(node, cookbook, "");
    }

    public void applyRecipe(Node node, String cookbook, String recipe) throws ConfigNotAppliedException {

    	String recipeFullName;
    	if(recipe.equals(""))
    		recipeFullName = cookbook;
    	else
    		recipeFullName = cookbook + "::" + recipe;

    	NodeChecker checker = new NodeChecker();
    	try {
    		checker.checkAndPrepareNode(node);
		} catch (NodeNotOKException e) {
			throw new ConfigNotAppliedException(recipeFullName, node.getId());
		} 

        Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);

        logger.debug("aplying " + recipeFullName + " to node " + node.getChefName());

        // Chef fails silently when adding multiple recipes concurrently
        synchronized(RecipeApplier.class) {
        	try {
				knife.node().runListAdd(node.getChefName(), recipeFullName);
			} catch (KnifeException e) {
				throw new ConfigNotAppliedException(recipeFullName, node.getId());
			}
        }

        boolean ok = verifyRecipeInRunList(knife, node.getChefName(), cookbook, recipe);
        if (!ok) {
			throw new ConfigNotAppliedException(recipeFullName, node.getId());
        }
    }
    
    private boolean verifyRecipeInRunList(Knife knife, String chefName,
			String cookbook, String recipe) {

    	String recipeFullName;
    	if(recipe.equals(""))
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
