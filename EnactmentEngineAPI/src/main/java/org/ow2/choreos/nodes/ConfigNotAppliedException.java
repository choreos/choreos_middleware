/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

public class ConfigNotAppliedException extends Exception {

    private static final long serialVersionUID = -3910285345563830341L;

    public ConfigNotAppliedException(String configName) {
	super("Config " + configName + " not applied");
    }
}
