/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.datamodel;

/**
 * The artifact is the deployable unit encompassing service binaries, and it is
 * intended to be deployed somewhere according to its type.
 * 
 * @author leonardo
 * 
 */
public enum PackageType {

    COMMAND_LINE, TOMCAT, EASY_ESB, OTHER;

}
