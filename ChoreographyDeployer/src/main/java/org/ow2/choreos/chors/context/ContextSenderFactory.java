/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import org.ow2.choreos.services.datamodel.ServiceType;

public class ContextSenderFactory {

    public static ContextSender getInstance(ServiceType serviceType) {

	switch (serviceType) {

	case SOAP:
	    return new SoapContextSender();

	default:
	    throw new IllegalArgumentException(serviceType + " not supported");
	}
    }

}
