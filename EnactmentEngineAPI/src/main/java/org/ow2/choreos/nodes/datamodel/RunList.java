package org.ow2.choreos.nodes.datamodel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The list of chef-recipes to be applied in some node.
 * 
 * @author leonardo
 * 
 */
public class RunList {

    private List<String> runList;

    /**
     * 
     * @param recipe
     *            in the format 'cookbook::recipe' ot 'cookbook'
     * @return
     */
    public boolean hasRecipeOnRunlist(String recipe) {
        if (runList != null) {
            for (String entry : runList) {
                if (entry.contains(recipe))
                    return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param recipeName
     *            it can be "cookbook" or "cookbook:recipe".
     * 
     */
    public void addRecipe(String recipeName) {
        if (runList == null)
            runList = new ArrayList<String>();
        runList.add("recipe[" + recipeName + "]");
    }

    /**
     * Returns the run list without the "recipe" keyword and without the
     * brackets "[]".
     * 
     * Example, if knife return "Run List:    recipe[java]", this method will
     * return a list of size == 1, whose value of its single element is "java"
     * 
     * @return
     */
    public List<String> getSimpleRunList() {

        Pattern RUNLIST_ITEM = Pattern.compile("[a-z]+\\[(.+?(::.+?)?)\\]");

        List<String> simpleRunList = new ArrayList<String>();
        for (String item : this.runList) {

            Matcher matcher = RUNLIST_ITEM.matcher(item);
            if (matcher.matches()) {
                simpleRunList.add(matcher.group(1));
            }
        }
        return simpleRunList;
    }

    public List<String> getRunList() {
        return Collections.unmodifiableList(runList);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((runList == null) ? 0 : runList.hashCode());
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
        RunList other = (RunList) obj;
        if (runList == null) {
            if (other.runList != null)
                return false;
        } else if (!runList.equals(other.runList))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "RunList [runList=" + runList + "]";
    }

    public String toJson() {
        StringBuilder json = new StringBuilder("{ 'run_list' : [ ");
        if (runList != null) {
            for (int i = 0; i < runList.size(); i++) {
                String runListItem = runList.get(i);
                json.append("'" + runListItem + "'");
                if (i != runList.size() - 1)
                    json.append(",");
                json.append(" ");
            }
        }
        json.append("] }");
        return json.toString();
    }

}
