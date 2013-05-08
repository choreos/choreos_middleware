package org.ow2.choreos.services.datamodel;


public class RecipeBundle {

	public Recipe getServiceRecipe() {
		return serviceRecipe;
	}
	public void setServiceRecipe(Recipe serviceRecipe) {
		this.serviceRecipe = serviceRecipe;
	}
	public Recipe getActivateRecipe() {
		return activateRecipe;
	}
	public void setActivateRecipe(Recipe activateRecipe) {
		this.activateRecipe = activateRecipe;
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
	private Recipe activateRecipe;
	private Recipe deactivateRecipe;
	private String cookbookFolder;
	
}
