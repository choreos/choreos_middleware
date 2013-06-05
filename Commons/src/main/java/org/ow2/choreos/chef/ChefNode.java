package org.ow2.choreos.chef;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChefNode {

    private String name;
    private String environment;
    private String fqdn;
    private String ip;
    private List<String> runList;
    private List<String> roles;
    private List<String> recipes;
    private String platform;

    /**
     * 
     * @param recipe
     *            in the format 'cookbook::recipe'
     * @return
     */
    public boolean hasRecipeOnRunlist(String recipe) {

	for (String entry : runList) {
	    if (entry.contains(recipe))
		return true;
	}
	return false;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getEnvironment() {
	return environment;
    }

    public void setEnvironment(String environment) {
	this.environment = environment;
    }

    public String getFqdn() {
	return fqdn;
    }

    public void setFqdn(String fqdn) {
	this.fqdn = fqdn;
    }

    public String getIp() {
	return ip;
    }

    public void setIp(String ip) {
	this.ip = ip;
    }

    public List<String> getRunList() {
	return runList;
    }

    public void setRunList(List<String> runList) {
	this.runList = runList;
    }

    public List<String> getRoles() {
	return roles;
    }

    public void setRoles(List<String> roles) {
	this.roles = roles;
    }

    public List<String> getRecipes() {
	return recipes;
    }

    public void setRecipes(List<String> recipes) {
	this.recipes = recipes;
    }

    public String getPlatform() {
	return platform;
    }

    public void setPlatform(String platform) {
	this.platform = platform;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((ip == null) ? 0 : ip.hashCode());
	result = prime * result + ((name == null) ? 0 : name.hashCode());
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
	ChefNode other = (ChefNode) obj;
	if (ip == null) {
	    if (other.ip != null)
		return false;
	} else if (!ip.equals(other.ip))
	    return false;
	if (name == null) {
	    if (other.name != null)
		return false;
	} else if (!name.equals(other.name))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "ChefNode [name=" + name + ", ip=" + ip + ", runList=" + runList + ", roles=" + roles + ", recipes="
		+ recipes + "]";
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

}
