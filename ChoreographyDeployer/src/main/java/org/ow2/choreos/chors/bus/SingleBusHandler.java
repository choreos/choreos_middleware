/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import org.ow2.choreos.nodes.NodePoolManager;

/**
 * Always return the same EasyESB node, that is created in the first invocation.
 * 
 * This class is intended to be used as a singleton.
 * 
 * @author leonardo, felps
 * 
 */
class SingleBusHandler implements BusHandler {

    private static SingleBusHandler singleton;

    private int SYNC_WAIT_STEP = 30; // seconds

    private volatile EasyESBNode esbNode;
    private NodePoolManager npm;
    private boolean creating = false;

    public static synchronized SingleBusHandler getInstance(NodePoolManager npm) {

	if (singleton == null) {
	    singleton = new SingleBusHandler(npm);
	}
	return singleton;
    }

    // to test purposes
    SingleBusHandler(NodePoolManager npm) {
	this.npm = npm;
    }

    // to test purposes
    SingleBusHandler(NodePoolManager npm, int waitStep) {
	this.npm = npm;
	this.SYNC_WAIT_STEP = 1;
    }

    @Override
    public EasyESBNode retrieveBusNode() throws NoBusAvailableException {

	this.sync();

	if (this.esbNode == null) {
	    BusHandler simpleBusHandler = new SimpleBusHandler(this.npm);
	    this.esbNode = simpleBusHandler.retrieveBusNode();
	    this.creating = false;
	}
	return this.esbNode;
    }

    private void sync() {

	boolean wait = false;
	synchronized (this) {
	    if (this.creating) {
		wait = true;
	    }
	    if (!this.creating && this.esbNode == null) {
		this.creating = true;
	    }
	}

	if (wait) {
	    while (this.creating) {
		sleep();
	    }
	}
    }

    private void sleep() {
	try {
	    Thread.sleep(SYNC_WAIT_STEP * 1000);
	} catch (InterruptedException e) {
	    // hope never get here ^^
	}
    }

}
