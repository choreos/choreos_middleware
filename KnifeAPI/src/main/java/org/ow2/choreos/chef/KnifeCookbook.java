/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

public interface KnifeCookbook {

    public String list() throws KnifeException;

    public String upload(String cookbookName) throws KnifeException;

    public String upload(String cookbookName, String cookbookParentFolder) throws KnifeException;

    public String delete(String cookbookName) throws KnifeException;

}
