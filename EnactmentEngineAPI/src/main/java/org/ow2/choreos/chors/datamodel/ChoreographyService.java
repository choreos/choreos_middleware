/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.datamodel;

import org.ow2.choreos.services.datamodel.Service;

public class ChoreographyService {

    private ChoreographyServiceSpec choreographyServiceSpec;
    private Service service;

    public ChoreographyService() {
    }

    public ChoreographyService(ChoreographyServiceSpec choreographyServiceSpec) {
	this.choreographyServiceSpec = choreographyServiceSpec;
    }

    public ChoreographyServiceSpec getChoreographyServiceSpec() {
	return choreographyServiceSpec;
    }

    public void setChoreographyServiceSpec(ChoreographyServiceSpec choreographyServiceSpec) {
	this.choreographyServiceSpec = choreographyServiceSpec;
    }

    public Service getService() {
	return service;
    }

    public void setService(Service service) {
	this.service = service;
    }

    @Override
    public String toString() {
	return "ChoreographyService [choreographyServiceSpec=" + choreographyServiceSpec + ", service=" + service;
    }
}
