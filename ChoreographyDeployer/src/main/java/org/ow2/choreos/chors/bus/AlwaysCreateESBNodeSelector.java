/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import org.ow2.choreos.nodes.NodePoolManager;

/**
 * Selects a different ESB Node to each service.
 * 
 * Note it relies on NPM to provide different nodes.
 * 
 * @author leonardo
 * 
 */
public class AlwaysCreateESBNodeSelector extends BaseESBNodeSelector {

    public AlwaysCreateESBNodeSelector(NodePoolManager npm) {

	super(new SimpleBusHandler(npm));
    }

    // to test purposes
    AlwaysCreateESBNodeSelector(BusHandler busHandler) {

	super(busHandler);
    }

}
