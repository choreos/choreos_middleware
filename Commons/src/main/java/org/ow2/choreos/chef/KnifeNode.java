package org.ow2.choreos.chef;

import java.util.List;

public interface KnifeNode {

	public List<String> list() throws KnifeException;

	public ChefNode show(String nodeName) throws KnifeException;

	/**
	 * Uses default recipe
	 * @param nodeName
	 * @param cookbook
	 * @return
	 * @throws KnifeException
	 */
	public String runListAdd(String nodeName, String recipeFullName) throws KnifeException;

	public String runListRemove(String nodeName, String recipeFullName) throws KnifeException;
    
    public String create(String nodeName) throws KnifeException;
    
    public String delete(String nodeName) throws KnifeException;

}


