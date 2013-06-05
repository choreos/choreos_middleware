package org.ow2.choreos.chef;

public interface KnifeCookbook {

    public String list() throws KnifeException;

    public String upload(String cookbookName) throws KnifeException;

    public String upload(String cookbookName, String cookbookParentFolder) throws KnifeException;

    public String delete(String cookbookName) throws KnifeException;

}
