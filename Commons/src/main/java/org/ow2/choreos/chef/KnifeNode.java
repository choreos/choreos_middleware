package org.ow2.choreos.chef;

public interface KnifeNode {

	public String list() throws KnifeException;

	public String show(String nodeName) throws KnifeException;

	public String runListAdd(String nodeName, String cookbook, String recipe) throws KnifeException;
	
	public String runListRemove(String nodeName, String cookbook, String recipe) throws KnifeException;
    
    public String create(String nodeName) throws KnifeException;
    
    public String delete(String nodeName) throws KnifeException;

}


