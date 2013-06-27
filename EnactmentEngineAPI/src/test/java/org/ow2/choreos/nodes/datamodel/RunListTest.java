package org.ow2.choreos.nodes.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

public class RunListTest {

    @Test
    public void shouldAddRecipe() {
        String recipeName = "tomcat";
        RunList runList = new RunList();
        runList.addRecipe(recipeName);

        assertTrue(runList.getRunList().contains("recipe[tomcat]"));
    }

    @Test
    public void shouldVerifyIfHasRecipe() {
        String recipeName = "tomcat";
        RunList runList = new RunList();
        runList.addRecipe(recipeName);

        assertTrue(runList.hasRecipeOnRunlist(recipeName));
    }

    @Test
    public void shouldGenerateSimpleRunList() {
        RunList runList = new RunList();
        runList.addRecipe("tomcat");
        runList.addRecipe("easyesb::client");

        List<String> recipes = runList.getSimpleRunList();
        assertEquals(2, recipes.size());
        assertEquals("tomcat", recipes.get(0));
        assertEquals("easyesb::client", recipes.get(1));
    }

    @Test
    public void shouldGenerateJson() {
        RunList runList = new RunList();
        runList.addRecipe("tomcat");
        runList.addRecipe("easyesb::client");

        String expectedJson = "{ 'run_list' : [ 'recipe[tomcat]', 'recipe[easyesb::client]' ] }";
        String json = runList.toJson();
        assertEquals(expectedJson, json);
    }

}
