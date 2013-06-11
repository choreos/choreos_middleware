/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

public class RecipeBundle {

    private Recipe serviceRecipe;
    private Recipe deactivateRecipe;
    private String cookbookFolder;
    
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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((cookbookFolder == null) ? 0 : cookbookFolder.hashCode());
	result = prime * result + ((deactivateRecipe == null) ? 0 : deactivateRecipe.hashCode());
	result = prime * result + ((serviceRecipe == null) ? 0 : serviceRecipe.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	RecipeBundle other = (RecipeBundle) obj;
	if (cookbookFolder == null) {
	    if (other.cookbookFolder != null)
		return false;
	} else if (!cookbookFolder.equals(other.cookbookFolder))
	    return false;
	if (deactivateRecipe == null) {
	    if (other.deactivateRecipe != null)
		return false;
	} else if (!deactivateRecipe.equals(other.deactivateRecipe))
	    return false;
	if (serviceRecipe == null) {
	    if (other.serviceRecipe != null)
		return false;
	} else if (!serviceRecipe.equals(other.serviceRecipe))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "RecipeBundle [serviceRecipe=" + serviceRecipe + "]";
    }

}
