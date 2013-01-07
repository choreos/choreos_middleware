package org.ow2.choreos.deployment.services.recipe;

import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

public interface RecipeBuilder {
	
	/**
	 * Uses the template to create a new recipe according to <code>service</code> specification.
	 * This new recipe can be used by Chef to deploy the <code>service</code>.
	 * 
	 * @param service
	 * @return
	 */
	public Recipe createRecipe(ServiceSpec serviceSpec);
}
