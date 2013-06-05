/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import org.ow2.choreos.nodes.NodePoolManager;

/**
 * Selects the same EasyESBNode to all the instances.
 * 
 * @author leonardo
 * 
 */
public class SingleESBNodeSelector extends BaseESBNodeSelector {

    public SingleESBNodeSelector(NodePoolManager npm) {

	super(SingleBusHandler.getInstance(npm));
    }

    // to test purposes
    SingleESBNodeSelector(BusHandler busHandler) {

	super(busHandler);
    }
}
