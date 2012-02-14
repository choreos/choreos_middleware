package eu.choreos.storagefactory.recipe;

public class RecipeFactory {

	public Recipe createRecipe() {
		Recipe recipe = new Recipe();
		recipe.setName("mysql::server");
		recipe.setFolder("");
		return recipe;
	}
}
