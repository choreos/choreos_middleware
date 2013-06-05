/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

public class RecipeBundle {

    public Recipe getServiceRecipe() {
	return serviceRecipe;
    }

    public void setServiceRecipe(Recipe serviceRecipe) {
	this.serviceRecipe = serviceRecipe;
    }

    public Recipe getDeactivateRecipe() {
	return deactivateRecipe;
    }

    public void setDeactivateRecipe(Recipe deactivateRecipe) {
	this.deactivateRecipe = deactivateRecipe;
    }

    public String getCookbookFolder() {
	return cookbookFolder;
    }

    public void setCookbookFolder(String cookbookFolder) {
	this.cookbookFolder = cookbookFolder;
    }

    private Recipe serviceRecipe;
    private Recipe deactivateRecipe;
    private String cookbookFolder;

}
