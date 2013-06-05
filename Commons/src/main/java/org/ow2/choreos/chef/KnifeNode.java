/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

import java.util.List;

public interface KnifeNode {

    public List<String> list() throws KnifeException;

    public ChefNode show(String nodeName) throws KnifeException;

    /**
     * Uses default recipe
     * 
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
