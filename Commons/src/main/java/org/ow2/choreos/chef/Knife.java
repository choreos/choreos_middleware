package org.ow2.choreos.chef;

/**
 * Implementing classes must execute the knife commands
 * 
 * The text output is returned by each method
 * The implementer class can parse the output text to check if is everything OK
 * If not, it is desirable to throw an KnifeException
 * @author leonardo
 *
 */
public interface Knife {

	public String bootstrap(String pKeyFile, String ip, String user) throws KnifeException;
    
    public String runListAdd(String nodeName, String cookbook, String recipe) throws KnifeException;
    
    public String nodeList() throws KnifeException;
    
    public String nodeShow(String nodeName) throws KnifeException;
    
    public String nodeDelete(String nodeName) throws KnifeException;

    public String clientDelete(String clientName) throws KnifeException;
    
    public String cookbookUpload(String cookbookName, String cookbookParentFolder) throws KnifeException;
    
    public String cookbookDelete(String cookbookName) throws KnifeException;
    
    public String cookbooksList() throws KnifeException;

}
